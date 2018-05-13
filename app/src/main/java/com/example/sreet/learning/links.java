package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class links extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Links");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_links);
    }
}
