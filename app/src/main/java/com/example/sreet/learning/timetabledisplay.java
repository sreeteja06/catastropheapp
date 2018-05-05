package com.example.sreet.learning;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;

import com.firebase.client.Firebase;

import java.util.Date;
import java.text.DateFormat;

public class timetabledisplay extends AppCompatActivity {

    EditText myedittext,keyvaluetext;
    Button myApplyBt;
    String myString,keyvaluedata;
    Firebase myfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TimeTable");
        setContentView(R.layout.activity_timetabledisplay);

        myedittext = (EditText) findViewById(R.id.editText);
        //keyvaluetext = (EditText) findViewById(R.id.keytext);
        myApplyBt = (Button) findViewById(R.id.button);
        Firebase.setAndroidContext(this);
        //String DeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        myfirebase = new Firebase("https://learning-2b334.firebaseio.com/users");
        myApplyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myString = myedittext.getText().toString();
                //keyvaluedata = keyvaluetext.getText().toString();
                Date todaysDate = new Date();
                DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = df2.format(todaysDate);
                Firebase childbase = myfirebase.child(date);
                childbase.setValue(myString);
                Toast.makeText(timetabledisplay.this, "success", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
