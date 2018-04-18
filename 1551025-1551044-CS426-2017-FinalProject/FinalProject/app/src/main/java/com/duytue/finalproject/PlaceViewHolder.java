package com.duytue.finalproject;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by duytue on 7/18/17.
 */

public class PlaceViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    CardView cardView;
    TextView nameView, descView;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
        cardView = (CardView)itemView.findViewById(R.id.cardView);
        nameView = (TextView)itemView.findViewById(R.id.nameView);
        descView = (TextView)itemView.findViewById(R.id.addView);
    }
}
