package com.duytue.finalproject.JSONHelper;

import com.duytue.finalproject.DownloadTask.RecipeImageDownloader;
import com.duytue.finalproject.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.duytue.finalproject.MainActivity.recipesList;

/**
 * Created by duytue on 7/27/17.
 */

public class JSONRecipe extends JSONHelper {

    @Override
    public void parseJSON(String s) {

        try {

            int id, cookID, cateID;
            String name, imageURL, desc;
            boolean bookmark;


            JSONArray master = new JSONArray(s);

            String[] urls = new String[master.length()];

            for (int i = 0; i< master.length(); ++i) {

                JSONObject temp = master.getJSONObject(i);
                id = temp.getInt("id");
                cookID = temp.getInt("cookID");
                cateID = temp.getInt("categoryID");

                name = temp.getString("name");
                imageURL = temp.getString("imageURL");
                desc = temp.getString("desc");

                bookmark = temp.getBoolean("bookmark");

                urls[i] = imageURL;

                recipesList.add(new Recipe(name, desc, id, cateID, cookID, imageURL, bookmark));
            }

            RecipeImageDownloader imageDownloader = new RecipeImageDownloader();
            imageDownloader.execute(urls);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
