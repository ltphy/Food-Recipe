package com.duytue.finalproject;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.duytue.finalproject.MainActivity.placesList;

public class PlaceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    PlaceHelper pHelper;
    public static ArrayList<Place> list = new ArrayList<>();
    int ORIENTATION_PORTRAIT = 1;
    int ORIENTATION_LANDSCAPE = 0;
    int ORIENTATION = 1;
   MyLocationManager myLocationManager;
    double latitude = 0 ,longitude = 0;
    public static PlaceAdapter mPlaceAdapter;

    //spinner
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLocationManager = new MyLocationManager(PlaceActivity.this);

        setContentView(R.layout.activity_place);
        overridePendingTransition(R.anim.animation_right_slide_left, R.anim.animation_left_slide_left);
        initialComponent();

        initiateRecyclerView(ORIENTATION);

    }

    private void initialComponent() {
        createToolbar();
        list = new ArrayList<>();
        getMyLocation();
        pHelper = new PlaceHelper(latitude,longitude);
        mPlaceAdapter = new PlaceAdapter(this,list);
        getPlace();
        initialSpinner();

    }
    private void initialSpinner()
    {
        spinner = (Spinner) findViewById(R.id.spinSort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sorts_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }
    private void getMyLocation() {
        //Toast.makeText(PlaceActivity.this,"latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
        myLocationManager.getLocation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                latitude = myLocationManager.getLatitude();
                longitude = myLocationManager.getLongitude();
              // Toast.makeText(PlaceActivity.this, "latitudeeeee:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
            }
        }, 50);
        latitude = myLocationManager.getLatitude();
        longitude = myLocationManager.getLongitude();
     //  Toast.makeText(PlaceActivity.this, "latitudeeeee:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

//controlling function

    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_place);
       // toolbar.setTitle(placesList.get(placePosition).name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColor));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.duytue.finalproject.PlaceActivity.super.onBackPressed();
                overridePendingTransition(R.anim.animation_left_slide_right, R.anim.animation_right_slide_right);
            }
        });
    }

    private void getPlace() {
        try {
            for(int i = 0 ;i< placesList.size();++i)
            {
                list.add(placesList.get(i));
            }
           pHelper.sortByDistanceDesc(list, 0, list.size()-1);
            //list = pHelper.getNNearestPlace(placesList , 0, pla)
        }
        catch(Exception e)
        {
                e.printStackTrace();

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
        rv.setAdapter(mPlaceAdapter);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                pHelper.sortByDistanceDesc(list, 0, list.size()-1);
                break;
            case 1:
                Collections.sort(list, new Comparator<Place>() {
                    @Override
                    public int compare(Place obj1, Place obj2) {
                        return obj1.name.compareToIgnoreCase(obj2.name);
                    }
                });
                break;
        }
        mPlaceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
