package com.example.grocery.parser;

import android.util.Log;

import com.example.grocery.Grocery;
import com.example.grocery.network.HTTPConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    private final List<Grocery> groceryList = new ArrayList<>();

    public static String KEY_JSON_GROCERY = "Grocery";
    public static String KEY_JSON_TITLE="title";
    public static String KEY_JSON_PERISHABLE="perishable";
    public static String KEY_JSON_PRICE="price";


    public List<Grocery> getGroceryFromJSON(String json)
    {
        if(json != null)
        {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_JSON_GROCERY);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject groceryJson = jsonArray.getJSONObject(i);
                    String title = groceryJson.getString(KEY_JSON_TITLE);
                    String perishable = groceryJson.getString(KEY_JSON_PERISHABLE);
                    boolean p = perishable.equals("true") ? true : false;
                    float price = (float)(groceryJson.getDouble(KEY_JSON_PRICE));
                    Grocery g = new Grocery(title, p, price);
                    groceryList.add(g);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            Log.e("Json Parsing", "JSON Object inexistent!");
        }
        return groceryList;
    }



}
