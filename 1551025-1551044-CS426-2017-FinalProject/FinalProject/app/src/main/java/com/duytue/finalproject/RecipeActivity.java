package com.duytue.finalproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import static com.duytue.finalproject.MainActivity.recipesList;
import static com.duytue.finalproject.MainActivity.cateList;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    ArrayList<Recipe> list;

    int catePosition;
    int cateID;

    int ORIENTATION_PORTRAIT = 1;
    int ORIENTATION_LANDSCAPE = 0;
    int ORIENTATION = 1;

    public static RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recipe);
        overridePendingTransition(R.anim.animation_right_slide_left, R.anim.animation_left_slide_left);




        list = new ArrayList<>();
        mRecipeAdapter = new RecipeAdapter(this, list);

        Intent i = getIntent();

        catePosition = i.getIntExtra("position", 0);
        cateID = cateList.get(catePosition).categoryID;
        Log.i("catePosition", Integer.toString(catePosition));

        createToolbar();

        getRecipe();

        initiateRecyclerView(ORIENTATION);
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_recipe);
        toolbar.setTitle(cateList.get(catePosition).name);
        Log.i("title", cateList.get(catePosition).name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColor));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeActivity.super.onBackPressed();
                overridePendingTransition(R.anim.animation_left_slide_right, R.anim.animation_right_slide_right);
            }
        });
    }

    private void getRecipe() {
        for (int i = 0; i < recipesList.size(); ++i) {
            Recipe temp = recipesList.get(i);
            if (cateID == temp.cateID) {
                list.add(temp);
            }
        }
    }

    public void initiateRecyclerView(int orientation) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        if (orientation == ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(layoutManager);
        } else if (orientation == ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(this, 2);
            rv.setLayoutManager(layoutManager);
        }

        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ORIENTATION = 1;
            initiateRecyclerView(ORIENTATION);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ORIENTATION = 0;
            initiateRecyclerView(ORIENTATION);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.animation_left_slide_right, R.anim.animation_right_slide_right);
        // (enter animation, exit animation)
    }
}
