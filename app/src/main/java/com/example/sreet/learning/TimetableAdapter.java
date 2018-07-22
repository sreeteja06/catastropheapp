package com.example.sreet.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.crashlytics.android.beta.Beta.TAG;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.DocumentHolder> {

    private Context Cx;
    private List<TimeDataclass> Tlist;
    String data;
    DatabaseReference reference ;

    public TimetableAdapter(Context cx, List<TimeDataclass> tlist,String data) {
        Cx = cx;
        Tlist = tlist;
        this.data = data;
    }

    @NonNull
    @Override
    public DocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(Cx).inflate(R.layout.custom_list_adapter, parent, false);


        return new TimetableAdapter.DocumentHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentHolder holder, final int position) {
        final TimeDataclass tclass = Tlist.get(position);
        Log.i("CHeck", Tlist.get(position).getSubject());
        holder.t1.setText(tclass.getSubject().trim());
        holder.t2.setText(tclass.getStarttime().trim());

        holder.t3.setText(tclass.getEndtime().trim());
        holder.t4.setText(tclass.getClassroom().trim());
        holder.c.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("check","chcked");

                PopupMenu popup = new PopupMenu(Cx, holder.c);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                  public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                String temp[] = data.split(" ");
                                reference = FirebaseDatabase.getInstance().getReference("users/TimetablePersonal/"+temp[1]+"/"+temp[0]);
                                reference.child(tclass.getUniquekey()).removeValue();
                                Tlist.remove(position);
                                notifyDataSetChanged();



                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });


    }


    @Override
    public int getItemCount() {
        return Tlist.size();
    }

    public class DocumentHolder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3, t4;
        ConstraintLayout c;

        public DocumentHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.subjectnametext);
            t2 = itemView.findViewById(R.id.starttextid);
            t3 = itemView.findViewById(R.id.endtextid);
            t4 = itemView.findViewById(R.id.Roomtextid);
            c = itemView.findViewById(R.id.cID);


        }
    }
}
