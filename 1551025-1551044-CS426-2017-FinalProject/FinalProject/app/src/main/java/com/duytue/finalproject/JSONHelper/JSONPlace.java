package com.duytue.finalproject.JSONHelper;

import android.util.Log;

import com.duytue.finalproject.DownloadTask.PlaceImageDownloader;
import com.duytue.finalproject.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.duytue.finalproject.MainActivity.placesList;

/**
 * Created by Phy on 7/28/2017.
 */

public class JSONPlace extends JSONHelper {
    @Override
    public void parseJSON(String s) {
        try {

           int placeID;
            String name;
            String address;
            String phoneNo;
            double lat,lng;
            String imgURL;
            String websiteURL;
            String hours;

            // for image download task


            JSONArray master = new JSONArray(s);
            String[] urls = new String[master.length()];

            for (int i = 0; i < master.length(); ++i) {
                JSONObject temp = master.getJSONObject(i);
                placeID = temp.getInt("placeID");
                name = temp.getString("name");
                address = temp.getString("address");
                phoneNo = temp.getString("phoneNo");
                lat = temp.getDouble("lat");
                lng = temp.getDouble("lng");

                imgURL = temp.getString("placeImgURL");
                websiteURL = temp.getString("websiteURL");
                hours = temp.getString("hours");

                urls[i] = imgURL;
                Log.i("placeID", Integer.toString(placeID));

                placesList.add(new Place(placeID,name,address,phoneNo,new LatLng(lat, lng),imgURL,websiteURL,hours));
            }

            PlaceImageDownloader imageDownloader = new PlaceImageDownloader();
            imageDownloader.execute(urls);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
