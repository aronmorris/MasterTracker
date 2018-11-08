package com.example.anthony.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


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

    private TextView weatherText;
    final private String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=montreal&units=metric&appId=9a0db6c8d2081bbedd23fa9290db341b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        weatherText.setText("N/A");

        RequestQueue q = Volley.newRequestQueue(this);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, weatherURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main = response.getJSONObject("main");
                            Double temp = main.getDouble("temp");
                            weatherText.setText(temp.toString());
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                weatherText.setText(error.getMessage());
            }
        });

        q.add(stringRequest);

    }
}
