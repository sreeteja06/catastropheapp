package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;

public class SavedThings extends AppCompatActivity implements View.OnClickListener{

    private CardView SavedNotices, SavedNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_things);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SavedNotices = (CardView) findViewById(R.id.SavedNotice);
        SavedNotes = (CardView) findViewById(R.id.SavedNotes);


        SavedNotices.setOnClickListener(this);
        SavedNotes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId()){
            case R.id.SavedNotice: i = new Intent(this,savedNotices.class); startActivity(i); break;
            case R.id.SavedNotes: i = new Intent(this,savedNotes.class); startActivity(i); break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
        MenuItem item1 = menu.findItem(R.id.saveNOtice);
        item1.setVisible(false);
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
            case R.id.LogOut:
                FirebaseAuth.getInstance().signOut();
                Intent sign = new Intent(this,SignIn.class);
                startActivity(sign);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
