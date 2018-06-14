package com.example.sreet.learning;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class attendance extends AppCompatActivity implements View.OnClickListener{
    CardView openSis, CalcAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        openSis = (CardView) findViewById(R.id.openSis);
        CalcAttendance = (CardView) findViewById(R.id.calculateAttendance);
        openSis.setOnClickListener(this);
        CalcAttendance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.openSis: Uri uri = Uri.parse("http://42.104.112.137/sz/Login.aspx");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);startActivity(intent);break;
            case R.id.calculateAttendance: i = new Intent(this,calculateAttendance.class);startActivity(i);break;
            default: break;
        }
    }
}
