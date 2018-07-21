package com.example.sreet.learning;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat Monthformat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());

    private TextView textView;
    private FloatingActionButton floatingActionButton;
    private String SelectedDate;
    private Date dateClickedGlobal;
    private DatabaseReference myfire;
    private ArrayList<Event> CalendarEvents = new ArrayList<>();
    private ListView calendaraEventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (TextView) findViewById(R.id.dayDetailsid);
        calendaraEventsList = (ListView)findViewById(R.id.calendarEventsList);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addCalenderEvent);
        floatingActionButton.setVisibility(View.INVISIBLE);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        final Date date = new Date();
        SelectedDate =  ss.format(date);
        setTitle(Monthformat.format(date));
        Long presentDateMill = date.getTime();
        List<Event> events = compactCalendarView.getEvents(presentDateMill);
        if(!events.isEmpty())
        {
            textView.setVisibility(View.GONE);
            ArrayList<String> CalendarData = new ArrayList<>();
            for(int i = 0;i<events.size();i++){
                CalendarData.add(events.get(i).getData().toString());
            }
            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,CalendarData);
            calendaraEventsList.setItemsCanFocus(true);
            calendaraEventsList.setAdapter(mArrayAdapter);
            calendaraEventsList.setVisibility(View.VISIBLE);
        }
        else {
            calendaraEventsList.setVisibility(View.INVISIBLE);
            textView.setText("No Event planned on " + SelectedDate);
            textView.setVisibility(View.VISIBLE);
        }

        Firebase.setAndroidContext(this);
        //myfire = new Firebase("https://learning-2b334.firebaseio.com/calendar");
        myfire = FirebaseDatabase.getInstance().getReference().child("calendar");
        myfire.keepSynced(true);
        Event ev1 = new Event(Color.RED,1531872000000L,"Testing date");
        compactCalendarView.addEvent(ev1);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                dateClickedGlobal = dateClicked;
                SimpleDateFormat dayClickFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                SelectedDate = dayClickFormat.format(dateClicked);

                    Long dateClickedMill = dateClicked.getTime();
                    List<Event> events = compactCalendarView.getEvents(dateClickedMill);
                    if(!events.isEmpty())
                    {
                        textView.setVisibility(View.GONE);
                        ArrayList<String> CalendarData = new ArrayList<>();
                        for(int i = 0;i<events.size();i++){
                            CalendarData.add(events.get(i).getData().toString());
                        }
                        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(CalendarActivity.this,android.R.layout.simple_list_item_1,CalendarData);
                        calendaraEventsList.setAdapter(mArrayAdapter);
                        calendaraEventsList.setAdapter(mArrayAdapter);
                        calendaraEventsList.setVisibility(View.VISIBLE);
                    }
                    else {
                        calendaraEventsList.setVisibility(View.INVISIBLE);
                        textView.setText("No Event planned on " + SelectedDate);
                        textView.setVisibility(View.VISIBLE);
                    }


                if(dateClickedGlobal!=null) {
                    if (dateClickedGlobal.after(date) ) {
                        floatingActionButton.setVisibility(View.VISIBLE);
                        floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog = new Dialog(CalendarActivity.this);
                                dialog.setContentView(R.layout.calendar_event_adder);
                                dialog.setTitle("Add Calendar Event");
                                CardView submitCal = (CardView) dialog.findViewById(R.id.submitCalendarEvent);
                                final EditText eventDescript = (EditText) dialog.findViewById(R.id.calendarEventDescription);
                                final Spinner dropdown = (Spinner) dialog.findViewById(R.id.BatchSpinner);
                                String items[] = new String[]{"Common", "BBA", "LLB", "B.Tech"};
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(CalendarActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                if (dropdown != null) {
                                    dropdown.setAdapter(adapter);
                                }
                                dialog.show();
                                dialog.setCanceledOnTouchOutside(true);
                                if (eventDescript != null) {
                                    submitCal.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int EventColor;
                                            String dropdownSelected = dropdown.getSelectedItem().toString();

                                            if (dropdownSelected.equals("B.Tech")) {
                                                EventColor = Color.RED;
                                            } else if (dropdownSelected.equals("BBA")) {
                                                EventColor = Color.GREEN;
                                            } else if (dropdownSelected.equals("LLB")) {
                                                EventColor = Color.YELLOW;
                                            } else if (dropdownSelected.equals("Common")) {
                                                EventColor = Color.BLUE;
                                            } else {
                                                EventColor = Color.BLACK;
                                            }

                                            Long dateClickedMills = dateClickedGlobal.getTime();
                                            Event extra = new Event(EventColor, dateClickedMills, eventDescript.getText().toString());
                                            String id = myfire.push().getKey();
                                            myfire.child(id).setValue(extra);
                                            dialog.hide();
                                        }
                                    });
                                }
                            }
                        });
                    }
                    else if(dateClickedGlobal.before(date)){
                        floatingActionButton.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setTitle(Monthformat.format(firstDayOfNewMonth));
            }
        });



       myfire.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {
               int color = dataSnapshot.child("color").getValue(Integer.class);
               String data = dataSnapshot.child("data").getValue(String.class);
               Long timeInMillis = dataSnapshot.child("timeInMillis").getValue(Long.class);

               Event event = new Event(color,timeInMillis,data);
               CalendarEvents.add(event);

               compactCalendarView.addEvent(new Event(color,timeInMillis,data));
               setReminder(timeInMillis,data);
           }

           @Override
           public void onChildChanged(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildRemoved(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }
    void setReminder(Long timeinMills,String Data)
    {
        // Toast.makeText(Timetableconfig.this,"Reminder Set at"+Selectedhour+" :"+SelectedMin,Toast.LENGTH_SHORT).show();
        Intent alertIntent = new Intent(this, Notify.class);
        alertIntent.putExtra("type", "calendar");
        alertIntent.putExtra("Body", Data);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Timetableconfig.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeinMills, pendingIntent);
    }
}
