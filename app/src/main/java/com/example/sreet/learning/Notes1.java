package com.example.sreet.learning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notes1 extends AppCompatActivity {
    DatabaseReference dataref;
    List<NotesDataClass> Filedata;
    EditText search;
    DocumentAdapter dA;
    Button b1;
    RecyclerView recyclerView;
    String personEmail;
   ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        // progressDialog.setMax(100);
        progressDialog.setMessage("Getting the data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

      //  progressBar.setVisibility(View.VISIBLE);
        //progressBar.setProgress(Window.FEATURE_INDETERMINATE_PROGRESS);


        setContentView(R.layout.activity_notes);
        b1 = findViewById(R.id.fileUploadButton);
        b1.setVisibility(View.INVISIBLE);

        dataref = FirebaseDatabase.getInstance().getReference("users/Notes/" + getIntent().getStringExtra("Year"));
        search = findViewById(R.id.Searchaction);

        recyclerView = findViewById(R.id.Documentrecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Filedata = new ArrayList<>();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personEmail = acct.getEmail();
            if (personEmail.equalsIgnoreCase("itstechclub@gmail.com") || personEmail.equalsIgnoreCase("ppraneeth294@gmail.com") || personEmail.equalsIgnoreCase("samalakrishna7@gmail.com") || personEmail.equalsIgnoreCase("sripad2708@gmail.com")) {
                //LinearLayout sendNotice = (LinearLayout) findViewById(R.id.sendNoticeLayout);
                b1.setVisibility(View.VISIBLE);
            }
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                Filedata.removeAll(Filedata);
                Intent i = new Intent(Notes1.this, Documentupdate.class);
                i.putExtra("yeardetails", getIntent().getStringExtra("Year"));
                startActivity(i);
            }
        });
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ps : dataSnapshot.getChildren()) {
                    NotesDataClass nclass = ps.getValue(NotesDataClass.class);
                    Filedata.add(nclass);
                }

                dA = new DocumentAdapter(Notes1.this, Filedata);


                recyclerView.setAdapter(dA);
                registerForContextMenu(recyclerView);
                progressDialog.dismiss();
                dA.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });


    }


    public void filter(String text) {
        List<NotesDataClass> temp = new ArrayList<>();
        for (NotesDataClass d : Filedata) {
            if (d.getName().toLowerCase().contains(text)) {
                temp.add(d);
            }
        }
        dA.updateList(temp);
    }
}
