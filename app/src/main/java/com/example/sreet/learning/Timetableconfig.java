package com.example.sreet.learning;


import android.app.AlarmManager;

import android.app.Dialog;

import android.app.NotificationManager;

import android.app.PendingIntent;

import android.app.TimePickerDialog;

import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Environment;

import android.support.v4.app.NotificationCompat;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.support.v7.widget.CardView;

import android.util.Log;

import android.view.ContextMenu;

import android.view.Menu;

import android.view.MenuInflater;

import android.view.MenuItem;

import android.view.View;

import android.widget.AdapterView;

import android.widget.ArrayAdapter;

import android.widget.Button;

import android.widget.DatePicker;

import android.widget.EditText;

import android.widget.ListView;

import android.widget.TextView;

import android.widget.TimePicker;

import android.widget.Toast;


import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Calendar;

import java.util.Date;

import java.util.HashSet;

import java.util.List;

import java.util.Set;


public class Timetableconfig extends AppCompatActivity {

    ListView l;

    ArrayAdapter<String> arrayAdapter;

    List<String> list;

    String sub, time;

    // int Selectedhour;

    // int SelectedMin;

    Calendar toofix, toofix2;

    int daysetter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timetableconfig);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        l = findViewById(R.id.listviewtable);

        list = new ArrayList<>();

        updateInfo();

        arrayAdapter = new ArrayAdapter<>(Timetableconfig.this,

                android.R.layout.simple_list_item_1, list);

        l.setAdapter(arrayAdapter);

        registerForContextMenu(l);

        daysetter = getIntent().getIntExtra("daydetails", 0);
        String LayoutTitle = getIntent().getStringExtra("dayofweek");
        setTitle((LayoutTitle.substring(9,LayoutTitle.length())));
//Log.i("test",String.valueOf(daysetter));

    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.timetableadder, menu);


        MenuItem item = menu.findItem(R.id.AddTableId);

        item.setVisible(true);

        this.invalidateOptionsMenu();


        arrayAdapter.notifyDataSetChanged();

        return true;

    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.AddTableId) {

            final Dialog dialog = new Dialog(Timetableconfig.this);

            dialog.setContentView(R.layout.timetablepicker);

            dialog.setTitle("Set details");

            final EditText e1 = dialog.findViewById(R.id.Subnameid);

            final TextView e2 = dialog.findViewById(R.id.starttimeid);

            final TextView e3 = dialog.findViewById(R.id.endtimeid);

            CardView b1 = dialog.findViewById(R.id.start);

            CardView b2 = dialog.findViewById(R.id.end);

            CardView b3 = dialog.findViewById(R.id.submit);

            dialog.show();

            dialog.setCanceledOnTouchOutside(true);


            b1.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    Calendar mcurrentTime = Calendar.getInstance();

                    final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);

                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    toofix = Calendar.getInstance();

                    toofix2 = Calendar.getInstance();

                    // final Date date = toofix.getTime();'if (cal.get(CalendarActivity.DAY_OF_WEEK) != dayOfWeek) {


                    TimePickerDialog mTimePicker;

                    mTimePicker = new TimePickerDialog(Timetableconfig.this, new TimePickerDialog.OnTimeSetListener() {

                        @Override

                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            e2.setVisibility(View.VISIBLE);

                            e2.setText(selectedHour + ":" + selectedMinute);

                            // Log.i("check",String.valueOf(daysetter));

                            // Log.i("check",date.toString());


                            if (toofix.get(Calendar.DAY_OF_WEEK) != daysetter) {

                                toofix.add(Calendar.DAY_OF_MONTH, (daysetter + 7 - toofix.get(Calendar.DAY_OF_WEEK)) % 7);

                            } else {

                                int minOfDay = toofix.get(Calendar.HOUR_OF_DAY) * 60 + toofix.get(Calendar.MINUTE);

                                if (minOfDay >= selectedHour * 60 + selectedMinute)

                                    toofix.add(Calendar.DAY_OF_MONTH, 7); // Bump to next week

                            }

                            toofix.set(Calendar.HOUR_OF_DAY, selectedHour);

                            toofix.set(Calendar.MINUTE, selectedMinute);

                            toofix.set(Calendar.SECOND, 0);

                            toofix.set(Calendar.MILLISECOND, 0);

                            Long a = toofix.getTimeInMillis() + 3000000;

                            toofix2.setTimeInMillis(a);

                            int hourpost = toofix2.get(Calendar.HOUR_OF_DAY);

                            int Minpost = toofix2.get(Calendar.MINUTE);

                            e3.setText(" to " + String.valueOf(hourpost) + ":" + String.valueOf(Minpost));


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

                    final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);

                    final int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;

                    mTimePicker = new TimePickerDialog(Timetableconfig.this, new TimePickerDialog.OnTimeSetListener() {

                        @Override

                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            String concat = String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute);

                            e3.setVisibility(View.VISIBLE);

                            e3.setText(concat.trim());


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

                    if (e1.getText().toString().length() != 0 && e2.getText().toString().length() != 0 && e3.getText().toString().length() != 0) {

                        list.add(e1.getText().toString().trim() + " From " + e2.getText().toString() + e3.getText().toString());

                        sub = e1.getText().toString().trim();

                        time = e2.getText().toString().trim() + e3.getText().toString();


                        saveInfo(list);

                        arrayAdapter.notifyDataSetChanged();


                        //int alertTime = ((Selectedhour*60*60)+(SelectedMin*60))*1000;

                        setReminder();


                        dialog.dismiss();
                    } else

                    {

                        Toast.makeText(Timetableconfig.this, "Please fill all the details", Toast.LENGTH_SHORT).show();

                    }


                }

            });


        }

        // l.setAdapter(arrayAdapter);

        return super.onOptionsItemSelected(item);

    }


    void saveInfo(List<String> a) {

        SharedPreferences preferences = getSharedPreferences("Timetablepersonal", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        Set set = new HashSet(a);

        editor.putStringSet(getIntent().getStringExtra("dayofweek"), set);

        editor.apply();

    }


    void updateInfo() {

        SharedPreferences preferences = getSharedPreferences("Timetablepersonal", MODE_PRIVATE);

        Set<String> check = new HashSet<>();

        check.add("check");

        Set again = preferences.getStringSet(getIntent().getStringExtra("dayofweek"), check);

        if (again.contains("check")) {

            list = new ArrayList();

        } else

            list = new ArrayList(again);


    }


    void setReminder()

    {
        // Toast.makeText(Timetableconfig.this,"Reminder Set at"+Selectedhour+" :"+SelectedMin,Toast.LENGTH_SHORT).show();
        Intent alertIntent = new Intent(this, Notify.class);
        alertIntent.putExtra("type","timeTable");
        alertIntent.putExtra("Body", sub+" from "+time);
        Log.i("time", String.valueOf(toofix.getTimeInMillis()));
        AlarmManager alarmManager = (AlarmManager) getSystemService(Timetableconfig.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, list.size(), alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // alarmManager.setExact(AlarmManager.RTC_WAKEUP, toofix.getTimeInMillis(), pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, toofix.getTimeInMillis() - 300000, (7 * 24 * 60 * 60), pendingIntent);

    }


    @Override

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);

    }


    @Override

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())

        {

            case R.id.delete: {

                String s = list.get(info.position);

                list.remove(info.position);

                arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getSharedPreferences("Timetablepersonal", MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                Set<String> check = new HashSet<>();

                check.add("check");

                Set again = sharedPreferences.getStringSet(getIntent().getStringExtra("dayofweek"), check);

                if (again.contains(s))

                {

                    again.remove(s);

                    editor.putStringSet(getIntent().getStringExtra("dayofweek"), again);

                    editor.apply();

                    Toast.makeText(Timetableconfig.this, "Succesfully deleted", Toast.LENGTH_SHORT).show();

                } else

                {

                    Toast.makeText(Timetableconfig.this, "Unable to delete", Toast.LENGTH_SHORT).show();


                }

            }

            default:

            {


            }


        }


        return super.onContextItemSelected(item);

    }

}