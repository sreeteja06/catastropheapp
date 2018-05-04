package com.example.sreet.learning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView Suggestions,Links,Activities,TimeTable,Notices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //defining cards
        Suggestions = (CardView) findViewById(R.id.suggestionsid);
        Links = (CardView) findViewById(R.id.linksid);
        Activities = (CardView) findViewById(R.id.activitiesid);
        TimeTable = (CardView) findViewById(R.id.timetableid);
        Notices = (CardView) findViewById(R.id.noticesid);
        //add click listener to the cards
        Suggestions.setOnClickListener(this);
        Links.setOnClickListener(this);
        Activities.setOnClickListener(this);
        TimeTable.setOnClickListener(this);
        Notices.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.timetableid: i = new Intent(this,TimeTable.class);startActivity(i);break;
            case R.id.activitiesid: i = new Intent(this,activities.class);startActivity(i);break;
            case R.id.suggestionsid: i = new Intent(this,suggestions.class);startActivity(i);break;
            case R.id.linksid: i = new Intent(this,links.class);startActivity(i);break;
            case R.id.noticesid: i = new Intent(this,Notices.class);startActivity(i);break;
            default: break;
        }
    }
}