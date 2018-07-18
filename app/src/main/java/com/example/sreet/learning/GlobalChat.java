package com.example.sreet.learning;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GlobalChat extends AppCompatActivity {
    DatabaseReference myfire;
    EditText editTextGlobalChat;
    ImageButton sendGlobalChatButton;
    RecyclerView recyclerView;
    ArrayList<String> Emails = new ArrayList<>();
    ArrayList<String> Messages = new ArrayList<>();
    globalChatAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);
        setTitle("Global Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextGlobalChat = (EditText) findViewById(R.id.editTextGlobalChat);
        sendGlobalChatButton = (ImageButton) findViewById(R.id.sendGlobalChatButton);
        recyclerView = (RecyclerView) findViewById(R.id.globalChatRecyclerView);
        myfire = FirebaseDatabase.getInstance().getReference().child("GlobalChat");
        myfire.keepSynced(true);

        myAdapter = new globalChatAdapter(this,Emails,Messages);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        sendGlobalChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextGlobalChat.getText().toString().trim().length()>0){
                    String pushID = myfire.push().getKey();
                    DatabaseReference pushGlobalMessage = myfire.child(pushID);
                    Date todaysDate = new Date();
                    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = df2.format(todaysDate);
                    String SenderEmail = "Alien";
                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(GlobalChat.this);
                    if(acct!=null){
                        SenderEmail = acct.getEmail();
                    }

                    pushGlobalMessage.child("Date").setValue(date);
                    pushGlobalMessage.child("EMAIL").setValue(SenderEmail);
                    pushGlobalMessage.child("Message").setValue(editTextGlobalChat.getText().toString());
                    editTextGlobalChat.setText(null);
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

        myfire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Emails.add(dataSnapshot.child("EMAIL").getValue(String.class));
                Messages.add(dataSnapshot.child("Message").getValue(String.class));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
