package com.example.sreet.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class globalChatAdapter extends RecyclerView.Adapter<globalChatAdapter.ViewHolder> {

    private ArrayList<GlobalChatDataClass> GCDC = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView globalChatEmailView;
        private TextView globalChatMessageView;
        private View parentView;

        public ViewHolder(View view){
            super(view);
            this.parentView = view;
            globalChatEmailView = (TextView) view.findViewById(R.id.globalChatEmailView);
            globalChatMessageView = (TextView) view.findViewById(R.id.globalChatMessageView);
        }
    }

    public globalChatAdapter(Context context, ArrayList<GlobalChatDataClass> GCDC){
        this.context = context;
        this.GCDC = GCDC;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context)
                .inflate(R.layout.global_message_style,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlobalChatDataClass currentData = GCDC.get(position);
        holder.globalChatEmailView.setText(currentData.EMAIL);
        holder.globalChatMessageView.setText(currentData.Message);

    }

    @Override
    public int getItemCount() {
        return GCDC.size();
    }



}
