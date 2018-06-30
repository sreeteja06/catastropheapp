package com.example.sreet.learning;

/**
 * Created by sripadpc on 27-05-2018.
 */

public class NotesDataClass {
    String name;
    String date;
    private String Url;

    NotesDataClass(){}

    public NotesDataClass(String name, String date, String url) {
        this.name = name;
        this.date = date;
        Url = url;
    }

    public String getDate() {
        return date;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        Url = url;
    }
    /* public void setUrl(String url) {
        Url = url;
    }*/
}
