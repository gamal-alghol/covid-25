package com.covid_19.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid_19.R;
import com.covid_19.model.User;
import com.covid_19.view.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersAdabter extends RecyclerView.Adapter<UsersAdabter.ViewHolder> {
    ArrayList<User> allUsers;
    Context context;
    String  lastMsg;



    public UsersAdabter(ArrayList<User> allUsers, Context context) {
        this.allUsers = allUsers;
        this.context=context;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user= allUsers.get(position);

        holder.bind(user);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, Chat.class);
                    intent.putExtra("userPhoneNumber",user.getPhoneNumber());
                    intent.putExtra("name",user.getUserName());
                    context.startActivity(intent);
                }
            });

//        LastMsg(FirebaseAuth.getInstance().getCurrentUser().getUid(),user.getId(),holder.txtLastMsg);

    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView txtLastMsg,phooneNum;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_user);
            txtLastMsg = (TextView) itemView.findViewById(R.id.txt_last_msg);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            phooneNum= (TextView) itemView.findViewById(R.id.number);
        }

        public void bind(User user) {
            name.setText(user.getUserName());
            phooneNum.setText(user.getPhoneNumber());
           imageView.setImageResource(R.drawable.user_placeholder);
      //      LastMsg(FirebaseAuth.getInstance().getCurrentUser().getUid(),user.getId(),txtLastMsg);
                                    }



    }

private     void LastMsg(final String sender,final String receiver, final TextView textViewLastMsg){

        FirebaseDatabase.getInstance().getReference("Chat").child(sender).child(receiver)
                .orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            lastMsg =snapshot.child("message").getValue().toString();
                            if (!lastMsg.equals("") && lastMsg!=null){
                                textViewLastMsg.setText(lastMsg);

                            }else {
                                textViewLastMsg.setText("");
                            }
                      }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

        }

