package com.example.sreet.learning;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class testfirebase extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> myarraylist = new ArrayList<>();
    ListView list;
    EditText myedittext,keyvaluetext;
    ImageButton myApplyBt;
    String myString,keyvaluedata;
    Firebase myfire;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Hello listview","you clicked item: "+id+"at position"+position);
        Intent intent = new Intent(this,listViewClick.class);
        String positionstr = myarraylist.get(position);
        intent.putExtra("message",positionstr);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfirebase);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setTitle("Message test");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myedittext = (EditText) findViewById(R.id.editText);
        myApplyBt = (ImageButton) findViewById(R.id.button);
        Firebase.setAndroidContext(this);
        myfire = new Firebase("https://learning-2b334.firebaseio.com/users");
        final ArrayAdapter<String> myarrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myarraylist);
        list = (ListView) findViewById(R.id.listview);
        list.setOnItemClickListener(this);
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
                Toast.makeText(testfirebase.this, "success", Toast.LENGTH_SHORT).show();
            }
        });


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
