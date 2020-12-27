package com.covid_19.model;

public class User {
    private String userName;
    private String phoneNumber;
    private String imageUrl;
    private String status;

    public User(String userName, String phoneNumber) {
        this.userName =userName;
        this.phoneNumber=phoneNumber;
    }

public User(){

}
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String userEmail) {
        this.phoneNumber = userEmail;
    }



    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String ImageUrl) {
        this.imageUrl = ImageUrl;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }



}

