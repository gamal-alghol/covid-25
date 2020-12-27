package com.covid_19.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid_19.R;
import com.covid_19.model.Message;

import java.util.ArrayList;


public class MsgAdabter extends RecyclerView.Adapter<MsgAdabter.ViewHolder> {

private Context context;
private ArrayList<Message> msgArrayList;
private String sender,receiver;


    public MsgAdabter(ArrayList<Message> msgArrayList, Context context, String sender, String receiver) {
        this.context = context;
        this.msgArrayList = msgArrayList;
        this.sender=sender;
        this.receiver=receiver;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.message_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message=msgArrayList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return msgArrayList.size();
    }


    class  ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMsgSender,txtMsgReceiver;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMsgSender=itemView.findViewById(R.id.message_sender);
            txtMsgReceiver=itemView.findViewById(R.id.message_receiver);


        }

        void bind(Message message) {



            if(message.getSender().equals(sender)||message.getReceiver().equals(receiver)){
                txtMsgSender.setVisibility(View.VISIBLE);
                txtMsgSender.setText(message.getMessage());
                txtMsgReceiver.setVisibility(View.GONE);
            }else if(message.getReceiver().equals(receiver)||message.getReceiver().equals(sender)){
                txtMsgReceiver.setVisibility(View.VISIBLE);
                txtMsgReceiver.setText(message.getMessage());
                txtMsgSender.setVisibility(View.GONE);
            }

        }
    }


}
