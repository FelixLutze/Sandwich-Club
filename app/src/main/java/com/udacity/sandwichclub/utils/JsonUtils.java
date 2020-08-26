package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Log.d("DEBUG-LOG", json);
        Sandwich sandwich = new Sandwich();
        JSONArray jsonArray;
        String[] array;

        try {
            JSONObject jsonObject = new JSONObject(json);

            sandwich.setMainName(new JSONObject(jsonObject.getString("name")).getString("mainName"));
            sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            sandwich.setDescription(jsonObject.getString("description"));
            sandwich.setImage("image");

            //Get and set AlsoKnownAs as a String list
            jsonArray = new JSONObject(jsonObject.getString("name")).getJSONArray("alsoKnownAs");
            array = new String[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++)
                array[i] = jsonArray.getString(i);
            sandwich.setAlsoKnownAs(Arrays.asList(array));

            //Get and set ingredients as a String list
            jsonArray = jsonObject.getJSONArray("ingredients");
            array = new String[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++)
                array[i] = jsonArray.getString(i);
            sandwich.setIngredients(Arrays.asList(array));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
