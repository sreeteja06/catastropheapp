package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

public class listViewClick extends AppCompatActivity {
    ImageView imageView, imageView2, imageView3;
    StorageReference storageReference;
    String imageURL;
    boolean fav = false;
    boolean check = false;
    private SharedPreferences sharedPreferences;
    private Menu menu;
    String Descript,year,Date;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
        this.menu = menu;
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_click);
        setTitle("Notice Details");
        Bundle bundle = getIntent().getExtras();
        Date = bundle.getString("Date","firstYear");
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2 = (ImageView) findViewById(R.id.imageView3);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        Descript = bundle.getString("Description","coffee is good for health");
        year = bundle.getString("Year","firstYear");
        TextView dateAndTime = (TextView) findViewById(R.id.dateAndTime);
        dateAndTime.setText(Date);
        TextView notice = (TextView) findViewById(R.id.notice);
        notice.setText(Descript);
        storageReference = FirebaseStorage.getInstance().getReference().child("Notices/"+year+"/"+Descript+"/0");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageURL = uri.toString();
                Glide.with(getApplicationContext()).load(imageURL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
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
        storageReference = FirebaseStorage.getInstance().getReference().child("Notices/"+year+"/"+Descript+"/2");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageURL = uri.toString();
                Glide.with(getApplicationContext()).load(imageURL).into(imageView3);
            }
        });
        supportInvalidateOptionsMenu();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorite:
                Intent intent = new Intent(this,idCard.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent i = new Intent(this,about.class);
                startActivity(i);
                break;
            case R.id.saveNOtice:
                int noticesValue=0;
                sharedPreferences = this.getSharedPreferences("com.example.sreet.learning", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(!fav)
                {
                    Toast.makeText(listViewClick.this, "Notice Saved", Toast.LENGTH_SHORT).show();
                    menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_fill));
                    String noticesValueString = sharedPreferences.getString("number of saved notices","0");
                    noticesValue = Integer.parseInt(noticesValueString);
                    noticesValue++;
                    editor.putString("number of saved notices",String.valueOf(noticesValue));
                    editor.putString(String.valueOf(noticesValue)+"year",year);
                    editor.putString(String.valueOf(noticesValue)+"Descript",Descript);
                    editor.putString(String.valueOf(noticesValue)+"Date",Date);
                    editor.apply();
                    fav = true;
                }
                else{
                    //menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));

                }
                editor.putBoolean(year+" "+Descript+"check",true);
                editor.putBoolean(year+" "+Descript+"fav",fav);
                editor.apply();
                break;
            case R.id.savedNotices:
                Intent intent1 = new Intent(this,savedNotices.class);
                startActivity(intent1);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        sharedPreferences = this.getSharedPreferences("com.example.sreet.learning", Context.MODE_PRIVATE);
        check = sharedPreferences.getBoolean(year+" "+Descript+"check",false);               //if true that means there is no stored memory for this notice
        if(check){
            fav = sharedPreferences.getBoolean(year+" "+Descript+"fav",false);
        }
        if(fav){
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_fill));
        }
        return super.onPrepareOptionsMenu(menu);
    }
}