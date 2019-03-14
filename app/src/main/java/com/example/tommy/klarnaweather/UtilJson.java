package com.example.tommy.klarnaweather;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class UtilJson {
    private UtilJson(){}

    public static final String JSON_URL =
            "https://api.darksky.net/forecast/2bb07c3bece89caf533ac9a5d23d8417/";


    public static URL buildUrl (String coordinates){

        URL url = null;
        Uri uri = Uri.parse(JSON_URL).buildUpon()
                .appendPath(coordinates)
                .build();
        try {
            url = new URL(uri.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
    public static String getJson(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        }
        catch (Exception e){
            Log.d("Error", e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }

    public static ArrayList<Weater>getWeaterFromJson(String json){
        final String TIMEZONE = "timezone";
        final String TIME = "time";
        final String SUMMARY = "summary";
        final String TEMPERATURE = "temperature";
        final String ITEMS = "currently";

        ArrayList<Weater> weater=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject currently = jsonObject.getJSONObject(ITEMS);
            Weater w = new Weater(
                    jsonObject.getString(TIMEZONE),
                    currently.getString(TIME),
                    currently.getString(TEMPERATURE),
                    currently.getString(SUMMARY)

            );
            weater.add(w);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return weater;
    }
}
