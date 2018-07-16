package com.example.sreet.learning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

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
    //   EditText search;
    DocumentAdapter dA;

    FloatingActionButton b1;
    RecyclerView recyclerView;
    String personEmail;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("Year"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        dataref.keepSynced(true);
        // search = findViewById(R.id.Searchaction);

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
                WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(Notes1.this, LinearLayoutManager.HORIZONTAL, false);
                wrapContentLinearLayoutManager.setReverseLayout(true);
                wrapContentLinearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
                registerForContextMenu(recyclerView);
                progressDialog.dismiss();
                dA.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
     /*   search.addTextChangedListener(new TextWatcher() {
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
*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        // MenuItem item= menu.findItem(R.id.LogOut);
        //MenuItem item1 = menu.findItem(R.id.saveNOtice);
        //item1.setVisible(false);
        //item.setVisible(false);
        this.invalidateOptionsMenu();
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }

        });

        return super.onCreateOptionsMenu(menu);

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