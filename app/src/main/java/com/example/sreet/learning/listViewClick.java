package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class listViewClick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_click);
        Bundle bundle = getIntent().getExtras();
        String Date = bundle.getString("Date");
        String Descript = bundle.getString("Description");
        TextView dateAndTime = (TextView) findViewById(R.id.dateAndTime);
        dateAndTime.setText(Date);
        TextView notice = (TextView) findViewById(R.id.notice);
        notice.setText(Descript);
    }
}