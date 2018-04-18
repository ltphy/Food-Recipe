package com.duytue.finalproject.JSONHelper;

import com.duytue.finalproject.Category;
import com.duytue.finalproject.DownloadTask.CateImageDownloader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.duytue.finalproject.MainActivity.cateList;

/**
 * Created by duytue on 7/27/17.
 */

public class JSONCategory extends JSONHelper {
    @Override
    public void parseJSON(String s) {
        try {

            int cateID;
            String cateName;
            String imgURL;

            // for image download task


            JSONArray master = new JSONArray(s);
            String[] urls = new String[master.length()];
            for (int i = 0; i < master.length(); ++i) {
                JSONObject temp = master.getJSONObject(i);
                cateID = temp.getInt("categoryID");
                cateName = temp.getString("name");
                imgURL = temp.getString("imgURL");

                urls[i] = imgURL;

                cateList.add(new Category(cateID, cateName, imgURL));
            }

            CateImageDownloader imageDownloader = new CateImageDownloader();
            imageDownloader.execute(urls);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
