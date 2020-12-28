package com.covid_19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.covid_19.model.Message;
import com.covid_19.network.ApiClient;
import com.covid_19.network.ApiInterface;
import com.covid_19.view.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ChatUnitTest {

    private CountDownLatch latch = new CountDownLatch(1);
    DatabaseReference reference;
    ArrayList<Message> msgArrayList;
    int limitMsgs = 15;

    private Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        Date date = new Date(Calendar.getInstance().getTime().getTime());

        return date;

    }

    @Before
    public void beforeClassTest() {
        reference = FirebaseDatabase.getInstance().getReference("Chat");
        msgArrayList = new ArrayList<>();
    }

    @Test
    public void sendMessageTest() {

        Message message = new Message(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), "0595619096", "Hello World", getCurrentDate());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("0595619096").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Assert.assertTrue("Successful", true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Assert.assertTrue("Faild", false);
            }
        });

        reference.child("Chat").child("0595619096").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Assert.assertTrue("Successful", true);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Assert.assertTrue("Faild", false);
            }
        });
    }

    @Test
    public void getMessagesTest() throws InterruptedException {
        FirebaseDatabase.getInstance().getReference("Chat").child("yehya").child("hamza")
                .limitToLast(limitMsgs).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                limitMsgs = limitMsgs + 1;
                Message message = dataSnapshot.getValue(Message.class);
                msgArrayList.add(message);
                latch.countDown();

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

        latch.await(5, TimeUnit.SECONDS);
        Assert.assertNotNull(msgArrayList);
    }
}
