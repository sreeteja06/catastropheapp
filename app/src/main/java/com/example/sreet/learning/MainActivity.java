package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView Suggestions,Links,Activities,TimeTable,Notices;
    private static MainActivity mInstance;
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
        FirebaseMessaging.getInstance().subscribeToTopic("notifications");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
        MenuItem item= menu.findItem(R.id.savedNotices);
        item.setTitle("Show guide");
        this.invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorite:
                Intent intent = new Intent(this,idCard.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent i = new Intent(this,about.class);
                startActivity(i);
                break;
            case R.id.saveNOtice:
                Intent intent1 = new Intent(this,savedNotices.class);
                startActivity(intent1);
                break;
            case R.id.savedNotices:
                Intent intent2 = new Intent(this,WelcomeActivity.class);
                SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = ref.edit();
                editor.putBoolean("FirstTimeStartFlag", true);
                editor.commit();
                startActivity(intent2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.timetableid: i = new Intent(this,TimeTable.class);startActivity(i);break;
            case R.id.activitiesid: i = new Intent(this,activities.class);startActivity(i);break;
            case R.id.suggestionsid: i = new Intent(this,suggestions.class);startActivity(i);break;
            case R.id.linksid: i = new Intent(this,notes.class);startActivity(i);break;
            case R.id.noticesid: i = new Intent(this, com.example.sreet.learning.Notices.class);startActivity(i);break;
            default: break;
        }
    }




}