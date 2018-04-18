package com.duytue.finalproject.Information;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.duytue.finalproject.R;

/**
 * Created by duytue on 7/30/17.
 */

public class RowViewHolder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView textView;
    CheckBox checkBox;

    public RowViewHolder(View itemView) {
        super(itemView);
        cv = (CardView)itemView.findViewById(R.id.info_cardview);
        textView = (TextView)itemView.findViewById(R.id.info_cardview_text);
        checkBox = (CheckBox)itemView.findViewById(R.id.info_cardview_checkbox);
    }
}
