package com.duytue.finalproject;

import android.graphics.Bitmap;

/**
 * Created by duytue on 7/18/17.
 */

public class Recipe {
    String name, desc;
    int foodID;
    int cateID;
    int cookID;
    Bitmap img;
    String imgURL;
    boolean bookmark;

    public Recipe(String name, String desc, int foodID, int cateID, int cookID, String imgURL, boolean bookmark) {
        this.name = name;
        this.desc = desc;
        this.foodID = foodID;
        this.cateID = cateID;
        this.cookID = cookID;
        this.imgURL = imgURL;
        this.bookmark = bookmark;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getDesc() {
        return desc;
    }

    public int getCookID() {
        return cookID;
    }
}