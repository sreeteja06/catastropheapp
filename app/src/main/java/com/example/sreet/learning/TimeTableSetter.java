package com.example.sreet.learning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import java.util.Calendar;

public class TimeTableSetter extends AppCompatActivity {
    CardView c1,c2,c3,c4,c5,c6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_setter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("TimeTable");
        c1 = findViewById(R.id.monday);
        c2 = findViewById(R.id.tuesday);
        c3 =findViewById(R.id.wednesday);
        c4 = findViewById(R.id.thursday);
        c5 = findViewById(R.id.friday);
        c6 = findViewById(R.id.saturday);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTableSetter.this,Timetableconfig.class);
                intent.putExtra("daydetails", 2);
                intent.putExtra("dayofweek","TimeTableMonday");
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTableSetter.this,Timetableconfig.class);
                intent.putExtra("daydetails",3);
                intent.putExtra("dayofweek","TimeTableTuesday");

                startActivity(intent);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTableSetter.this,Timetableconfig.class);
                intent.putExtra("daydetails",4);
                intent.putExtra("dayofweek","TimeTableWednesday");

                startActivity(intent);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTableSetter.this,Timetableconfig.class);
                intent.putExtra("daydetails",5);
                intent.putExtra("dayofweek","TimeTableThursday");

                startActivity(intent);
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTableSetter.this,Timetableconfig.class);
                intent.putExtra("daydetails",6);
                intent.putExtra("dayofweek","TimeTableFriday");

                startActivity(intent);
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeTableSetter.this,Timetableconfig.class);
                intent.putExtra("daydetails",7);
                intent.putExtra("dayofweek","TimeTableSaturday");

                startActivity(intent);
            }
        });
    }
}
