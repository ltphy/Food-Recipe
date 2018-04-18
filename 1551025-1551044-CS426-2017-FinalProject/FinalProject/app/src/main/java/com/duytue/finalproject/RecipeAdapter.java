package com.duytue.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duytue.finalproject.Information.InformationActivity;

import java.util.ArrayList;

/**
 * Created by duytue on 7/18/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    Context context;
    ArrayList<Recipe> recipesList;

    public RecipeAdapter (Context context, ArrayList<Recipe> list) {
        this.context = context;
        recipesList = list;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recipe_row_info, parent, false);

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(itemView);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        bindCardView(holder, recipesList.get(position));
        setCardViewOnClick(holder, position);
    }

    private void bindCardView(RecipeViewHolder holder, Recipe recipe) {
        holder.nameView.setText(recipe.name);
        holder.descView.setText(recipe.desc);
        if (recipe.img != null) {
            holder.imageView.setImageBitmap(recipe.img);
        }
    }

    private void setCardViewOnClick(RecipeViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInformationActivity(position);
            }
        });
    }

    private void startInformationActivity(int position) {
        Intent intent = new Intent(context, InformationActivity.class);
        int newPosition = position;
        for (int i = 0 ; i < MainActivity.recipesList.size(); ++i) {
            if (recipesList.get(position) == MainActivity.recipesList.get(i)) {
                newPosition = i;
            }
        }
        intent.putExtra("position", newPosition);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }
}
