package com.covid_19.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.covid_19.Adapter.MsgAdabter;
import com.covid_19.R;
import com.covid_19.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Chat extends AppCompatActivity {
    EditText msgEdit;
    ImageView imageButton;
    ArrayList<Message> msgArrayList;
    RecyclerView recyclerView;
    TextView txtName;
    ImageButton onBack;
    LinearLayoutManager layoutManager;
    int limitMsgs = 15;
    MsgAdabter messagesAdapter;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        msgEdit = findViewById(R.id.edit_chat);
        imageButton = findViewById(R.id.img_send);
        txtName = findViewById(R.id.text_title_condition);
        recyclerView = findViewById(R.id.list_chat);
        onBack = findViewById(R.id.btn_back);
        refreshLayout=findViewById(R.id.swipe_refresh);
        msgArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        txtName.setText(getIntent().getStringExtra("name"));


        SendMsgs();
        setOnBack();
        getMessages();
        messagesAdapter = new MsgAdabter(msgArrayList, getApplicationContext(), FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), getIntent().getStringExtra("userPhoneNumber"));
        recyclerView.setAdapter(messagesAdapter);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                msgArrayList.clear();
                getMessages();
            }
        });

    }


    private void getMessages() {

        getInfoMasgs(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), getIntent().getStringExtra("userPhoneNumber"));

    }

    public void getInfoMasgs(final String sender, final String receiver) {
        FirebaseDatabase.getInstance().getReference("Chat").child(sender).child(receiver)
                .limitToLast(limitMsgs).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                limitMsgs = limitMsgs + 1;
                Message message = dataSnapshot.getValue(Message.class);
                msgArrayList.add(message);

                messagesAdapter.notifyDataSetChanged();

                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    public void SendMsgs(){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!msgEdit.getText().toString().equals("")&&msgEdit.getText().toString()!=null){

                    Message message=new Message(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), getIntent().getStringExtra("userPhoneNumber"), msgEdit.getText().toString(),getCurrentDate());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child( getIntent().getStringExtra("userPhoneNumber")).push().setValue(message);
                    reference.child("Chat").child(getIntent().getStringExtra("userPhoneNumber") ).child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).push().setValue(message);
                    msgEdit.setText("");
                    recyclerView.scrollToPosition(msgArrayList.size()-1);

                }

            }
        });
    }


    private Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        Date date = new Date(Calendar.getInstance().getTime().getTime());

        return date;

    }

    public  void setOnBack(){
        onBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
