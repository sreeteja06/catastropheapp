package com.example.sreet.learning;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
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
    private Firebase myfire;
    private ArrayList<Event> CalendarEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (TextView) findViewById(R.id.dayDetailsid);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addCalenderEvent);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        Date date = new Date();
        SelectedDate =  ss.format(date);
        setTitle(Monthformat.format(date));
        textView.setText("No Event planned on "+SelectedDate);
        textView.setVisibility(View.VISIBLE);
        Firebase.setAndroidContext(this);
        myfire = new Firebase("https://learning-2b334.firebaseio.com/calendar");
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
                        textView.setText(events.get(0).getData().toString());
                        textView.setVisibility(View.VISIBLE);
                    }
                    else {
                        textView.setText("No Event planned on " + SelectedDate);
                        textView.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setTitle(Monthformat.format(firstDayOfNewMonth));
            }
        });


       floatingActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Dialog dialog = new Dialog(CalendarActivity.this);
               dialog.setContentView(R.layout.calendar_event_adder);
               dialog.setTitle("Add Calendar Event");
               CardView submitCal = (CardView) dialog.findViewById(R.id.submitCalendarEvent);
               final EditText eventDescript = (EditText) dialog.findViewById(R.id.calendarEventDescription);
               dialog.show();
               dialog.setCanceledOnTouchOutside(true);
               if(eventDescript!=null) {
                   submitCal.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Long dateClickedMills = dateClickedGlobal.getTime();
                           Event extra = new Event(Color.RED,dateClickedMills,eventDescript.getText().toString());
                           String id = myfire.push().getKey();
                           myfire.child(id).setValue(extra);
                           dialog.hide();
                       }
                   });
               }
          }
       });

       myfire.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               int color = dataSnapshot.child("color").getValue(Integer.class);
               String data = dataSnapshot.child("data").getValue(String.class);
               Long timeInMillis = dataSnapshot.child("timeInMillis").getValue(Long.class);
               compactCalendarView.addEvent(new Event(color,timeInMillis,data));
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(FirebaseError firebaseError) {

           }
       });



    }
}
