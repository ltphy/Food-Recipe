package com.duytue.finalproject;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Phy on 7/26/2017.
 */

public class Category {
    int categoryID;
    String name;
    String imgURL;
    Bitmap img;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setImg(Bitmap img) { this.img = img; }

    public Category(int categoryID, String name, String imgURL) {
        this.categoryID = categoryID;
        this.name = name;
        this.imgURL = imgURL;
    }
}
