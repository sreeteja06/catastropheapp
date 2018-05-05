package com.example.sreet.learning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class TimeTable extends AppCompatActivity implements View.OnClickListener{
    private CardView firstyear,secondyear,thirdyear,forthyear;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setTitle("TimeTable");
        setContentView(R.layout.activity_time_table);
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
                i = new Intent(this,timetabledisplay.class);
                i.putExtra("year",1);
                startActivity(i);
                break;
            }
            case R.id.secondyearid:{
                i = new Intent(this,testfirebase.class);
                startActivity(i);
                break;
            }
            case R.id.thirdyearid:{
                i = new Intent(this,teststorage.class);
                startActivity(i);
                break;
            }
        }
    }
}
