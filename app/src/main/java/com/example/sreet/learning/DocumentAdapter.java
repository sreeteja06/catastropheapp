package com.example.sreet.learning;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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


    private List<NotesDataClass> lists;
    private static final int MEGABYTE = 1024 * 1024;
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
        final String u = noclass.getUrl();




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





    public  class DocmentHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
      ImageView b1;

        DocmentHolder(View itemView) {
            super(itemView);
           tv1 = itemView.findViewById(R.id.textView11);
            tv2 = itemView.findViewById(R.id.textView7);

            b1 = itemView.findViewById(R.id.button4);


        }


    }
public void download(String Url, String file)
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
          //super.onPreExecute();

          progressDialog=new ProgressDialog(Cx);
          progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
          progressDialog.setIndeterminate(false);
          progressDialog.setMax(100);
          progressDialog.setMessage("Downloading file...");
          progressDialog.setCancelable(false);
          progressDialog.setCanceledOnTouchOutside(false);
         progressDialog.show();




      }

      @Override
      protected void onPostExecute(String s) {
          super.onPostExecute(s);
          Toast.makeText(Cx,"Downloaded",Toast.LENGTH_SHORT).show();

          progressDialog.dismiss();
          progressDialog.setCancelable(true);
          progressDialog.setCanceledOnTouchOutside(true);



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
         Log.i("adad",strings[0]);
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




