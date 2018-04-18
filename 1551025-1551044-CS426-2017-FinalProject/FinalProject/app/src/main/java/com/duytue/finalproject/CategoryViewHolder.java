package com.duytue.finalproject;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Phy on 7/26/2017.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    CardView cardView;
    public CategoryViewHolder(View itemView) {
       super (itemView);
        imageView = (ImageView)itemView.findViewById(R.id.iv_category);
        cardView = (CardView)itemView.findViewById(R.id.cv_category);
    }
}
