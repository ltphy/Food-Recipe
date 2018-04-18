package com.duytue.finalproject.DownloadTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.duytue.finalproject.MainActivity.recipesList;

/**
 * Created by duytue on 7/27/17.
 */

public class ImageDowloader extends AsyncTask<String, Void, ArrayList<Bitmap>> {
    @Override
    protected ArrayList<Bitmap> doInBackground(String... strings) {

        ArrayList<Bitmap> imageList = new ArrayList<>();

        try {

            for (int i = 0 ; i < strings.length; ++i) {

               // Log.i("Image URL", strings[i]);
                URL url = new URL(strings[i]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imageList.add(bitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageList;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
        super.onPostExecute(bitmaps);
    }
}

