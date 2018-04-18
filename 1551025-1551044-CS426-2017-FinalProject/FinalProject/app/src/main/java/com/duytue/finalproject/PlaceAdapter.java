package com.duytue.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by duytue on 7/18/17.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    Context context;
    ArrayList<Place> pList;

    public PlaceAdapter (Context context, ArrayList<Place> list) {
        this.context = context;
        pList = list;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.place_row_info, parent, false);

        PlaceViewHolder PlaceViewHolder = new PlaceViewHolder(itemView);
        return PlaceViewHolder;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        bindCardView(holder, pList.get(position));
        setCardViewOnClick(holder, position);
    }

    private void bindCardView(PlaceViewHolder holder, Place Place) {
        holder.nameView.setText(Place.name);
        holder.descView.setText(Place.address);
        if (Place.img != null) {
            holder.imageView.setImageBitmap(Place.img);
        }
    }

    private void setCardViewOnClick(PlaceViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlaceInfoActivity(position);
            }
        });
    }

    private void startPlaceInfoActivity(int position) {
        Place place = pList.get(position);
        Intent intent = new Intent(context, PlaceInfoActivity.class);
        intent.putExtra("name", place.name);

        /*intent.putExtra("address", place.address);
        if(place.phoneNo.isEmpty()|| place.phoneNo == null){
            intent.putExtra("phoneNo", "N/A");
        }
        else {
            intent.putExtra("phoneNo", place.phoneNo);
        }
        if(place.hours.isEmpty()||place.hours == null){
            intent.putExtra("hours", "N/A");
        }
        else {
            intent.putExtra("hours", place.hours);
        }
        if(place.websiteURL.isEmpty() || place.websiteURL == null){
            intent.putExtra("websiteURL", "N/A");
        }
        else {
            intent.putExtra("websiteURL", place.websiteURL);
        }
        intent.putExtra("lat",place.latLng.latitude);
        intent.putExtra("lng",place.latLng.longitude);
        // convert bitmap to byte[] in order to send through intent
        Bitmap bmp = place.getImg();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("placeImgURL", byteArray);*/
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return pList.size();
    }
}
