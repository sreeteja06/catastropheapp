package com.example.sreet.learning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class TimeTableSetter extends AppCompatActivity {
    CardView c1,c2,c3,c4,c5,c6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_setter);
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
                startActivity(intent);
            }
        });
    }
}
