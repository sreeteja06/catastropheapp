package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class savedNotices extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private SharedPreferences sharedPreferences;
    List<String> year;
    List<String> Descript_array_list;
    List<String> Date;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,listViewClick.class);
        String positionDate = Date.get(position);
        intent.putExtra("Date",positionDate);
        String positionDes = Descript_array_list.get(position);
        intent.putExtra("Description",positionDes);
        String postitionYear = year.get(position);
        intent.putExtra("Year",postitionYear);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_notices);
        setTitle("Saved Notices");
        sharedPreferences = this.getSharedPreferences("com.example.sreet.learning", Context.MODE_PRIVATE);
        String noticesValueString = sharedPreferences.getString("number of saved notices","1");
        int noticesValue = Integer.parseInt(noticesValueString);
        listView = (ListView) findViewById(R.id.listview);
        year = new ArrayList<String>();
        Descript_array_list = new ArrayList<String>();
        Date = new ArrayList<String >();
        for(int i=1;i<=noticesValue;i++){
            year.add(sharedPreferences.getString(String.valueOf(i)+"year","firstYear"));
            Descript_array_list.add(sharedPreferences.getString(String.valueOf(i)+"Descript","descript"));
            Date.add(sharedPreferences.getString(String.valueOf(i)+"Date","date"));
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Descript_array_list);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}