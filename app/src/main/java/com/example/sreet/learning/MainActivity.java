package com.example.sreet.learning;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static MainActivity mInstance;
    private final int REQUEST_CODE = 1;
    GoogleApiClient mGoogleApiClient;
    FirebaseAnalytics mFirebaseAnalytics;
    FloatingActionButton Globalchat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CardView Attendance,Links,Activities,TimeTable,Notices,SavedThings;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String extstoragedir = Environment.getExternalStorageDirectory().toString();
        File fol = new File(extstoragedir, "Ifhe");
        File folder=new File(fol,"Documents");
        if(!folder.exists()) {
            boolean bool = folder.mkdirs();
        }

        //defining cards
        Attendance = (CardView) findViewById(R.id.attendanceID);
        Links = (CardView) findViewById(R.id.linksid);
        Activities = (CardView) findViewById(R.id.activitiesid);
        TimeTable = (CardView) findViewById(R.id.timetableid);
        Notices = (CardView) findViewById(R.id.noticesid);
        SavedThings = (CardView) findViewById(R.id.SavedThings);
        Globalchat = (FloatingActionButton) findViewById(R.id.globalChatButton);



        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("tag","Permission is granted");
            //File write logic here
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Error loging in", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        //add click listener to the cards
        Attendance.setOnClickListener(this);
        Links.setOnClickListener(this);
        Activities.setOnClickListener(this);
        TimeTable.setOnClickListener(this);
        Notices.setOnClickListener(this);
        SavedThings.setOnClickListener(this);
        Globalchat.setOnClickListener(this);

        FirebaseMessaging.getInstance().subscribeToTopic("notifications");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
        MenuItem Fav = menu.findItem(R.id.saveNOtice);
        Fav.setVisible(false);
        MenuItem suggestonsItem = menu.findItem(R.id.suggestionsMenu);
        suggestonsItem.setVisible(true);
        this.invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorite:
                Intent intent = new Intent(this,idCard.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent i = new Intent(this,about.class);
                startActivity(i);
                break;

            case  R.id.sis:
                Intent intent3 = new Intent(this,WebView_SIS.class);startActivity(intent3);break;

            case R.id.LogOut:
                FirebaseAuth mauth = FirebaseAuth.getInstance();
                mauth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {

                            }
                        });
                FirebaseAuth.getInstance().signOut();
                Intent sign = new Intent(this,SignIn.class);
                startActivity(sign);
                finish();
                break;
            case R.id.suggestionsMenu:
                Intent in = new Intent(this,suggestions.class);startActivity(in);break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.timetableid: i = new Intent(this,TimeTableSetter.class);startActivity(i);break;
            case R.id.activitiesid: i = new Intent(this,Events.class);startActivity(i);break;
            case R.id.attendanceID: i = new Intent(this,CalendarActivity.class);startActivity(i);break;
            case R.id.linksid: i = new Intent(this,notes.class);startActivity(i);break;
            case R.id.noticesid: i = new Intent(this, com.example.sreet.learning.Notices.class);startActivity(i);break;
            case R.id.SavedThings: i = new Intent(this,SavedThings.class);startActivity(i);break;
            case R.id.globalChatButton: i = new Intent(this,GlobalChat.class);startActivity(i);break;
            default: break;
        }
    }




}