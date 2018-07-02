package com.example.sreet.learning;

import java.util.ArrayList;

public class NoticesDataClass {
    String Descript;
    String Date;
    String user;
    String imagesValue;
     public NoticesDataClass(String Descript, String Date, String user, String imagesValue){
         this.Descript = Descript;
         this.Date = Date;
         this.user = user;
         this.imagesValue = imagesValue;
    }
    public String getDescript(){
         return this.Descript;
    }
}
