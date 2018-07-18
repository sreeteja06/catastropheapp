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

    private ArrayList<String> Emails = new ArrayList<>();
    private ArrayList<String> Messages = new ArrayList<>();
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

    public globalChatAdapter(Context context, ArrayList<String> Emails,ArrayList<String> Messages){
        this.context = context;
        this.Emails = Emails;
        this.Messages = Messages;
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
        holder.globalChatEmailView.setText(Emails.get(position));
        holder.globalChatMessageView.setText(Messages.get(position));

    }

    @Override
    public int getItemCount() {
        return Emails.size();
    }



}
