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
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    String sub,time;
   // int Selectedhour;
   // int SelectedMin;
     Calendar toofix;
int daysetter ;
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
            final EditText e1 = dialog.findViewById(R.id.editText3);
            final TextView e2 = dialog.findViewById(R.id.editText4);
            final TextView e3 = dialog.findViewById(R.id.editText5);
            CardView b1 = dialog.findViewById(R.id.start);
            CardView b2 = dialog.findViewById(R.id.end);
            CardView b3 = dialog.findViewById(R.id.submit);
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    toofix = Calendar.getInstance();
                   // final Date date = toofix.getTime();'if (cal.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {




                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Timetableconfig.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            e2.setVisibility(View.VISIBLE);
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
                    final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    final int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Timetableconfig.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            e3.setVisibility(View.VISIBLE);
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
                    if((e1.getText().toString().trim().length()>0)||(e2.getText().toString().trim().length()>0)||(e3.getText().toString().trim().length()>0)){
                        list.add(e1.getText().toString().trim() + " from " + e2.getText().toString().trim() + " to " + e3.getText().toString().trim());
                        saveInfo(list);
                        l.setAdapter(arrayAdapter);
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(Timetableconfig.this, "Fill all the forms", Toast.LENGTH_SHORT).show();
                    }

                    }
            });

                    dialog.dismiss();}
                    else
                    {
                        Toast.makeText(Timetableconfig.this,"Please select all the details",Toast.LENGTH_SHORT).show();
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
       alertIntent.putExtra("subjectname",sub);
       alertIntent.putExtra("time",time);
//Log.i("time",String.valueOf(toofix.getTimeInMillis()));
       AlarmManager alarmManager = (AlarmManager) getSystemService(Timetableconfig.ALARM_SERVICE);
       PendingIntent pendingIntent = PendingIntent.getBroadcast(this, list.size(), alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);

      // alarmManager.setExact(AlarmManager.RTC_WAKEUP, toofix.getTimeInMillis(), pendingIntent);
       alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, toofix.getTimeInMillis()-1000,(7*AlarmManager.INTERVAL_DAY), pendingIntent);

   }

}


/*
// Long finalarray[] = new Long[list.size()];

      /* for( i=0;i<list.size(); i++) {
//int h;
           pendingIntent[i] = PendingIntent.getBroadcast(this, i, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
           //   String s[] = list.get(i).split(" ");
           //          h = s.length;
       }
         // long timeofalarm = Long.parseLong(s[h-1]);
        */ // finalarray[i] = timeofalarm;
// alarmManager.setExact(AlarmManager.RTC_WAKEUP, toofix.getTimeInMillis(), pendingIntent[i]);
// Log.i("time1", String.valueOf(timeofalarm));
//Log.i("tie2", String.valueOf(toofix.getTimeInMillis()));

/*for(int j = 0;j<finalarray.length;j++)
{
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, finalarray[j], pendingIntent[j]);

}*/
/* */