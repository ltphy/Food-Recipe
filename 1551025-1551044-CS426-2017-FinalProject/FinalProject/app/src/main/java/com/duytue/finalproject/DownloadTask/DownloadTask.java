package com.duytue.finalproject.DownloadTask;

import android.os.AsyncTask;
import android.util.Log;

import com.duytue.finalproject.JSONHelper.JSONCategory;
import com.duytue.finalproject.JSONHelper.JSONCook;
import com.duytue.finalproject.JSONHelper.JSONHelper;
import com.duytue.finalproject.JSONHelper.JSONPlace;
import com.duytue.finalproject.JSONHelper.JSONRecipe;
import com.duytue.finalproject.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by duytue on 7/5/17.
 */

public class DownloadTask extends AsyncTask<String, Void, ArrayList<String>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> result = new ArrayList<>();
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            //  Order of url: Category, Recipe, Cook, Place
            for (int i = 0 ; i < strings.length; ++i) {
                String temp = "";
                url = new URL(strings[i]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    temp += current;

                    data = reader.read();

                }
                result.add(temp);
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> s) {
        super.onPostExecute(s);

        // TODO: DATA HELPER
        Log.i("DownloadTask", "Finished");

        JSONCategory jsonCategory = new JSONCategory();
        jsonCategory.execute(s.get(0));

        JSONRecipe jsonRecipe = new JSONRecipe();
        jsonRecipe.execute(s.get(1));

        JSONCook jsonCook = new JSONCook();
        jsonCook.execute(s.get(2));

        JSONPlace jsonPlace= new JSONPlace();
        jsonPlace.execute(s.get(3));
    }
}
