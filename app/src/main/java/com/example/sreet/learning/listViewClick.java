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
        String message = bundle.getString("message");
        setTitle(message.substring(0,10));
        TextView textview = (TextView) findViewById(R.id.textView);
        textview.setText(message);
    }
}
