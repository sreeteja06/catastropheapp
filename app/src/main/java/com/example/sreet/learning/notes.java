package com.example.sreet.learning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class notes extends AppCompatActivity implements View.OnClickListener{

    CardView firstyear,secondyear,thirdyear,forthyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_notes);

        firstyear = (CardView) findViewById(R.id.firstyearid);
        secondyear = (CardView) findViewById(R.id.secondyearid);
        thirdyear = (CardView) findViewById(R.id.thirdyearid);
        forthyear = (CardView) findViewById(R.id.forthyearid);
        //setting click listeners to the cards
        firstyear.setOnClickListener(this);
        secondyear.setOnClickListener(this);
        thirdyear.setOnClickListener(this);
        forthyear.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
        MenuItem item= menu.findItem(R.id.saveNOtice);
        item.setVisible(false);
        this.invalidateOptionsMenu();
        return true;
    }


    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.firstyearid:{
                i = new Intent(this,notesList.class);
                i.putExtra("Year","firstYear");
                startActivity(i);
                break;
            }
            case R.id.secondyearid:{
                i = new Intent(this,notesList.class);
                i.putExtra("Year","secondYear");
                startActivity(i);
                break;
            }
            case R.id.thirdyearid:{
                i = new Intent(this,notesList.class);
                i.putExtra("Year","thirdYear");
                startActivity(i);
                break;
            }
            case R.id.forthyearid:{
                i = new Intent(this,fourthYearNotices.class);
                i.putExtra("Year","fourthYear");
                startActivity(i);
                break;
            }
        }
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
