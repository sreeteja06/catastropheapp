package com.example.sreet.learning;


import android.annotation.SuppressLint;
import android.app.AlarmManager;

import android.app.Dialog;

import android.app.NotificationManager;

import android.app.PendingIntent;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;

import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Environment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.support.v7.widget.CardView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.ContextMenu;

import android.view.Menu;

import android.view.MenuInflater;

import android.view.MenuItem;

import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;

import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.DatePicker;

import android.widget.EditText;

import android.widget.ListView;

import android.widget.TextView;

import android.widget.TimePicker;

import android.widget.Toast;


import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Calendar;

import java.util.Date;

import java.util.HashSet;

import java.util.List;

import java.util.Set;
import java.util.concurrent.TimeUnit;


public class Timetableconfig extends AppCompatActivity {

    RecyclerView reView;
    ProgressDialog progressDialog;


    TimetableAdapter tA;

    List<TimeDataclass> list;

    String sub, time;


    DatabaseReference databaseReference;

    Calendar toofix, toofix2;

    int daysetter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        list = new ArrayList<TimeDataclass>();
        String personEmail = null,name;
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personEmail = acct.getEmail();
            name = acct.getDisplayName();
            if(personEmail.equalsIgnoreCase("itstechclub@gmail.com")||personEmail.equalsIgnoreCase("ppraneeth294@gmail.com")||personEmail.equalsIgnoreCase("samalakrishna7@gmail.com")||personEmail.equalsIgnoreCase("sripad2708@gmail.com")){
                //LinearLayout sendNotice = (LinearLayout) findViewById(R.id.sendNoticeLayout);
                //.setVisibility(View.VISIBLE);
            }
        }



        setContentView(R.layout.activity_timetableconfig);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        // progressDialog.setMax(100);
        progressDialog.setMessage("Getting the data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reView = findViewById(R.id.Recycleviewtable);

        reView.setLayoutManager(new LinearLayoutManager(this));
        tA = new TimetableAdapter(this, list, getIntent().getStringExtra("dayofweek")+" "+personEmail.replace(".","0"));
        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(Timetableconfig.this, LinearLayoutManager.HORIZONTAL, false);
        wrapContentLinearLayoutManager.setReverseLayout(true);
        wrapContentLinearLayoutManager.setStackFromEnd(true);
        reView.setLayoutManager(wrapContentLinearLayoutManager);
        reView.setAdapter(tA);

        databaseReference = FirebaseDatabase.getInstance().getReference("users/TimetablePersonal/"+personEmail.replace(".","0")+"/"+ getIntent().getStringExtra("dayofweek"));

        databaseReference.keepSynced(true);
        progressDialog.dismiss();


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TimeDataclass tclass = dataSnapshot.getValue(TimeDataclass.class);
                list.add(tclass);
                tA.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // registerForContextMenu(l);

        daysetter = getIntent().getIntExtra("daydetails", 0);
        String LayoutTitle = getIntent().getStringExtra("dayofweek");
        setTitle((LayoutTitle.substring(9, LayoutTitle.length())));
//Log.i("test",String.valueOf(daysetter));

    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.timetableadder, menu);


        MenuItem item = menu.findItem(R.id.AddTableId);

        item.setVisible(true);

        this.invalidateOptionsMenu();


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

            final EditText e4 = dialog.findViewById(R.id.Roomnameid);

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

                            e3.setText( String.valueOf(hourpost) + ":" + String.valueOf(Minpost));


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

                    if (e1.getText().toString().length() != 0 && e2.getText().toString().length() != 0 && e3.getText().toString().length() != 0 && e4.getText().toString().length() != 0 && e3.getText().toString() != null && e2.getText().toString()!= null) {


                        //list.add(e1.getText().toString().trim() + " From " + e2.getText().toString() + e3.getText().toString());

                        String key = databaseReference.push().getKey();
                        TimeDataclass tclass = new TimeDataclass(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString(),key);
                        databaseReference.child(key).setValue(tclass);
                        tA.notifyDataSetChanged();


                        sub = e1.getText().toString().trim();

                        time = e2.getText().toString().trim() +" to" + e3.getText().toString();


                        // saveInfo(list);

                        //arrayAdapter.notifyDataSetChanged();


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


    List<PendingIntent> plist;

    void setReminder()

    {
        plist = new ArrayList<>();
        // Toast.makeText(Timetableconfig.this,"Reminder Set at"+Selectedhour+" :"+SelectedMin,Toast.LENGTH_SHORT).show();
        Intent alertIntent = new Intent(this, Notify.class);
        alertIntent.putExtra("type", "timeTable");
        alertIntent.putExtra("Body", sub + " from " + time);
        Log.i("time", String.valueOf(toofix.getTimeInMillis()));

        AlarmManager alarmManager = (AlarmManager) getSystemService(Timetableconfig.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, list.size(), alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // alarmManager.setExact(AlarmManager.RTC_WAKEUP, toofix.getTimeInMillis(), pendingIntent);
        plist.add(pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, toofix.getTimeInMillis() - 300000, 7 * 24 * 60 * 60 * 1000, plist.get(plist.size() - 1));

    }


    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context, int horizontal, boolean b) {
            super(context);
        }

        //... constructor
        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probe", "meet a IOOBE in RecyclerView");
            }
        }
    }


}

