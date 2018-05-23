package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class idCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        setTitle("ID");
        getSupportActionBar().hide();
    }
}
