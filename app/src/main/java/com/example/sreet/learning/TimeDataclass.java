package com.example.sreet.learning;

public class TimeDataclass {

    TimeDataclass(){}
    private String subject,starttime,endtime,classroom,Uniquekey;

    public TimeDataclass(String subject, String starttime, String endtime, String classroom,String Uniquekey) {
        this.subject = subject;
        this.starttime = starttime;
        this.endtime = endtime;
        this.classroom = classroom;
        this.Uniquekey =Uniquekey;
    }

    public String getSubject() {

        return subject;
    }

    public String getUniquekey() {
        return Uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        Uniquekey = uniquekey;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
