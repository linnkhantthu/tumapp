package com.example.tumplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class LoginActivity extends AppCompatActivity {

    private List<author> authors;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = (EditText)findViewById(R.id.email);
        final EditText password = (EditText)findViewById(R.id.password);
        Button login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email;
                str_email = email.getText().toString();
                String str_password;
                str_password = password.getText().toString();
                authentication(str_email, str_password);
            }
        });
    }

    private void authentication(String email, String password){
        final ProgressBar loading = (ProgressBar)findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        sessionManager = new SessionManager(this);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://infinite-anchorage-45437.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api requestInteface=retrofit.create(Api.class);
        Call<List<author>> call= requestInteface.getLoginDetails(email, password);
        call.enqueue(new Callback<List<author>>() {
            @Override
            public void onResponse(Call<List<author>> call, Response<List<author>> response) {
                authors = response.body();
                if (authors == null){
                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    //sessionManager.logout();
                    loading.setVisibility(View.GONE);
                }
                else{
                    authors = new ArrayList<>(response.body());
                    for (int i=0;i<authors.size();i++){
                        sessionManager.createSession(authors.get(i).getUsername(), authors.get(i).getEmail(),
                                authors.get(i).getId() + "", authors.get(i).getImage_file(),
                                authors.get(i).getAccount_status(), authors.get(i).getAccount_type());
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    (LoginActivity.this).finish();
                }
            }

            @Override
            public void onFailure(Call<List<author>> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Connection Error",Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
            }
        });
    }
}