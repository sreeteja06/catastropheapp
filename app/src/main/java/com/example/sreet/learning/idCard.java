package com.example.sreet.learning;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

public class idCard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        setTitle("ID");
        getSupportActionBar().hide();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            TextView email = (TextView)findViewById(R.id.studentEmail);
            email.setText(personEmail);
            if(personPhoto!=null){
                ImageView profile = (ImageView)findViewById(R.id.imageView3);
                Picasso.with(this).load(personPhoto).into(profile);
            }
            TextView name = (TextView) findViewById(R.id.studentName);
            name.setText(personName);
        }
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.sreet.learning", Context.MODE_PRIVATE);
        TextView enroll_tv = (TextView) findViewById(R.id.studentEnroll);
        TextView block_tv = (TextView) findViewById(R.id.course);
        TextView batch_tv = (TextView) findViewById(R.id.studentBatch);
        TextView dob_tv = (TextView) findViewById(R.id.studentDOB);
        enroll_tv.setText(sharedPreferences.getString("enroll", "16stuhh0211"));
        block_tv.setText(sharedPreferences.getString("bolck", "bolck"));
        batch_tv.setText(sharedPreferences.getString("batch", "batch"));
        dob_tv.setText(sharedPreferences.getString("DOB", "DOB"));
    }
}