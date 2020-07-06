package com.example.tumplatform;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity {

    ArrayList<Comments> comments = new ArrayList<>();
    ArrayList<Posts> posts = new ArrayList<>();
    private commentsAdapter commentsAdapter;
    private postsAdapter postsAdapter;
    private RecyclerView comments_recyclerview;
    private RecyclerView posts_recyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        posts_recyclerview = (RecyclerView)findViewById(R.id.posts_recyclerview);
        posts_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        getResponsePosts();

        comments_recyclerview = (RecyclerView)findViewById(R.id.comments_recyclerview);
        comments_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        getResponse();

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResponse();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void getResponse() {
        Intent intent = getIntent();
        final int post_id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("post_id")));
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://infinite-anchorage-45437.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api requestInteface=retrofit.create(Api.class);
        Call<List<Comments>> call = requestInteface.getCommentsByPostId(post_id);
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                comments = new ArrayList<>(response.body());
                commentsAdapter = new commentsAdapter(CommentActivity.this, posts,comments);
                comments_recyclerview.setAdapter(commentsAdapter);
                comments_recyclerview.setNestedScrollingEnabled(false);
                Toast.makeText(CommentActivity.this,"Comments retrieved successfully",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                Toast.makeText(CommentActivity.this,"Post not retrieved",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getResponsePosts() {
        Intent intent = getIntent();
        final int post_id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("post_id")));
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://infinite-anchorage-45437.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api requestInteface=retrofit.create(Api.class);
        Call<List<Posts>> call= requestInteface.getPostsByPostId(post_id);
        Call<List<Comments>> callComments = requestInteface.getCommentsByPostId(post_id);
        callComments.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                comments = new ArrayList<>(response.body());
                postsAdapter = new postsAdapter(CommentActivity.this, posts, comments);
                posts_recyclerview.setAdapter(postsAdapter);
                posts_recyclerview.setNestedScrollingEnabled(false);
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {

            }
        });
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                posts = new ArrayList<>(response.body());
                postsAdapter = new postsAdapter(CommentActivity.this, posts, comments);
                posts_recyclerview.setAdapter(postsAdapter);
                posts_recyclerview.setNestedScrollingEnabled(false);
                Toast.makeText(CommentActivity.this,"Post retrieved successfully",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(CommentActivity.this,"Post not retrieved",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
