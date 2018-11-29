package com.example.anthony.myapplication;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class WeatherDataRetriever {

    public static Weather[] getWeatherArrayFromJsonFile(final Context context) {
        Weather[] weatherArray = new Weather[0];
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context));
            JSONArray m_jArry = obj.getJSONArray("weatherData");
            weatherArray = new Weather[m_jArry.length()];
            Weather weatherData;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("year") + "-"
                        + jo_inside.getString("month") + "-"
                        + jo_inside.getString("day"));
                int year = jo_inside.getInt("year");
                int month = jo_inside.getInt("month");
                int day = jo_inside.getInt("day");
                double maxTemp = jo_inside.getDouble("maxTemp");
                double minTemp = jo_inside.getDouble("minTemp");
                double meanTemp = jo_inside.getDouble("meanTemp");
                int totalRain_mm = jo_inside.getInt("totalRain_mm");
                int windDir_10sDeg = jo_inside.getInt("windDir");
                int windSpeed = jo_inside.getInt("windSpeed");


                weatherData = new Weather(year, month, day, maxTemp, minTemp, meanTemp,
                        totalRain_mm, windDir_10sDeg, windSpeed);

                weatherArray[i] = weatherData;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherArray;
    }

    private static String loadJSONFromAsset(final Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("weather.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
