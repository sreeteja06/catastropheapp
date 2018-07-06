package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class customListAdapter extends RecyclerView.Adapter<customListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NoticesDataClass> OrginalData;
    private ArrayList<NoticesDataClass> NoticesData;
    String Year, preActivity;

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
    public void updateList(ArrayList<NoticesDataClass> listupdater){
        NoticesData = listupdater;
        notifyDataSetChanged();
    }

    public customListAdapter(Context context, ArrayList<NoticesDataClass> NoticesData, String Year, String preActivity) {
        this.context = context;
        this.NoticesData = NoticesData;
        this.OrginalData = NoticesData;
        this.preActivity = preActivity;
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

        final NoticesDataClass Notices = NoticesData.get(position);

        String shrinkDescript = Notices.Descript;
        if(shrinkDescript!=null) {
            if (shrinkDescript.length() > 20) {
                shrinkDescript = shrinkDescript.substring(0, 14) + "....";
            }
        }
        String imgValue = Notices.imagesValue;
        if(imgValue!=null && !imgValue.equalsIgnoreCase("0")) {
            shrinkDescript = shrinkDescript+"(Contains Images)";
        }

        holder.Descript.setText(shrinkDescript);
        holder.Date.setText(Notices.Date.substring(0,10));
        holder.userName.setText(Notices.user);
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,listViewClick.class);
                String positionDate = Notices.Date;
                intent.putExtra("Date",positionDate);
                String positionDes = Notices.Descript;
                intent.putExtra("Description",positionDes);
                String userName = Notices.user;
                intent.putExtra("userName",userName);
                String images = Notices.imagesValue;
                intent.putExtra("images",images);
                intent.putExtra("Year",Year);
                intent.putExtra("preActivity",preActivity);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return NoticesData.size();
    }

    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<NoticesDataClass> founded = new ArrayList<NoticesDataClass>();
                    for(int i = 0; i< OrginalData.size(); i++){
                        String item = OrginalData.get(i).getDescript();
                        if(item.toString().toLowerCase().contains(constraint)){
                            founded.add(OrginalData.get(i));
                        }
                    }

                    result.values = founded;
                    result.count = founded.size();
                }else {
                    result.values = OrginalData;
                    result.count = OrginalData.size();
                }
                return result;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                NoticesData.clear();
                for (NoticesDataClass item : (List<NoticesDataClass>) results.values) {
                    NoticesData.add(item);
                }
                notifyDataSetChanged();
            }
        };
    }
}