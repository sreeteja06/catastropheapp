package com.example.sreet.learning;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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


public class customListAdapter extends ArrayAdapter<String>{
    private Context context;
    private ArrayList<String> Descript;
    private ArrayList<String> Date;
    private ArrayList<String> Users;
    private ArrayList<String> images;

    public customListAdapter(Context context, ArrayList<String> Descript, ArrayList<String> Date,ArrayList<String> Users,ArrayList<String> images) {
        super(context, R.layout.custom_list, Descript);
        this.context = context;
        this.Descript =  Descript;;
        this.Date = Date;
        this.Users = Users;
        this.images = images;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_list,parent,false);

        String shrinkDescript = this.Descript.get(getCount() - position - 1);
        if(shrinkDescript!=null) {
            if (shrinkDescript.length() > 20) {
                shrinkDescript = shrinkDescript.substring(0, 14) + "....";
            }
        }
        String imgValue = images.get(getCount() - position - 1);
        if(imgValue!=null && !imgValue.equalsIgnoreCase("0")) {
            shrinkDescript = shrinkDescript+"(Contains Images)";
        }


        TextView Descript = (TextView) customView.findViewById(R.id.Descript);
        Descript.setText(shrinkDescript);

        TextView Date = (TextView) customView.findViewById(R.id.Date);
        Date.setText(this.Date.get(getCount() - position - 1).substring(0,10));

        TextView userName = (TextView) customView.findViewById(R.id.userName);
        userName.setText(this.Users.get(getCount() - position - 1));

        //ImageView imageView = (ImageView) customView.findViewById(R.id.imageView5);
        //

        return customView;
    }
    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<String> founded = new ArrayList<String>();
                    for(String item: Descript){
                        if(item.toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }

                    result.values = founded;
                    result.count = founded.size();
                }else {
                    result.values = Descript;
                    result.count = Descript.size();
                }
                return result;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                for (String item : (List<String>) results.values) {
                    add(item);
                }
                notifyDataSetChanged();
            }
        };
    }
}
