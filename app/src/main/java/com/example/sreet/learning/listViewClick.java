package com.example.sreet.learning;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class listViewClick extends AppCompatActivity {
    ImageView imageView, imageView2, imageView3;
    StorageReference storageReference;
    String imageURL,imageURL2,imageURL3;
    boolean fav = false;
    boolean check = false;
    private SharedPreferences sharedPreferences;
    private Menu menu;
    String Descript,year,Date,userName;
    String parentActivity;
    int imagesValue;
    Uri URI;

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
        parentActivity = bundle.getString("preActivity","Notices");
        userName = bundle.getString("userName","sree");
        imagesValue = bundle.getInt("images",0);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        Descript = bundle.getString("Description","coffee is good for health");
        year = bundle.getString("Year","firstYear");
        TextView dateAndTime = (TextView) findViewById(R.id.dateAndTime);
        dateAndTime.setText(Date);
        TextView notice = (TextView) findViewById(R.id.notice);
        notice.setText(Descript);
        storageReference = FirebaseStorage.getInstance().getReference().child(parentActivity+"/"+year+"/"+Descript+"/0");
        if(storageReference!=null) {
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imageURL = uri.toString();
                    final Uri SaveUri = uri;
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
                    imageView.setOnLongClickListener(new View.OnLongClickListener() {

                        Bitmap bitmap;

                        @Override
                        public boolean onLongClick(View v) {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                URL url = new URL(imageURL);
                                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
                            } catch (IOException e) {

                            }
                            String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, Descript+"/0", Descript+"/0");
                            URI = Uri.parse(imagePath);
                            Toast.makeText(listViewClick.this, "Saved success", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        storageReference = FirebaseStorage.getInstance().getReference().child(parentActivity+"/"+year+"/"+Descript+"/1");
        if(storageReference!=null) {
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imageURL2 = uri.toString();
                    Glide.with(getApplicationContext()).load(imageURL2).into(imageView2);
                    imageView2.setOnLongClickListener(new View.OnLongClickListener() {

                        Bitmap bitmap;

                        @Override
                        public boolean onLongClick(View v) {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                URL url = new URL(imageURL2);
                                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
                            } catch (IOException e) {

                            }
                            String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, Descript+"/1", Descript+"/1");
                            URI = Uri.parse(imagePath);
                            Toast.makeText(listViewClick.this, "Saved success", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                }
            });
        }
        storageReference = FirebaseStorage.getInstance().getReference().child(parentActivity+"/"+year+"/"+Descript+"/2");
        if(storageReference!=null) {
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imageURL3 = uri.toString();
                    Glide.with(getApplicationContext()).load(imageURL3).into(imageView3);
                    imageView3.setOnLongClickListener(new View.OnLongClickListener() {

                        Bitmap bitmap;

                        @Override
                        public boolean onLongClick(View v) {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                URL url = new URL(imageURL3);
                                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
                            } catch (IOException e) {

                            }
                            String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, Descript+"/2", Descript+"/2");
                            URI = Uri.parse(imagePath);
                            Toast.makeText(listViewClick.this, "Saved success", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                }
            });
        }
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
            case R.id.LogOut:
                FirebaseAuth.getInstance().signOut();
                Intent sign = new Intent(this,SignIn.class);
                startActivity(sign);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        sharedPreferences = this.getSharedPreferences("com.example.sreet.learning", Context.MODE_PRIVATE);
        check = sharedPreferences.getBoolean(year+" "+Descript+"check",false);               //if false that means there is no stored memory for this notice
        if(check){
            fav = sharedPreferences.getBoolean(year+" "+Descript+"fav",false);
        }
        if(fav){
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_fill));
        }
        return super.onPrepareOptionsMenu(menu);
    }
}