package com.duytue.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.duytue.finalproject.DownloadTask.DownloadTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Category> cateList;
    public static ArrayList<Recipe> recipesList;
    public static ArrayList<Cook> cookList;
    public static ArrayList<Place> placesList;

    public static ArrayList<Recipe> smallList;

    public static CategoryAdapter mCateAdapter;

    // private SQLiteDatabase mainDB;

    public static ProgressDialog progressDialog;

    int ORIENTATION_PORTRAIT = 1;
    int ORIENTATION_LANDSCAPE = 0;
    int ORIENTATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //overridePendingTransition(R.anim.animation_left_slide_right, R.anim.animation_right_slide_left);
        setContentView(R.layout.activity_main);

        if (cateList == null) {
            initiateDataList();
        }


        mCateAdapter = new CategoryAdapter(MainActivity.this, cateList);

        initiateRecyclerView(ORIENTATION);

        createToolbar();
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle("Food Companion");
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColor));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //return to appropriate activity
            }
        });


    }


    private void initiateDataList() {
        cateList = new ArrayList<>();
        recipesList = new ArrayList<>();
        cookList = new ArrayList<>();
        smallList = new ArrayList<>();
        placesList = new ArrayList<>();


        getDataFromDatabase(cateList);
    }

    private void getDataFromDatabase(ArrayList<Category> cateList) {

        DownloadTask downloadTask = new DownloadTask();
        // url list
        // cate: http://www.json-generator.com/api/json/get/cfDvfhsAte?indent=2
        // recipe: http://www.json-generator.com/api/json/get/bVjJwoBDWW?indent=2
        // cook:    http://www.json-generator.com/api/json/get/bUxcuwvLyq?indent=2
        // place:   http://www.json-generator.com/api/json/get/bOXDEsurUy?indent=2

        downloadTask.execute("http://www.json-generator.com/api/json/get/cfDvfhsAte?indent=2", "http://www.json-generator.com/api/json/get/bVjJwoBDWW?indent=2", "http://www.json-generator.com/api/json/get/bUxcuwvLyq?indent=2", "http://www.json-generator.com/api/json/get/bOXDEsurUy?indent=2");

        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Fetching list");
        progressDialog.setMessage("Just a moment...");
        progressDialog.show();

        //sample
        // http://www.json-generator.com/api/json/get/bUYxZygxVe?indent=2

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
        rv.setAdapter(mCateAdapter);
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


    public void startSearch(View view) {
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }
}
