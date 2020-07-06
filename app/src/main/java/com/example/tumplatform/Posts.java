package com.example.tumplatform;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Posts {
    private author author;
    private String content;
    private String date_posted;
    private int id;
    private ArrayList<String> poll_data;
    private String tag;
    private String title;
    private String user_id;

    public Posts( author author, String content, String date_posted, int id, ArrayList<String> poll_data, String tag, String title, String user_id) {
        this.author = author;
        this.content = content;
        this.date_posted = date_posted;
        this.id = id;
        this.poll_data = poll_data;
        this.tag = tag;
        this.title = title;
        this.user_id = user_id;
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

    public ArrayList<String> getPoll_data() {
        return poll_data;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public String getUser_id() {
        return user_id;
    }
}
