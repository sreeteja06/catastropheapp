package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class SavedThings extends AppCompatActivity implements View.OnClickListener{

    private CardView SavedNotices, SavedNotes, SavedTimeTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_things);

        SavedNotices = (CardView) findViewById(R.id.SavedNotice);
        SavedNotes = (CardView) findViewById(R.id.SavedNotes);
        SavedTimeTable = (CardView) findViewById(R.id.SavedTimeTable);

        SavedNotices.setOnClickListener(this);
        SavedNotes.setOnClickListener(this);
        SavedTimeTable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId()){
            case R.id.SavedNotice: i = new Intent(this,savedNotices.class); startActivity(i); break;
            case R.id.SavedNotes: i = new Intent(this,savedNotes.class); startActivity(i); break;
            case R.id.SavedTimeTable: i = new Intent(this,savedTimeTable.class); startActivity(i); break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
        MenuItem item= menu.findItem(R.id.savedNotices);
        MenuItem item1 = menu.findItem(R.id.saveNOtice);
        item1.setVisible(false);
        item.setVisible(false);
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
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
