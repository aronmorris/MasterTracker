package com.example.anthony.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private TextView weatherText, weatherText2, weatherText3, weatherText4, weatherText8, weatherText6, weatherText7;
    final private String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=montreal&units=metric&appId=9a0db6c8d2081bbedd23fa9290db341b";
    private User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("User");
        final double best_distance = getAverage(user.getDurations());

        // Testing DB
        AppDatabase db = AppDatabase.getDatabase(this);
        WeatherDao weatherDao = db.weatherDao();
        weatherDao.deleteAll();
        List<Weather> wList = weatherDao.getAll();
        if (wList.size() == 0) {
            Log.d("*-*-*-*-*-*-*-**-*--*", "Adding stuff");
            weatherDao.insertAll(WeatherDataRetriever.getWeatherArrayFromJsonFile(this));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherText = (TextView) findViewById(R.id.weatherText);
        weatherText2 = (TextView) findViewById(R.id.weatherText2);
        weatherText3 = (TextView) findViewById(R.id.weatherText3);
        weatherText4 = (TextView) findViewById(R.id.weatherText4);
        weatherText6 = (TextView) findViewById(R.id.weatherText6);
        weatherText7 = (TextView) findViewById(R.id.weatherText8);

        weatherText8 = (TextView) findViewById(R.id.weatherText7);
        RequestQueue q = Volley.newRequestQueue(this);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, weatherURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            weatherText8.setText("Today's Forecast");

                            JSONObject main = response.getJSONObject("main");
                            Double temp = main.getDouble("temp");
                            Double temp_min = main.getDouble("temp_min");
                            Double temp_max = main.getDouble("temp_max");

                            weatherText.setText("Current: " + temp + " °C");
                            weatherText2.setText("Maximum: " + temp_max + " °C");
                            weatherText3.setText("Minimum: " + temp_min + " °C");


                            JSONObject wind = response.getJSONObject("wind");
                            Double wind_speed = wind.getDouble("speed");

                            weatherText4.setText("Wind Speed: " + wind_speed + " km/hr");

                            JSONObject details = response.getJSONArray("weather").getJSONObject(0);
                            String description = details.getString("description");
                            weatherText7.setText(description);

                            if (!tobike(wind_speed, description, temp))
                                weatherText6.setText("Not ideal conditions for biking!");
                            else
                                weatherText6.setText("Perfect time to bike! Make a new record!\n " + Math.round(best_distance) + " km");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                weatherText.setText(error.getMessage());
                weatherText2.setText(error.getMessage());
                weatherText3.setText(error.getMessage());
                weatherText4.setText(error.getMessage());

                weatherText6.setText(error.getMessage());

            }


        });

        q.add(stringRequest);

    }

    public boolean tobike(double wind_speed, String description, double temp) {
        boolean value = false;

        if (wind_speed > 10 || description.contains("snow") || description.contains("rain") || description.contains("heavy") || temp < -20) {
            value = false;
        } else
            value = true;

        return value;
    }
    private double getAverage(ArrayList<Double> data){
        double average=0;
        for (int i = 0; i<data.size();i++){
            average += data.get(i);
        }
        average = average/data.size();

        return average;
    }
}