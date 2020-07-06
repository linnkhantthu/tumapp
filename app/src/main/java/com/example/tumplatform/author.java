package com.example.tumplatform;

public class author {
    private String account_status;
    private String account_type;
    private String email;
    private int id;
    private String image_file;
    private String username;

    public author(String account_status, String account_type, String email, int id, String image_file, String username) {
        this.account_status = account_status;
        this.account_type = account_type;
        this.email = email;
        this.id = id;
        this.image_file = image_file;
        this.username = username;
    }

    public String getAccount_status() {
        return account_status;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getImage_file() {
        return image_file;
    }

    public String getUsername() {
        return username;
    }
}
