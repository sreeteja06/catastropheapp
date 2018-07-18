package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalChat extends AppCompatActivity {
    DatabaseReference myfire;
    EditText editTextGlobalChat;
    ImageButton sendGlobalChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);
        setTitle("Global Chat");

        editTextGlobalChat = (EditText) findViewById(R.id.editTextGlobalChat);
        sendGlobalChatButton = (ImageButton) findViewById(R.id.sendGlobalChatButton);
        myfire = FirebaseDatabase.getInstance().getReference().child("GlobalChat");
        myfire.keepSynced(true);

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
                }
            }
        });
    }
}
