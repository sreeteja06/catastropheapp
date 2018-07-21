package com.example.sreet.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

public class globalChatAdapter extends RecyclerView.Adapter<globalChatAdapter.ViewHolder> {

    private ArrayList<GlobalChatDataClass> GCDC = new ArrayList<>();
    private Context context;
    private int lastPosition = -1;

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout SenderLayout;
        LinearLayout RecieverLayout;

        private TextView globalChatEmailView;
        private TextView globalChatMessageView;
        private TextView globalSenderView;
        private View parentView;

        public ViewHolder(View view){
            super(view);
            this.parentView = view;
            SenderLayout = (LinearLayout) view.findViewById(R.id.GlobalSender);
            RecieverLayout = (LinearLayout) view.findViewById(R.id.GlobalReciever);
            globalChatEmailView = (TextView) view.findViewById(R.id.globalChatEmailView);
            globalChatMessageView = (TextView) view.findViewById(R.id.globalChatMessageView);
            globalSenderView = (TextView) view.findViewById(R.id.globalSentChat);
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
        holder.globalSenderView.setText(currentData.Message);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        String senderEmail = "Alien";
        if (acct != null) {
            senderEmail = acct.getEmail();
        }
        if(senderEmail.equals(currentData.EMAIL)){
            holder.SenderLayout.setVisibility(View.VISIBLE);
            holder.RecieverLayout.setVisibility(View.INVISIBLE);
            holder.globalChatEmailView.setVisibility(View.INVISIBLE);
        }
        else{
            holder.RecieverLayout.setVisibility(View.VISIBLE);
            holder.SenderLayout.setVisibility(View.INVISIBLE);
            holder.globalChatEmailView.setVisibility(View.VISIBLE);
        }
        setAnimation(holder.itemView, position);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
    }


    @Override
    public int getItemCount() {
        return GCDC.size();
    }



}
