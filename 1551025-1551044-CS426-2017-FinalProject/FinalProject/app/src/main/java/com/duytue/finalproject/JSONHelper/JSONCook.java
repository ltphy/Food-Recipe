package com.duytue.finalproject.JSONHelper;

import android.util.Log;

import com.duytue.finalproject.Cook;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.duytue.finalproject.MainActivity.cookList;

/**
 * Created by duytue on 7/27/17.
 */

public class JSONCook extends JSONHelper {@Override
public void parseJSON(String s) {

    try {

        int id;


        JSONArray master = new JSONArray(s);

        for (int i = 0; i< master.length(); ++i) {
            ArrayList<String> ingredients = new ArrayList<>(), instruction = new ArrayList<>();

            JSONObject temp = master.getJSONObject(i);

            id = temp.getInt("id");

            JSONArray ingre = temp.getJSONArray("ingredient");

            for (int j = 0 ; j < ingre.length(); ++j) {
                ingredients.add(ingre.getString(j));
            }

            JSONArray instr = temp.getJSONArray("instruction");
            for (int j = 0 ; j < instr.length(); ++j) {
                instruction.add(instr.getString(j));
            }

            cookList.add(new Cook(id, instruction, ingredients));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

}
}