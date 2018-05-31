package com.example.sreet.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class customListAdapter extends ArrayAdapter<String>{
    private Context context;
    private ArrayList<String> Descript;
    private ArrayList<String> Date;
    private ArrayList<String> Users;
    private ArrayList<String> images;

    public customListAdapter(Context context, ArrayList<String> Descript, ArrayList<String> Date,ArrayList<String> Users,ArrayList<String> images) {
        super(context, R.layout.custom_list, Descript);
        this.context = context;
        this.Descript = Descript;
        this.Date = Date;
        this.Users = Users;
        this.images = images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_list,parent,false);

        TextView Descript = (TextView) customView.findViewById(R.id.Descript);
        Descript.setText(this.Descript.get(position));

        TextView Date = (TextView) customView.findViewById(R.id.Date);
        Date.setText(this.Date.get(position).substring(0,10));

        TextView userName = (TextView) customView.findViewById(R.id.userName);
        userName.setText(this.Users.get(position));

        ImageView imageView = (ImageView) customView.findViewById(R.id.imageView5);
        int imgValue = Integer.parseInt(images.get(position));
        if(imgValue==0){
            imageView.setVisibility(View.GONE);
        }

        return customView;
    }
}
