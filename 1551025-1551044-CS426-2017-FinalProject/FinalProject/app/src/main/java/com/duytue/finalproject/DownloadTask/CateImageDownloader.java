package com.duytue.finalproject.DownloadTask;

import android.graphics.Bitmap;

import com.duytue.finalproject.MainActivity;

import java.util.ArrayList;

import static com.duytue.finalproject.MainActivity.cateList;
import static com.duytue.finalproject.MainActivity.mCateAdapter;

/**
 * Created by duytue on 7/27/17.
 */

public class CateImageDownloader extends ImageDowloader {
    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmaps) {

        for (int i = 0 ; i < bitmaps.size(); ++i) {
            cateList.get(i).setImg(bitmaps.get(i));
            mCateAdapter.notifyDataSetChanged();
        }

        MainActivity.progressDialog.dismiss();

    }
}
