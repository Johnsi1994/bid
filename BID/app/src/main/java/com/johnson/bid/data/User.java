package com.johnson.bid.data;

import java.util.ArrayList;

public class User {

    private long mId;
    private String mName;
    private String mImage;
    private ArrayList<Long> mMyBiddingProductsId;
    private ArrayList<Long> mMySellingProductsId;
    private ArrayList<Long> mMyBoughtProductsId;
    private ArrayList<Long> mMySoldProductsId;
    private ArrayList<Long> mEyesOn;

//    private ArrayList<Product> mChatRoomId;

    public User() {

        mId = -1;
        mName = "";
        mImage = "";
        mMyBiddingProductsId = new ArrayList<>();
        mMySellingProductsId = new ArrayList<>();
        mMyBoughtProductsId = new ArrayList<>();
        mMySoldProductsId = new ArrayList<>();
        mEyesOn = new ArrayList<>();
//        mChatRoomId = new ArrayList<>();

    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public ArrayList<Long> getMyBiddingProductsId() {
        return mMyBiddingProductsId;
    }

    public void setMyBiddingProductsId(ArrayList<Long> myBiddingProductsId) {
        mMyBiddingProductsId = myBiddingProductsId;
    }

    public ArrayList<Long> getMySellingProductsId() {
        return mMySellingProductsId;
    }

    public void setMySellingProductsId(ArrayList<Long> mySellingProductsId) {
        mMySellingProductsId = mySellingProductsId;
    }

    public ArrayList<Long> getMyBoughtProductsId() {
        return mMyBoughtProductsId;
    }

    public void setMyBoughtProductsId(ArrayList<Long> myBoughtProductsId) {
        mMyBoughtProductsId = myBoughtProductsId;
    }

    public ArrayList<Long> getMySoldProductsId() {
        return mMySoldProductsId;
    }

    public void setMySoldProductsId(ArrayList<Long> mySoldProductsId) {
        mMySoldProductsId = mySoldProductsId;
    }

    public ArrayList<Long> getEyesOn() {
        return mEyesOn;
    }

    public void setEyesOn(ArrayList<Long> eyesOn) {
        mEyesOn = eyesOn;
    }

//    public ArrayList<Product> getChatRoomId() {
//        return mChatRoomId;
//    }
//
//    public void setChatRoomId(ArrayList<Product> chatRoomId) {
//        mChatRoomId = chatRoomId;
//    }
}
