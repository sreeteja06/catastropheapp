package com.example.sreet.learning;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class calculateAttendance extends AppCompatActivity {




    EditText classesTaken, classesAttended, totalClasses;
    TextView sickLeave;
    Button submit;
    int TakenValue, AttendedValue, totalValue;
    Button img;
    Text txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_attendance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        classesTaken = (EditText) findViewById(R.id.classesTaken);
        classesAttended = (EditText) findViewById(R.id.classesAttended);
        totalClasses = (EditText) findViewById(R.id.Totalclasses);
        sickLeave = (TextView) findViewById(R.id.sickLeave);
        submit = (Button) findViewById(R.id.button3);
        img=(Button) findViewById(R.id.button2);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                AlertDialog.Builder builder= new AlertDialog.Builder(calculateAttendance.this);
                builder.setTitle("information")
                        .setMessage("According to the Statistics Average total clases taken are 50 ")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float attendance;


                TakenValue = Integer.parseInt(classesTaken.getText().toString());
                AttendedValue = Integer.parseInt(classesAttended.getText().toString());
                totalValue = Integer.parseInt(totalClasses.getText().toString());
                attendance = (float)AttendedValue/TakenValue;
                attendance = attendance*100;
                if(attendance>75){
                    int i = 0;
                    for(i = TakenValue;i<totalValue;i++){
                        float flag = (float)AttendedValue/i;
                        int k = i+1;
                        flag = flag*100;
                        if(flag<=75){
                            break;
                        }
                    }
                    sickLeave.setText("Attendance: "+attendance+"\nSick leaves you can take: "+(i-TakenValue));
                }
                else{
                    //int i,j;
                    //for(i=TakenValue,j=AttendedValue;i<totalValue||j<totalValue;j++,i++){
                    //    float flag = (float)j/i;
                    //    int k = i+1;
                    //    flag = flag*100;
                    //    if(flag>75){
                    //        break;
                    //    }
                    //}
                    sickLeave.setText("Attendance:"+attendance+"\nYour Attendance is less than 75%");
                }
            }
        });

    }
    }