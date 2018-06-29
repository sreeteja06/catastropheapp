package com.example.sreet.learning;

/**
 * Created by sripadpc on 27-05-2018.
 */

public class NotesDataClass {
    String name;
    String date;
NotesDataClass(){}

    public NotesDataClass(String name, String date, String url) {
        this.name = name;
        this.date = date;
        Url = url;
    }

    public String getDate() {
        return date;
    }

    String Url;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public String getUrll() {
        return Url;
    }

   /* public void setUrl(String url) {
        Url = url;
    }*/
}
