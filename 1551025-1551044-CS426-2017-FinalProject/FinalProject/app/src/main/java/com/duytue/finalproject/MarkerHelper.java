package com.duytue.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duytue.finalproject.Modules.DirectionFinder;
import com.duytue.finalproject.Modules.DirectionFinderListener;
import com.duytue.finalproject.Modules.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Phy on 7/28/2017.
 */

public class MarkerHelper implements DirectionFinderListener{

    GoogleMap mMap;
    Context context;
    View contentView;
    View mapView;
    Button btnFindDirection;
    OnInterInfoWindowTouchListener lsClick;
    MapWrapperLayout mapWrapperLayout;
    LinearLayout layoutFindDirection;

    Place place; //place of Market
    LatLng myPlace;// current position

    //findDirection
    List<Marker> originMarkers = new ArrayList<>();
    List<Marker> destinationMarkers = new ArrayList<>();
    List<Polyline> polylinePaths = new ArrayList<>();
    ProgressDialog progressDialog;

    TextView tvDestination;
    TextView tvDistance;
    TextView tvTime;

    Marker myMarker;
    public MarkerHelper(MapsActivity context, MapWrapperLayout mapWrapperLayout, GoogleMap mMap, Place place, LatLng myPlace) {
        this.context = context;
        this.mapWrapperLayout = mapWrapperLayout;
        this.place = place;
        this.mMap= mMap;
        this.myPlace = myPlace;
        contentView = LayoutInflater.from(context).inflate(R.layout.content, null);
        btnFindDirection = (Button) contentView.findViewById(R.id.btn_info);

        layoutFindDirection = context.findViewById(R.id.findDirection);
        tvDestination = context.findViewById(R.id.tv_destination);
        tvDistance = context.findViewById(R.id.tv_distance);
        tvTime = context.findViewById(R.id.tv_time);

    }

    public void viewPlaceMarker()
    {
        try {
            if (place.latLng.latitude != 0 || place.latLng.longitude != 0) {

                double latitude = place.latLng.latitude;
                double longitude = place.latLng.longitude;
                LatLng placeMarker = new LatLng(latitude,longitude);
                MarkerOptions options = new MarkerOptions();
                options.position(placeMarker)
                        .title(place.getName());
                if (place.getImg() != null)
                    options.icon(BitmapDescriptorFactory.fromBitmap(resizeCircularMarkerBitmap(place.getImg(), 100, 100)));
                myMarker = mMap.addMarker(options);
                myMarker.setTag(place);

          mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(placeMarker) // Set the center of the map to HCMUS
                    .zoom(14)    //Sets the zoom (1<=20)
                    .bearing(90) //Sets the orientation of the camera to east
                    .tilt(30)    //Sets the tilt of the camera to 30 degrees
                    .build();
//
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                lsClick = new OnInterInfoWindowTouchListener(btnFindDirection) {
                    @Override
                    protected void onClickConfirmed(View v,Marker marker) {


                        sendRequest();
                        layoutFindDirection.setVisibility(v.VISIBLE);
                        Log.d("INFO WINDOW", "Clicked");
                        tvDestination.setText(place.name+": "+place.address);
                    }

                };

                btnFindDirection.setOnTouchListener(lsClick);


                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        Place markerPlace = (Place)marker.getTag();
                        lsClick.setMarker(marker);
                        TextView name = contentView.findViewById(R.id.name);
                        TextView location = contentView.findViewById(R.id.location);

                        name.setText(marker.getTitle());
                        btnFindDirection.setVisibility(View.VISIBLE);

                        if(marker.getTitle().equals("Location: Your location")) {
                            btnFindDirection.setVisibility(View.GONE);
                        }
                        else {
                            try {
                                location.setText(markerPlace.getAddress());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        mapWrapperLayout.setMarkerWithInfoWindow(marker, contentView);
                        return contentView;
                    }
                });
            } else {
                //prompt to user

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void viewUserMarker()
        {
            if (myPlace.latitude != 0 ||  myPlace.longitude!= 0) {

                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("unnamed", "mipmap", context.getPackageName()));
                Marker myPlaceMarker = mMap.addMarker(new MarkerOptions()
                        .position(myPlace)
                        .title("Location: Your location")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapMarkerBitmap(bmp, 100, 100)))
                );
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(myPlace) // Set the center of the map to HCMUS
                        .zoom(18)    //Sets the zoom (1<=20)
                        .bearing(90) //Sets the orientation of the camera to east
                        .tilt(30)    //Sets the tilt of the camera to 30 degrees
                        .build();
//
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            } else {
                //prompt to user

            }
        }

    public Bitmap resizeCircularMarkerBitmap(Bitmap bmp, int width, int height)
    {
        Bitmap Marker = Bitmap.createScaledBitmap(bmp, width, height, false);
        return getCircularBitmapWithWhiteBorder(Marker,2);

    }
    public Bitmap resizeMapMarkerBitmap(Bitmap bmp, int width, int height)
    {
        Bitmap Marker = Bitmap.createScaledBitmap(bmp, width, height, false);
        return Marker;
    }


    public static Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap,
                                                          int borderWidth) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        final int width = bitmap.getWidth() + borderWidth;
        final int height = bitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP); //
        Paint paint = new Paint();
        paint.setAntiAlias(true);//
        paint.setShader(shader);// get image into paint

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);// draw image
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(borderWidth);//
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint); // draw border'
        return canvasBitmap;
    }

    private void sendRequest() {
        String origin = String.valueOf(myPlace.latitude) + "," +String.valueOf(myPlace.longitude);
        String destination = String.valueOf(place.latLng.latitude) + "," + String.valueOf(place.latLng.longitude);

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(context, "Please wait", "Finding direction...", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
           tvDistance.setText(route.distance.text);
            tvTime.setText(route.duration.text);

            myMarker.remove();
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(place.getName())
                    .position(route.endLocation)));
            btnFindDirection.setVisibility(View.GONE);
            PolylineOptions polylineOptions = new PolylineOptions()
                    .geodesic(true)
                    .color(Color.BLUE)
                    .width(10);

            for (int i = 0; i < route.points.size(); i++) {
                polylineOptions.add(route.points.get(i));
            }

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
