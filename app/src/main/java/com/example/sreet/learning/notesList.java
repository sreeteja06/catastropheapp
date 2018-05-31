package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class notesList extends AppCompatActivity {

    String Year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        setTitle("Notes");
        Bundle bundle = getIntent().getExtras();
        Year = bundle.getString("Year");
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(Year);
    }
}
