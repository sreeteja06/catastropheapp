package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class listViewClick extends AppCompatActivity {
    ImageView imageView, imageView2;
    StorageReference storageReference;
    String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_click);
        Bundle bundle = getIntent().getExtras();
        String Date = bundle.getString("Date");
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        String Descript = bundle.getString("Description");
        String year = bundle.getString("Year");
        TextView dateAndTime = (TextView) findViewById(R.id.dateAndTime);
        dateAndTime.setText(Date);
        TextView notice = (TextView) findViewById(R.id.notice);
        notice.setText(Descript);
        storageReference = FirebaseStorage.getInstance().getReference().child("Notices/"+year+"/"+Descript+"/0");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageURL = uri.toString();
                Glide.with(getApplicationContext()).load(imageURL).into(imageView);
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference().child("Notices/"+year+"/"+Descript+"/1");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageURL = uri.toString();
                Glide.with(getApplicationContext()).load(imageURL).into(imageView2);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorite:
                Toast.makeText(listViewClick.this, "Added to your favorite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Intent i = new Intent(this,about.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}