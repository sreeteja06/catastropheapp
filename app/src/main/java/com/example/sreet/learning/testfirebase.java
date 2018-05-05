package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class testfirebase extends AppCompatActivity {

    ArrayList<String> myarraylist = new ArrayList<>();
    ListView list;
    Firebase myfire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfirebase);

        Firebase.setAndroidContext(this);
        myfire = new Firebase("https://learning-2b334.firebaseio.com/");
        final ArrayAdapter<String> myarrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myarraylist);
        list = (ListView) findViewById(R.id.listview);
        list.setAdapter(myarrayadapter);
        myfire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String childvalaue = dataSnapshot.getValue(String.class);
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
