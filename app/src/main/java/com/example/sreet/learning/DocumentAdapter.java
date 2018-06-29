package com.example.sreet.learning;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by sripadpc on 28-05-2018.
 */


public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocmentHolder> {

    private Context Cx;

    int positionget;
    public void getPositionget(int p)
    {
        positionget = p;

    }
    private List<NotesDataClass> lists;
    public static final int MEGABYTE = 1024 * 1024;
    public void updateList(List<NotesDataClass> listupdater){
        lists = listupdater;
        notifyDataSetChanged();
    }


    public DocumentAdapter(Context cx, List<NotesDataClass> lists) {
        Cx = cx;
        this.lists = lists;

    }

    @NonNull

    @Override
    public DocmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View  v = LayoutInflater.from(Cx).inflate(R.layout.documentcard, parent, false);


        return new DocmentHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final DocmentHolder holder, int position) {
        final NotesDataClass noclass = lists.get(position);


        holder.tv1.setText(noclass.getName());
        holder.tv2.setText(noclass.getDate());
        final String u = noclass.getUrll();

      //   String last="";
     /*   if(u.contains(".pdf")){
            last = ".pdf";}
        if(u.contains(".jpg")){
            last = ".jpg";}
        if(u.contains(".doc")){
            last = ".doc";}
        if(u.contains(".docx")){
            last = ".docx";}
        if(u.contains(".txt")){
            last = ".txt";}
        if(u.contains(".mp3")){
            last = ".mp3";}
        if(u.contains(".mp4")){
            last = ".mp4";}
        if(u.contains(".png")){
            last = ".png";}
        if(u.contains(".xls")){
            last = ".xls";}
           final String ext = last;

*/

      /*  int k= u.indexOf("?");
        String sub =u.substring(0,k);
        int len = sub.length();
        len = len-1;
        int count = 0;

        while(sub.charAt(len)!='.'){
            len = len-1;
            count++;
        }
        final String last = sub.substring(k-count,k);
        */


      //final   Uri uri = Uri.parse(u);




        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(u, noclass.getName());


            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }





    public  class DocmentHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView tv1,tv2;
        Button b1;

        public DocmentHolder(View itemView) {
            super(itemView);
           tv1 = itemView.findViewById(R.id.textView11);
            tv2 = itemView.findViewById(R.id.textView7);
            itemView.setOnCreateContextMenuListener(this);


            b1 = itemView.findViewById(R.id.button4);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem delete = menu.add(Menu.NONE,1,1,"Delete");
            delete.setOnMenuItemClickListener(onEditMenu);
        }
        final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                if(item.getItemId() == 1){
                   // Log.i("Finally deleted","lololo");
                    NotesDataClass object  = lists.get(positionget);

                    String s = object.getName();
                    //System.out.println(s);
                    lists.remove(object);
                    notifyDataSetChanged();

                }

                return false;
            }
        };
    }
public void download(String Url,String file)
{
    ConnectivityManager manager = (ConnectivityManager) Cx.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo info = manager.getActiveNetworkInfo();
    boolean isConnected = info!=null && info.isConnectedOrConnecting();
    if(!isConnected) {
//        Toast.makeText(Cx, "Connect to internet", Toast.LENGTH_SHORT).show();
        return;
    }else{
        Toast.makeText(Cx,"Downloading",Toast.LENGTH_SHORT).show();
    }
    new DocumentAdapter.FileDownloader().execute(Url,file);

    }
  class FileDownloader extends AsyncTask<String,Integer,String >{
      ProgressDialog progressDialog;

      @Override
      protected void onPreExecute() {
          super.onPreExecute();
          progressDialog=new ProgressDialog(Cx);
          progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
          progressDialog.setIndeterminate(false);
          progressDialog.setMax(100);
          progressDialog.setMessage("Downloading file...");
          progressDialog.show();


      }

      @Override
      protected void onPostExecute(String s) {
          super.onPostExecute(s);
          Toast.makeText(Cx,"Downloaded",Toast.LENGTH_SHORT).show();
          progressDialog.dismiss();


      }

      @Override
      protected void onProgressUpdate(Integer... values) {
          super.onProgressUpdate(values);
          progressDialog.setProgress(values[0]);




      }

      @Override
      protected String doInBackground(String... strings) {
          String url = strings[0];
          String filename = strings[1];
          String extstoragedir = Environment.getExternalStorageDirectory().toString();
          File fol = new File(extstoragedir, "Ifhe");
          File folder=new File(fol,"Documents");
if(!folder.exists()) {
    boolean bool = folder.mkdirs();
}
          File file = new File(folder, filename);
          try {
              boolean state = file.createNewFile();
          } catch (IOException e) {
              Log.i("log", e.getLocalizedMessage());
              e.printStackTrace();
          }
          int count;
          try {
              Log.i("log","file downloader called");
              URL contenturl=new URL(url);
              HttpURLConnection httpURLConnection= (HttpURLConnection) contenturl.openConnection();
              httpURLConnection.connect();
              InputStream inputStream=httpURLConnection.getInputStream();
              FileOutputStream outputStream=new FileOutputStream(file);
              int totalsize=httpURLConnection.getContentLength();
              Log.i("log","length of file" +String.valueOf(totalsize));
              byte[] buffer= new byte[MEGABYTE];
              int bufferlength=0;
              while((count=inputStream.read(buffer))>0){
                  bufferlength+=count;
                  publishProgress((int) ( (bufferlength / (float)totalsize)*100));
                  outputStream.write(buffer,0,count);
              }
              inputStream.close();
              outputStream.flush();
              outputStream.close();


          } catch (MalformedURLException e) {
              Log.i("loge",e.getLocalizedMessage());
              e.printStackTrace();
          } catch (IOException e) {
              Log.i("loge",e.getLocalizedMessage());
              e.printStackTrace();
          }





          return null;
      }
  }
    }




