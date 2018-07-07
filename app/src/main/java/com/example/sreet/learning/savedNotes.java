package com.example.sreet.learning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class savedNotes extends AppCompatActivity {
    private List<String> fileList = new ArrayList<>();
    //EditText searchFile;
    ArrayAdapter<String> directoryList;
     ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_notes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Saved Notes");
        File root = new File(Environment
                .getExternalStorageDirectory()+"/Ifhe/Documents");
        ListDir(root);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
       // MenuItem item= menu.findItem(R.id.LogOut);
        //MenuItem item1 = menu.findItem(R.id.saveNOtice);
        //item1.setVisible(false);
        //item.setVisible(false);
        this.invalidateOptionsMenu();
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                directoryList.getFilter().filter(newText);
                return false;
            }

        });

        return super.onCreateOptionsMenu(menu);

    }
/*
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
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

*/
    void ListDir(File f){
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files){
            String s = file.getPath();
            String res[] = s.split("/");
            int h = res.length;
            fileList.add(res[h-1]);
        }


        directoryList   = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, fileList);
        lv = findViewById(R.id.doclview);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {

                int pos =lv.getPositionForView(arg1);
                String fileopath = fileList.get(pos);
                if(fileopath != null){
                    File file = new File(Environment.getExternalStorageDirectory(), "/Ifhe/Documents/"+fileopath);
                    Uri path = FileProvider.getUriForFile(savedNotes.this,getApplicationContext().getPackageName() + ".my.package.name.provider", file);
                    // Log.i("lol",fileopath);

                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        int exten = fileopath.indexOf('.');

                String checkexten = fileopath.substring(exten);
                    Log.i("lol", checkexten);

                    if(checkexten.equals( ".pdf")) {

                    pdfOpenintent.setDataAndType(path, "application/pdf");
                }
                else if(checkexten.equals(".jpg") || checkexten.equals(".png")  || checkexten.equals(".jpeg") ||checkexten.equals(".gif"))
                {
                    pdfOpenintent.setDataAndType(path, "image/*");

                }
                else if (checkexten.equals( ".mp3"))
                {
                    pdfOpenintent.setDataAndType(path,"audio/*");

                }
                else if (checkexten.equals( ".mp4"))
                {
                    pdfOpenintent.setDataAndType(path,"video/*");

                }
                else
                    {
                        pdfOpenintent.setDataAndType(path,"application/*");
                    }

                  //  pdfOpenintent.setDataAndType(path,"*/*");
                    startActivity(pdfOpenintent);





                }
            }
        });

        lv.setAdapter(directoryList);
        registerForContextMenu(lv);

        /*searchFile.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                savedNotes.this.directoryList.getFilter().filter(s.toString().toLowerCase());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                savedNotes.this.directoryList.getFilter().filter(s.toString().toLowerCase());


            }
        });
*/


    }
 /*   void filter(String text)
    {
        List<String> a = new ArrayList();
        for(String t : fileList){
            if(t.toLowerCase().contains(text)){
                a.add(t);

            }
            fileList =a;
            directoryList.notifyDataSetChanged();

        }
    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.delete:{//String seq = (String) item.getTitle();
                // Toast.makeText(savedNotes.this,seq,Toast.LENGTH_SHORT).show();
                 String seq = fileList.get(info.position);

                 directoryList.notifyDataSetChanged();
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
                {                 Toast.makeText(savedNotes.this,seq+" deleted succesfully",Toast.LENGTH_SHORT).show();
                    fileList.remove(info.position);


                }else{
                    Toast.makeText(savedNotes.this,seq+" unable to delete",Toast.LENGTH_SHORT).show();
                    directoryList.notifyDataSetChanged();

                }

                }
        }


        return super.onContextItemSelected(item);
    }
}
