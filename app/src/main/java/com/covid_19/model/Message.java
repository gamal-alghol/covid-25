package com.covid_19.model;

import androidx.annotation.Keep;

import java.util.Date;


@Keep
public class Message {

    public String sender;
    public String receiver;
    public String message;
    public Date deliveryTime;

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public Message(String sender, String receiver, String message, Date deliveryTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.deliveryTime=deliveryTime;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


}
