package com.example.venkatesh.project1.model;

/**
 * Created by venkatesh on 5/22/16.
 */
public class Task {
    public Integer id;
    public  String title;
    public String description;
    public  String date;
    public  Integer status;

    public Task(Integer id,String title, String description, String date, Integer status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
    }

}
