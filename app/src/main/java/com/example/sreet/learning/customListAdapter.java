package com.example.sreet.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class customListAdapter extends ArrayAdapter<String>{
    private Context context;
    private ArrayList<String> Descript;
    private ArrayList<String> Date;


    public customListAdapter(Context context, ArrayList<String> Descript, ArrayList<String> Date) {
        super(context, R.layout.custom_list, Descript);
        this.context = context;
        this.Descript = Descript;
        this.Date = Date;
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
        userName.setText("Sree");

        return customView;
    }
}
