package com.duytue.finalproject;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Phy on 7/26/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    Context context;
    ArrayList<Category> cateList;

    public CategoryAdapter (Context context, ArrayList<Category> list) {
        this.context = context;
        cateList = list;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.category_info, parent, false);

        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(itemView);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        bindCardView(holder, cateList.get(position));
        setCardViewOnClick(holder, position);
    }


    private void bindCardView(CategoryViewHolder holder, Category category) {

        if (category.img != null) {
            holder.imageView.setImageBitmap(category.img);
        }
    }

    private void setCardViewOnClick(CategoryViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRecipe(position);
            }
        });
    }

    private void displayRecipe(int position) {
        Intent i = new Intent(context, RecipeActivity.class);
        i.putExtra("position", position);
        context.startActivity(i);

    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }
}
