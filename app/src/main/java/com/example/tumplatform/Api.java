package com.example.tumplatform;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("/home/json")
    Call<List<Posts>> getPosts();
    @GET("/comments/json")
    Call<List<Comments>> getComments();

    @GET("/users/json/{user_id}")
    Call<List<author>> getProfiles(@Path("user_id") int userId);

    @GET("/users/posts/json/{user_id}")
    Call<List<Posts>> getUserPosts(@Path("user_id") int userId);

    @GET("/comments/json/{user_id}")
    Call<List<Comments>> getUserComments(@Path("user_id") int userId);

    @GET("/comments/post/json/{post_id}")
    Call<List<Comments>> getCommentsByPostId(@Path("post_id") int post_id);

    @GET("/posts/json/{post_id}")
    Call<List<Posts>> getPostsByPostId(@Path("post_id") int post_id);

    @FormUrlEncoded
    @POST("/login/app/")
    Call<List<author>> getLoginDetails(@Field("email") String email, @Field("password") String password);
}
