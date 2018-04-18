package com.duytue.finalproject;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Phy on 7/26/2017.
 */

public class Place {

    int placeID;
    String name;
    String address;
    String phoneNo;
    LatLng latLng;
    String imgURL;
    String websiteURL;
    String hours;
    Bitmap img;

    public Place(int placeID, String name, String address, String phoneNo, LatLng latLng, String imgURL, String websiteURL, String hours) {
        this.placeID = placeID;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.latLng = latLng;
        this.imgURL = imgURL;
        this.websiteURL = websiteURL;
        this.hours = hours;
    }
    public Place(String name,String address,LatLng latlng, Bitmap img)
    {
        this.name = name;
        this.address = address;
        this.latLng = latlng;
        this.img = img;
    }
    public Place(String name, String address, String phoneNo, LatLng latLng,String websiteURL, String hours,Bitmap img) {
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.latLng = latLng;
        this.websiteURL = websiteURL;
        this.hours = hours;
        this.img = img;
    }
    public Place(String name, String address, String phoneNo, String websiteURL, String hours, Bitmap img) {
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.websiteURL = websiteURL;
        this.hours = hours;
        this.img = img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }


    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Bitmap getImg() {
        return img;
    }



}
