package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class timetabledisplay extends AppCompatActivity {

    EditText myedittext;
    Button myApplyBt;
    String myString;
    Firebase myfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TimeTable");
        setContentView(R.layout.activity_timetabledisplay);

        myedittext = (EditText) findViewById(R.id.editText);
        myApplyBt = (Button) findViewById(R.id.button);
        Firebase.setAndroidContext(this);
        myfirebase = new Firebase("https://learning-2b334.firebaseio.com/");
        myApplyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myString = myedittext.getText().toString();
                Firebase childbase = myfirebase.child("MychildName");
                childbase.setValue(myString);
                Toast.makeText(timetabledisplay.this, "success", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
