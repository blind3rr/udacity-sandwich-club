package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.util.Pair;

import com.udacity.sandwichclub.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;

        try {
            MyJsonRecord sandwichJson;
            sandwichJson = new MyJsonRecord(json);
            mainName = sandwichJson.getMyJsonRecord("name").getMyJsonString("mainName").toString();
            alsoKnownAs = sandwichJson.getMyJsonRecord("name").getMyJsonStringArray("alsoKnownAs").getValues();
            placeOfOrigin = sandwichJson.getMyJsonString("placeOfOrigin").toString();
            description = sandwichJson.getMyJsonString("description").toString();
            image = sandwichJson.getMyJsonString("image").toString();
            ingredients = sandwichJson.getMyJsonStringArray("ingredients").getValues();
            Log.d("parseSandwichJson()","mainName = "+mainName);
            return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        }catch(Exception e){
            Log.e("parseSandwichJson","Json construction failed.");
        }
        return null;
    }

}