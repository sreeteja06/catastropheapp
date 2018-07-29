package com.example.sreet.learning;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {
    DatabaseReference dataref;
    List<NotesDataClass> Filedata;


    //   EditText search;
    DocumentAdapter dA;

    FloatingActionButton b1;
    RecyclerView recyclerView;
    String personEmail;
    EditText et;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main2, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Onstartprocess();

    }
    private void Onstartprocess(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(false);
        // progressDialog.setMax(100);
        progressDialog.setMessage("Getting the data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        et = getActivity().findViewById(R.id.search);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().toLowerCase());
            }
        });

        //  progressBar.setVisibility(View.VISIBLE);
        //progressBar.setProgress(Window.FEATURE_INDETERMINATE_PROGRESS);


      // getActivity(). setContentView(R.layout.activity_notes);
        b1 = getView().findViewById(R.id.fileUploadButton);

        b1.setVisibility(View.INVISIBLE);

        dataref = FirebaseDatabase.getInstance().getReference("users/Notes/" + getActivity().getIntent().getStringExtra("Year"));
        dataref.keepSynced(true);
        // search = findViewById(R.id.Searchaction);

        recyclerView = getView().findViewById(R.id.snotesrecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Filedata = new ArrayList<>();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
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
                Intent i = new Intent(getActivity(), Documentupdate.class);
                i.putExtra("yeardetails", getActivity().getIntent().getStringExtra("Year"));
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

                dA = new DocumentAdapter(getActivity(), Filedata);


                recyclerView.setAdapter(dA);
                Notes1.WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new Notes1.WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
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



    public  void filter(String text) {
       // Log.i("test",text);
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
