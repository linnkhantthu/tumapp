package com.example.tumplatform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String EMAIL = "EMAIL";
    public static final String ID = "ID";
    public static final String IMAGE_FILE = "IMAGE_FILE";
    public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ACCOUNT_STATUS = "ACCOUNT_STATUS";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email, String id, String image_file, String account_type, String account_status){

        editor.putBoolean(LOGIN, true);
        editor.putString(USERNAME, name);
        editor.putString(EMAIL, email);
        editor.putString(ID, id);
        editor.putString(IMAGE_FILE, image_file);
        editor.putString(ACCOUNT_STATUS, account_status);
        editor.putString(ACCOUNT_TYPE, account_type);
        editor.apply();

    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){

        if (!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(IMAGE_FILE, sharedPreferences.getString(IMAGE_FILE, null));
        user.put(ACCOUNT_TYPE, sharedPreferences.getString(ACCOUNT_TYPE, null));
        user.put(ACCOUNT_STATUS, sharedPreferences.getString(ACCOUNT_STATUS, null));


        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((LoginActivity) context).finish();

        //Intent i = new Intent(context, LoginActivity.class);
        //context.startActivity(i);
        //((MainActivity) context).finish();

    }

}