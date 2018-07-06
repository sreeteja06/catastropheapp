//This series of files is to upload documents in the firebase
package com.example.sreet.learning;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;

public class Documentupdate extends AppCompatActivity {
    ProgressDialog progressDialog;
    Intent intent;
    ImageView b1, b2;
    EditText ettx,ettx1;
    private static final int PICK = 111;
   Uri urii;
    Uri uriarray[];
    DatabaseReference databaseReference;
    public StorageReference storageReference;
    String personEmail;
    String name;

    @Override
    public void onBackPressed() {
super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(Documentupdate.this);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        // progressDialog.setMax(100);
        progressDialog.setMessage("Uploading file...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentupdate);
        b1 = findViewById(R.id.selectimagebutton);
        b2 = findViewById(R.id.uploadimageviewbuttonid);
        ettx = findViewById(R.id.Filenameid);
        ettx1 = findViewById(R.id.SubName);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
             personEmail = acct.getEmail();
             name = acct.getDisplayName();
            if(personEmail.equalsIgnoreCase("itstechclub@gmail.com")||personEmail.equalsIgnoreCase("ppraneeth294@gmail.com")||personEmail.equalsIgnoreCase("samalakrishna7@gmail.com")||personEmail.equalsIgnoreCase("sripad2708@gmail.com")){
               //LinearLayout sendNotice = (LinearLayout) findViewById(R.id.sendNoticeLayout);
                //.setVisibility(View.VISIBLE);
            }
        }

        storageReference = FirebaseStorage.getInstance().getReference("users/Notes/"+getIntent().getStringExtra("yeardetails"));
        databaseReference = FirebaseDatabase.getInstance().getReference("users/Notes/"+getIntent().getStringExtra("yeardetails"));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatata();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ettx.getText().toString().length() !=0&&indicator==1 && ettx1.getText().toString().length() !=0){
                progressDialog.show();


                uploadFile();
            }
            else
                {
                    Toast.makeText(Documentupdate.this,"select file and fill the fields",Toast.LENGTH_SHORT).show();
                }}
        });

    }

    public void showDatata() {
       intent = new Intent();
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK);

    }
int indicator;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
           urii = data.getData();
           indicator =1;
          //uploadFile(temp);
        } else if(requestCode == PICK && resultCode == RESULT_OK && data != null && data.getClipData()!=null){
             for(int i=0;i<data.getClipData().getItemCount();i++)
             {
                 uriarray[i] = data.getClipData().getItemAt(0).getUri();
                // uploadFile(temp2);
                 indicator = 1;

             }

        }
        else
        {
            finishActivity(requestCode);
        }
    }


    public String getTypenotes(Uri uri) {

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
String Url;
    public void uploadFile() {
        if(urii!=null)
        {
            uriDependent(urii);
        }
        else{
            for(int i=0;i<uriarray.length;i++)
            {
                uriDependent(uriarray[i]);
            }
        }





                    /*.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Date currentTime = Calendar.getInstance().getTime();
                    NotesDataClass nc = new NotesDataClass(ettx.getText().toString().trim()+"."+getTypenotes(urii),currentTime.toString(), taskSnapshot.getDownloadUrl().toString());
                    Toast.makeText(Documentupdate.this, "Success", Toast.LENGTH_SHORT).show();
                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(nc);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Documentupdate.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    System.out.println("Upload is " + progress + "% done");
                    int currentprogress = (int) progress;
                    progressDialog.setProgress(currentprogress);
                }


            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    progressDialog.dismiss();

                }
            });*/
        }
         public void uriDependent(Uri uridep) {
        final   StorageReference ffref = storageReference.child(ettx1.getText().toString()+" "+ettx.getText().toString().trim() + "." + getTypenotes(uridep));
        final UploadTask uploadTask =  ffref.putFile(uridep);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ffref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Url = downloadUri.toString();
                    Date currentTime = Calendar.getInstance().getTime();
                    String finale = currentTime.toString().replace("GMT+05:30","");

                    NotesDataClass nc = new NotesDataClass(ettx1.getText().toString()+" "+ettx.getText().toString().trim()+"."+getTypenotes(urii),finale+" by "+name, Url);
                    Toast.makeText(Documentupdate.this, "Success", Toast.LENGTH_SHORT).show();
                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(nc);
                    progressDialog.dismiss();
                  //  Intent i =new Intent(Documentupdate.this,Notes1.class);
                    //startActivity(i);
                    finish();
                    ettx1.setText("");
                    ettx.setText("");
                } else {
                    // Handle failures
                    // ...
                }
            }}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Documentupdate.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
