package com.example.sreet.learning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Events extends AppCompatActivity {
    ArrayList<NoticesDataClass> EventsData = new ArrayList<>();
    String personEmail, myString;
    ProgressDialog uploadProgress;
    StorageReference imageStorage;
    StorageReference multipleImageStorage;
    RecyclerView list;
    private  static final int GALLERY_INTENT = 1;
    ProgressBar spinner;
    SharedPreferences sharedPreferences;
    EditText myedittext;
    ImageButton myApplyBt,imageButton;
    customListAdapter myarrayadapter;
    Firebase myfire;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//to get result from the gallery image picker
        super.onActivityResult(requestCode, resultCode, data);
        sharedPreferences = this.getSharedPreferences("com.example.sreet.learning", Context.MODE_PRIVATE);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            if(data.getClipData()!=null){
                uploadProgress.setMessage("Uploading ... ");
                uploadProgress.show();
                int totalItemsSelected = data.getClipData().getItemCount();
                EditText FilePathName = (EditText) findViewById(R.id.editText);
                final String filePathValue = FilePathName.getText().toString();
                for(int i=0;i<totalItemsSelected;i++){
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    String s = String.valueOf(i);
                    StorageReference filetopath = multipleImageStorage.child("Event").child("").child(filePathValue).child(s);
                    filetopath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Events.this, "done", Toast.LENGTH_SHORT).show();
                            uploadProgress.dismiss();
                        }
                    });
                }
                Date todaysDate = new Date();
                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = df2.format(todaysDate);
                Firebase childbase = myfire.child(date);
                childbase.child("images").setValue(totalItemsSelected);
                childbase.child("Event").setValue(filePathValue);
                childbase.child("user").setValue(sharedPreferences.getString("userName","alien"));
                FilePathName.setText(null);
            }
            else if(data.getData()!=null){
                Uri uri = data.getData();
                final EditText FilePathName = (EditText) findViewById(R.id.editText);
                uploadProgress.setMessage("Uploading ... ");
                uploadProgress.show();
                final String filePathValue = FilePathName.getText().toString();
                StorageReference filepath = imageStorage.child("Event").child("").child(filePathValue).child("0");
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Events.this, "Upload Done", Toast.LENGTH_SHORT).show();
                        uploadProgress.dismiss();
                        Date todaysDate = new Date();
                        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date = df2.format(todaysDate);
                        Firebase childbase = myfire.child(date);
                        childbase.child("images").setValue("1");
                        childbase.child("Event").setValue(filePathValue);
                        childbase.child("user").setValue(sharedPreferences.getString("userName","alien"));
                        FilePathName.setText(null);
                    }
                });
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activities");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_activities);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            personEmail = acct.getEmail();
            if(personEmail.equalsIgnoreCase("itstechclub@gmail.com")||personEmail.equalsIgnoreCase("ppraneeth294@gmail.com")||personEmail.equalsIgnoreCase("samalakrishna7@gmail.com")||personEmail.equalsIgnoreCase("sreeteja.muthyala@gmail.com")){
                LinearLayout sendNotice = (LinearLayout) findViewById(R.id.sendNoticeLayout);
                sendNotice.setVisibility(View.VISIBLE);
            }
        }
        myedittext = (EditText) findViewById(R.id.editText);
        myApplyBt = (ImageButton) findViewById(R.id.button);
        imageButton = (ImageButton) findViewById(R.id.addImageButton);
        uploadProgress = new ProgressDialog(this);
        imageStorage = FirebaseStorage.getInstance().getReference();
        multipleImageStorage = FirebaseStorage.getInstance().getReference();
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myedittext.getText().toString().trim().length()>0) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT);
                }
                else{
                    Toast.makeText(Events.this, "Enter the notice before adding image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Firebase.setAndroidContext(this);
        myfire = new Firebase("https://learning-2b334.firebaseio.com/users/Events/");
        list = (RecyclerView) findViewById(R.id.listview);
        myarrayadapter = new customListAdapter(this, EventsData,"");
        list.setAdapter(myarrayadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        list.setLayoutManager(linearLayoutManager);
        sharedPreferences = this.getSharedPreferences("com.example.sreet.learning", Context.MODE_PRIVATE);
        //list.setSelection(list.getAdapter().getCount()-1);

        myApplyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myedittext.getText().toString().trim().length()>0) {
                    myString = myedittext.getText().toString();
                    //keyvaluedata = keyvaluetext.getText().toString();
                    Date todaysDate = new Date();
                    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = df2.format(todaysDate);
                    Firebase childbase = myfire.child(date);
                    childbase.child("Event").setValue(myString);
                    childbase.child("images").setValue("0");
                    childbase.child("user").setValue(sharedPreferences.getString("userName", "alien"));
                    myedittext.setText(null);
                    Toast.makeText(Events.this, "success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myfire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String noticeValue = dataSnapshot.child("Event").getValue(String.class);
                String userName = dataSnapshot.child("user").getValue(String.class);
                String images = dataSnapshot.child("images").getValue(String.class);
                String keyvalue = dataSnapshot.getKey();
                NoticesDataClass tempData = new NoticesDataClass(noticeValue,keyvalue,userName,images);
                EventsData.add(tempData);
                myarrayadapter.notifyDataSetChanged();
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                myarrayadapter.notifyDataSetChanged();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myarrayadapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
