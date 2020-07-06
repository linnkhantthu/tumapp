package com.example.tumplatform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    private RecyclerView profile_recyclerview;
    private RecyclerView profile_recyclerview_posts;
    ArrayList<author> author = new ArrayList<>();
    ArrayList<Posts> posts = new ArrayList<>();
    ArrayList<Comments> comments = new ArrayList<>();
    private postsAdapter postsAdapter;
    private profileAdapter profileAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_recyclerview=(RecyclerView)findViewById(R.id.profile_recyclerview);
        profile_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        getResponse();

        profile_recyclerview_posts=(RecyclerView)findViewById(R.id.profile_recyclerview_posts);
        profile_recyclerview_posts.setLayoutManager(new LinearLayoutManager(this));
        getResponsePosts();

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResponse();
                getResponsePosts();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.canChildScrollUp();
            }
        });
    }
    private void getResponse() {
        Intent intent = getIntent();
        int user_id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("user_id")));
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://infinite-anchorage-45437.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api requestInteface=retrofit.create(Api.class);
        Call<List<author>> call= requestInteface.getProfiles(user_id);
        call.enqueue(new Callback<List<author>>() {
            @Override
            public void onResponse(Call<List<author>> call, Response<List<author>> response) {
                author = new ArrayList<>(response.body());
                profileAdapter = new profileAdapter(Profile.this, author);
                profile_recyclerview.setAdapter(profileAdapter);
                profile_recyclerview.setNestedScrollingEnabled(false);
                Toast.makeText(Profile.this,"User retrieved successfully",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<author>> call, Throwable t) {
                Toast.makeText(Profile.this,"Post not retrieved",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getResponsePosts() {
        Intent intent = getIntent();
        int user_id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("user_id")));
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://infinite-anchorage-45437.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api requestInteface=retrofit.create(Api.class);
        Call<List<Posts>> call= requestInteface.getUserPosts(user_id);
        Call<List<Comments>> callComments = requestInteface.getUserComments(user_id);
        callComments.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                comments = new ArrayList<>(response.body());
                postsAdapter = new postsAdapter(Profile.this, posts, comments);
                profile_recyclerview_posts.setAdapter(postsAdapter);
                profile_recyclerview_posts.setNestedScrollingEnabled(false);
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {

            }
        });
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                posts = new ArrayList<>(response.body());
                postsAdapter = new postsAdapter(Profile.this, posts, comments);
                profile_recyclerview_posts.setAdapter(postsAdapter);
                Toast.makeText(Profile.this,"Post retrieved successfully",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(Profile.this,"Post not retrieved",Toast.LENGTH_SHORT).show();
            }
        });
    }

}