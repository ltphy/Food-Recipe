package com.duytue.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //current position
    //info Touch
    //voice
    Intent intentThatCalled;
    String voice2text; //added
    MyLocationManager myLocationManager = new MyLocationManager(MapsActivity.this);
    MarkerHelper markerHelper;

    //PlaceMarker
    MapWrapperLayout mapWrapperLayout;
    Place place;
    PlaceHelper placeHelper;

    //Direction

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //voice2text may use later
        intentThatCalled = getIntent();
        voice2text = intentThatCalled.getStringExtra("v2txt");
        getPlaceInfo();
        initialControl();
        //

    }

    private void getPlaceInfo() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        place = MainActivity.placesList.get(position);
//        byte[] byteArr = intent.getByteArrayExtra("placeImgURL");
//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
//        LatLng latlng = new LatLng(intent.getDoubleExtra("lat",0),intent.getDoubleExtra("lng",0));
//
//        place = new Place(intent.getStringExtra("name"),intent.getStringExtra("address"), latlng,
//                bmp);
    }

    private void initialControl() {
        mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_wrapper);

    }

    private void getMyLocation()
    {
        myLocationManager.getLocation();
        latitude = myLocationManager.getLatitude();
        longitude = myLocationManager.getLongitude();
        //Toast.makeText(MapsActivity.this, "phy" + "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    double latitude;
    double longitude;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mapWrapperLayout.init(mMap, this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //return;
        }
        else {
            mMap.setMyLocationEnabled(true);
            getMyLocation();
            LatLng myPlace = new LatLng(latitude, longitude);
            markerHelper = new MarkerHelper(MapsActivity.this, mapWrapperLayout, mMap, place, myPlace);

            markerHelper.viewUserMarker();

            // Toast.makeText(MapsActivity.this, "latitude:" + placesList.get(0).getName() + " longitude:" + placesList.get(0).getImg(), Toast.LENGTH_LONG).show();

            placeHelper = new PlaceHelper(latitude, longitude);
            try {
                markerHelper.viewPlaceMarker();
            /*nearestList = placeHelper.getNNearestPlace(3, placesList);
           Toast.makeText(MapsActivity.this, nearestList.size() + " " + nearestList.get(0).getName() + " " + nearestList.get(0).getAddress()+nearestList.get(0).getWebsiteURL()+nearestList.get(0).getPhoneNo(), Toast.LENGTH_LONG).show();
            if (nearestList.isEmpty()) {
                Toast.makeText(MapsActivity.this, "phy", Toast.LENGTH_LONG).show();
            }
            for (int i = 0; i < 3; ++i) {
                markerHelper.viewPlaceMarker(nearestList.get(i), mMap);

            }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
