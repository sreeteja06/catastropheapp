package com.example.sreet.learning;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView textView;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle("Calendar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.addCalenderEvent);
        textView = (TextView) findViewById(R.id.dayDetailsid);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        textView.setText("Nothing important on this day "+ss.format(date));
        textView.setVisibility(View.VISIBLE);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                textView.setText("Nothing important on this day "+dayOfMonth+"-"+month+"-"+year);
                textView.setVisibility(View.VISIBLE);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(CalendarActivity.this);
                dialog.setContentView(R.layout.calendar_event_adder);
                dialog.setTitle("Add Calendar Event");
                CardView submitCal = (CardView) dialog.findViewById(R.id.submitCalendarEvent);
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
                submitCal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
}
