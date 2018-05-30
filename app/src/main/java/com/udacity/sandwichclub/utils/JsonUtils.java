package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameJson = jsonObject.getJSONObject("name");
            List<String> ingredients = new ArrayList<>();
            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");
            JSONArray aliasJSON = nameJson.getJSONArray("alsoKnownAs");
            for (int i =0;i<ingredientsJsonArray.length();i++) {
                ingredients.add(ingredientsJsonArray.get(i).toString());
            }
            for(int i=0;i<aliasJSON.length();i++){
                alsoKnownAs.add(aliasJSON.get(i).toString());
            }
            sandwich.setMainName(nameJson.getString("mainName"));
            sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            sandwich.setDescription(jsonObject.getString("description"));
            sandwich.setImage(jsonObject.getString("image"));
            sandwich.setIngredients(ingredients);
            sandwich.setAlsoKnownAs(alsoKnownAs);
        }
        catch(JSONException e){
            Log.e(JsonUtils.class.getSimpleName(),"Error while parsing json");
        }
        return sandwich;
    }
}
