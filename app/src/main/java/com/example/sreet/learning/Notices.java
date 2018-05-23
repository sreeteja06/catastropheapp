package com.example.sreet.learning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Notices extends AppCompatActivity implements View.OnClickListener{
    private CardView firstyear,secondyear,thirdyear,forthyear;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setTitle("TimeTable");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_notices);
        //defining cards
        firstyear = (CardView) findViewById(R.id.firstyearid);
        secondyear = (CardView) findViewById(R.id.secondyearid);
        thirdyear = (CardView) findViewById(R.id.thirdyearid);
        forthyear = (CardView) findViewById(R.id.forthyearid);
        //setting click listeners to the cards
        firstyear.setOnClickListener(this);
        secondyear.setOnClickListener(this);
        thirdyear.setOnClickListener(this);
        forthyear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.firstyearid:{
                i = new Intent(this,firstYearNotices.class);
                startActivity(i);
                break;
            }
            case R.id.secondyearid:{
                i = new Intent(this,secondYearNotices.class);
                startActivity(i);
                break;
            }
            case R.id.thirdyearid:{
                i = new Intent(this,thirdYearNotices.class);
                startActivity(i);
                break;
            }
            case R.id.forthyearid:{
                i = new Intent(this,fourthYearNotices.class);
                startActivity(i);
                break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
