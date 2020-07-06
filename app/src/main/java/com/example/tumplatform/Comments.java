package com.example.tumplatform;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comments {
    private author author;
    private String content;
    private String date_posted;
    private int id;
    private int post_id;
    private String author_name;

    public Comments(com.example.tumplatform.author author, String content, String date_posted, int id, int post_id, String author_name) {
        this.author = author;
        this.content = content;
        this.date_posted = date_posted;
        this.id = id;
        this.post_id = post_id;
        this.author_name = author_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public com.example.tumplatform.author getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getDate_posted() throws ParseException {
        //2020-06-26T15:25:11.994451
        Date f_date = new SimpleDateFormat("yyyy-mm-dd").parse(date_posted);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        assert f_date != null;
        String strDate = dateFormat.format(f_date);
        return strDate;
    }

    public int getId() {
        return id;
    }

    public int getPost_id() {
        return post_id;
    }
}
