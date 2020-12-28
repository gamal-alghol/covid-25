package com.covid_19;


import com.covid_19.model.Message;
import com.covid_19.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.bouncycastle.asn1.cms.Time;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

public class test {

    private CountDownLatch authSignal = new CountDownLatch(10);

    @Mock
    private android.content.SharedPreferences sharedPreferences;
    FirebaseMocker firebaseMocker;


@Before
public  void Before(){
     firebaseMocker=new FirebaseMocker();
}
    @Test
    public void getUsersr(){
        firebaseMocker.getFirebase().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            Assert.assertEquals(" users",dataSnapshot.getValue().toString());
                            authSignal.countDown();
                }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        throw new UnsupportedOperationException("Not implemented");
                    }
                });
    }
    @Test
    public void getChat(){
       FirebaseMocker firebaseMocker=new FirebaseMocker();
        firebaseMocker.getFirebase().child("Chat")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Assert.assertEquals("Chat",dataSnapshot.getValue().toString());
                        authSignal.countDown();
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        throw new UnsupportedOperationException("Not implemented");
                    }
                });
    }


    @Test
    public void putEstfsar(){
        Message message=new Message();
 message.setMessage("hi");
        message.setReceiver("+972592891871");
        message.setSender("972592891871");
        message.setDeliveryTime(getCurrentDate());


        FirebaseMocker firebaseMocker=new FirebaseMocker();
        firebaseMocker.getFirebase().child("Chat").child("+972592891871").setValue(message);
    }
    private Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        Date date = new Date(Calendar.getInstance().getTime().getTime());

        return date;

    }
    @Test
    public void putUser(){
        User user=new User();
        user.setStatus("onlain");
        user.setUserName("gamal");
        user.setImageUrl("*****************");
        user.setPhoneNumber("+972592891871");

        FirebaseMocker firebaseMocker=new FirebaseMocker();
        firebaseMocker.getFirebase().child("User").setValue(user);

    }
}
