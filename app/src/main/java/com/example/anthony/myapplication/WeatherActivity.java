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

    private TextView weatherText, weatherText2, weatherText3, weatherText4, weatherText8, weatherText6, weatherText7;
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

                            weatherText.setText("Current: " + temp + " °C" );
                            weatherText2.setText("Maximum: " + temp_max + " °C");
                            weatherText3.setText("Minimum: " + temp_min + " °C" );


                            JSONObject wind = response.getJSONObject("wind");
                            Double wind_speed = wind.getDouble("speed");

                            weatherText4.setText("Wind Speed: " + wind_speed +  " km/hr");
                            if(wind_speed > 10)
                            weatherText6.setText("Not ideal conditions for biking!");
                            else
                                weatherText6.setText("Perfect time to bike! Make a new record!");

                            JSONObject details = response.getJSONArray("weather").getJSONObject(0);
                            weatherText7.setText(details.getString("description"));
                        } catch (JSONException e){
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
}