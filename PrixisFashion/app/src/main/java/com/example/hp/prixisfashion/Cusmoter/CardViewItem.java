package com.example.hp.prixisfashion.Cusmoter;

public class CardViewItem {
    private int mImageResource;
    private String mUsername;
    private String mRealName;

    public CardViewItem(int imageResource, String username, String realname){
        mImageResource = imageResource;
        mUsername = username;
        mRealName = realname;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmRealName() {
        return mRealName;
    }

    public void setmRealName(String mRealName) {
        this.mRealName = mRealName;
    }
}
