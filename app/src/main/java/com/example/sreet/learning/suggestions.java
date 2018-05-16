package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class suggestions extends AppCompatActivity {
    EditText myedittext;
    ImageButton myApplyBt;
    Firebase myfire;
    ListView list;
    String myString;
    ArrayList<String> myarraylist = new ArrayList<>();
    ArrayList<String> dateAndTimeList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Suggestions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_suggestions);
        myedittext = (EditText) findViewById(R.id.editText);
        myApplyBt = (ImageButton) findViewById(R.id.button);
        Firebase.setAndroidContext(this);
        myfire = new Firebase("https://learning-2b334.firebaseio.com/suggestions");
        final ArrayAdapter<String> myarrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,myarraylist);
        list = (ListView) findViewById(R.id.listview);
        list.setAdapter(myarrayadapter);
        //list.setSelection(list.getAdapter().getCount()-1);
        myApplyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myString = myedittext.getText().toString();
                //keyvaluedata = keyvaluetext.getText().toString();
                Date todaysDate = new Date();
                DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = df2.format(todaysDate);
                Firebase childbase = myfire.child(date);
                childbase.setValue(myString);
                myedittext.setText(null);
                Toast.makeText(suggestions.this, "success", Toast.LENGTH_SHORT).show();
            }
        });




        myfire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String childvalaue = dataSnapshot.getValue(String.class);
                String keyvalue = dataSnapshot.getKey();
                dateAndTimeList.add(keyvalue);
                myarraylist.add(childvalaue);
                myarrayadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                myarrayadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}