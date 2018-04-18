package com.duytue.finalproject.Information;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duytue.finalproject.R;

import java.util.ArrayList;


/**
 * Created by duytue on 7/30/17.
 */

public class RowAdapter extends RecyclerView.Adapter<RowViewHolder> {
    Context context;
    ArrayList<String> list;

    public RowAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.information_row, parent, false);

        RowViewHolder rowViewHolder = new RowViewHolder(itemView);
        return rowViewHolder;
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {
        bindCardView(holder, list.get(position));
        setCardViewOnClick(holder, position);
    }

    private void setCardViewOnClick(final RowViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkBox.performClick();
                if (holder.checkBox.isChecked()) {
                    String color = "#cfd8dc";
                    holder.cv.setBackgroundColor(Color.parseColor(color));
                }
                else {
                    String color = "#ffffff";
                    holder.cv.setBackgroundColor(Color.parseColor(color));
                }
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.checkBox.performClick();
                if (holder.checkBox.isChecked()) {
                    String color = "#cfd8dc";
                    holder.cv.setBackgroundColor(Color.parseColor(color));
                }
                else {
                    String color = "#ffffff";
                    holder.cv.setBackgroundColor(Color.parseColor(color));
                }
            }
        });
    }


    private void bindCardView(RowViewHolder holder, String s) {
        holder.textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
