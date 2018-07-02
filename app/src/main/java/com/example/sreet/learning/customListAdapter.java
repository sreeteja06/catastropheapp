package com.example.sreet.learning;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class customListAdapter extends RecyclerView.Adapter<customListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> Descript;
    private ArrayList<String> Date;
    private ArrayList<String> Users;
    private ArrayList<String> images;
    String Year;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Descript;
        private TextView Date;
        private TextView userName;
        private View parentView;

        public ViewHolder(View view){
            super(view);
            this.parentView = view;
            Descript = (TextView) view.findViewById(R.id.Descript);
            Date = (TextView) view.findViewById(R.id.Date);
            userName = (TextView) view.findViewById(R.id.userName);
        }
    }

    public customListAdapter(Context context, ArrayList<String> Descript, ArrayList<String> Date, ArrayList<String> Users, ArrayList<String> images, String Year) {
        this.context = context;
        this.Descript =  Descript;;
        this.Date = Date;
        this.Users = Users;
        this.images = images;
        this.Year = Year;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context)
                .inflate(R.layout.custom_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String shrinkDescript = this.Descript.get(position);
        if(shrinkDescript!=null) {
            if (shrinkDescript.length() > 20) {
                shrinkDescript = shrinkDescript.substring(0, 14) + "....";
            }
        }
        String imgValue = images.get(position);
        if(imgValue!=null && !imgValue.equalsIgnoreCase("0")) {
            shrinkDescript = shrinkDescript+"(Contains Images)";
        }

        holder.Descript.setText(shrinkDescript);
        holder.Date.setText(this.Date.get(position).substring(0,10));
        holder.userName.setText(this.Users.get(position));
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,listViewClick.class);
                String positionDate = Date.get(position);
                intent.putExtra("Date",positionDate);
                String positionDes = Descript.get(position);
                intent.putExtra("Description",positionDes);
                String userName = Users.get(position);
                intent.putExtra("userName",userName);
                String images = Users.get(position);
                intent.putExtra("images",images);
                intent.putExtra("Year",Year);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return Descript.size();
    }

    public void filter(String text){

    }
}
