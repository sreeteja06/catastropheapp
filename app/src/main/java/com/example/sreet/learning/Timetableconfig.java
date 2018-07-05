package com.example.sreet.learning;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Timetableconfig extends AppCompatActivity {
    ListView l;
    ArrayAdapter<String> arrayAdapter;
    List<String> list,updater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetableconfig);
        l = findViewById(R.id.listviewtable);
        list = new ArrayList<>();
        updateInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timetableadder,menu);
        arrayAdapter = new ArrayAdapter<>(Timetableconfig.this,
                android.R.layout.simple_list_item_1,list);


        MenuItem item = menu.findItem(R.id.AddTableId);
        item.setVisible(true);
        this.invalidateOptionsMenu();
       // Button button = (Button)item.getActionView();
       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Timetableconfig.this);
                dialog.setContentView(R.layout.timetablepicker);
                dialog.setTitle("Set details");
                EditText e1 = v.findViewById(R.id.editText3);
                EditText e2 = v.findViewById(R.id.editText4);
                EditText e3 = v.findViewById(R.id.editText5);
                Button b1 = v.findViewById(R.id.button2);
                Button b2 = v.findViewById(R.id.button5);
                Button b3 = v.findViewById(R.id.button6);

            }
        });*/
       l.setAdapter(arrayAdapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.AddTableId)
        {
            final Dialog dialog = new Dialog(Timetableconfig.this);
            dialog.setContentView(R.layout.timetablepicker);
            dialog.setTitle("Set details");
           final EditText e1 = dialog.findViewById(R.id.editText3);
            final TextView e2 = dialog.findViewById(R.id.editText4);
            final TextView e3 = dialog.findViewById(R.id.editText5);
            Button b1 = dialog.findViewById(R.id.button2);
            Button b2 = dialog.findViewById(R.id.button5);
            Button b3 = dialog.findViewById(R.id.button6);
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Timetableconfig.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            e2.setText( selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();


                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Timetableconfig.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            e3.setText( selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();


                }
            });

           // SharedPreferences preferences = getSharedPreferences("timetable",MODE_PRIVATE);
            //SharedPreferences.Editor editor = preferences.edit();
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.add(e1.getText().toString().trim()+" from "+e2.getText().toString().trim()+" to "+e3.getText().toString().trim());
                    saveInfo(list);
                   l.setAdapter(arrayAdapter);
                    dialog.dismiss();

                                    }
            });




        }
        l.setAdapter(arrayAdapter);
        return super.onOptionsItemSelected(item);
    }
void saveInfo(List<String> a)
{
    SharedPreferences preferences = getSharedPreferences("Timetablepersonal",MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    Set set = new HashSet(a);
    editor.putStringSet("TimeData",  set);
    editor.apply();
}
void updateInfo()
{
    SharedPreferences preferences = getSharedPreferences("Timetablepersonal",MODE_PRIVATE);
Set<String> check = new HashSet<>();
check.add("check");
    Set again = preferences.getStringSet("TimeData",check);
    if(again.contains("check"))
    {}
    else
        list = new ArrayList(again);

}

}
