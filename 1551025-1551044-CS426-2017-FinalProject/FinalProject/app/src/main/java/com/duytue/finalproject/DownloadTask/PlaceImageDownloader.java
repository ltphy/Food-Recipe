package com.duytue.finalproject.DownloadTask;

import android.graphics.Bitmap;
import java.util.ArrayList;

import static com.duytue.finalproject.MainActivity.placesList;


/**
 * Created by duytue on 7/27/17.
 */

public class PlaceImageDownloader extends ImageDowloader {
    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmaps) {

        for (int i = 0 ; i < bitmaps.size(); ++i) {
            placesList.get(i).setImg(bitmaps.get(i));
        }

    }
}
