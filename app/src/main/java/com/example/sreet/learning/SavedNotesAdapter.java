package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SavedNotesAdapter extends RecyclerView.Adapter<SavedNotesAdapter.DocumentHolder> {

    Context cx;
    List<String>list;
    public void updateList(List<String> listupdater) {
        list = listupdater;
        notifyDataSetChanged();
    }

    public SavedNotesAdapter(Context c , List l)
    {
        cx = c;
        list = l;
    }


    @NonNull
    @Override
    public DocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(cx).inflate(R.layout.savednotes_recycle, parent, false);


        return new SavedNotesAdapter.DocumentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentHolder holder, final int position) {
        holder.t.setText(list.get(position).trim());
        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileopath = list.get(position);
                if (fileopath != null) {
                    File file = new File(Environment.getExternalStorageDirectory(), "/Ifhe/Documents/" + fileopath);
                    Uri path = FileProvider.getUriForFile(cx, cx.getApplicationContext().getPackageName() + ".my.package.name.provider", file);
                    // Log.i("lol",fileopath);

                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    int exten = fileopath.indexOf('.');

                    String checkexten = fileopath.substring(exten);
                    Log.i("lol", checkexten);

                    if (checkexten.equals(".pdf")) {

                        pdfOpenintent.setDataAndType(path, "application/pdf");
                    } else if (checkexten.equals(".jpg") || checkexten.equals(".png") || checkexten.equals(".jpeg") || checkexten.equals(".gif")) {
                        pdfOpenintent.setDataAndType(path, "image/*");

                    } else if (checkexten.equals(".mp3")) {
                        pdfOpenintent.setDataAndType(path, "audio/*");

                    } else if (checkexten.equals(".mp4")) {
                        pdfOpenintent.setDataAndType(path, "video/*");

                    } else {
                        pdfOpenintent.setDataAndType(path, "application/*");
                    }

                    //  pdfOpenintent.setDataAndType(path,"*/*");
                    cx.startActivity(pdfOpenintent);


                }

            }
        });
        holder.c.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popup = new PopupMenu(cx, holder.c);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                String seq = list.get(position);

                                notifyDataSetChanged();
                                String extstoragedir = Environment.getExternalStorageDirectory().toString();
                                File fol = new File(extstoragedir, "Ifhe");
                                File folder=new File(fol,"Documents");
                                if(!folder.exists()) {
                                    boolean bool = folder.mkdirs();
                                }
                                File file = new File(folder, seq);
                                try {
                                    boolean state = file.createNewFile();
                                } catch (IOException e) {
                                    Log.i("log", e.getLocalizedMessage());
                                    e.printStackTrace();
                                }
                                boolean deleted = file.delete();
                                if(deleted)
                                {              //   Toast.makeText(savedNotes.this,seq+" deleted succesfully",Toast.LENGTH_SHORT).show();
                                    list.remove(position);


                                }else{
                                  //  Toast.makeText(savedNotes.this,seq+" unable to delete",Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();

                                }
                                notifyDataSetChanged();



                        }
                        return false;
                    }
                });
                popup.show();

                return false;

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class DocumentHolder extends RecyclerView.ViewHolder{
TextView t;
CardView c;
        public DocumentHolder(View itemView) {
            super(itemView);
            t = itemView.findViewById(R.id.filename);
            c = itemView.findViewById(R.id.cardforsavednotes);
        }
    }
}
