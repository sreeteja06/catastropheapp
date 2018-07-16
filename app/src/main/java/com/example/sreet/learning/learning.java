package com.example.sreet.learning;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

public class learning extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
