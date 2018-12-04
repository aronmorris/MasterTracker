package com.example.anthony.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PredictActivity extends AppCompatActivity {
    private User user = new User();
    /**
     * TODO display selected weather data and sport data on same graph
     * TODO make some kind of correlation between the data
     * ex:
     * avgspeed vs windspeed & temp
     * duration vs windspeed & temp
     * distance vs windspeed & temp
     * **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("User");

        final Spinner spinner = (Spinner) findViewById(R.id.spinner3_Month);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_name_choice, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner4_TempStat);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_weather = ArrayAdapter.createFromResource(this,
                R.array.graph_extra_choice, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter_weather);

        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner5);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_endomondo = ArrayAdapter.createFromResource(this,
                R.array.endomondo_selection, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner3.setAdapter(adapter_endomondo);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==8){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        AppDatabase db = AppDatabase.getDatabase(this);
        WeatherDao weatherDao = db.weatherDao();
        weatherDao.deleteAll();
        List<Weather> wList = weatherDao.getAll();
        int month = 9;
        int month_size = weatherDao.findByYearMonth(2018, month).size();
        ArrayList<Integer> date = new ArrayList<>();
        ArrayList<Integer> y_wind = new ArrayList<>();
        ArrayList<Integer> y_temp = new ArrayList<>();

        for (int i = 0; i < month_size; i++) {
            date.add(i+1);
        }
        for (int i = 0; i < date.size(); i++) {
            int wind = weatherDao.findByDate(2018, month, date.get(i)).getWindSpeed();
            double temp = weatherDao.findByDate(2018, month, date.get(i)).getMeanTemp();
            y_wind.add(wind);
            y_temp.add((int)temp);
        }

        Integer[] y_axis1 = y_wind.toArray(new Integer[y_wind.size()]);
        Integer[] y_axis2 = y_temp.toArray(new Integer[y_temp.size()]);
        Integer[] x_axis = date.toArray(new Integer[date.size()]);






    }

    private Integer[] getWeatherOnWorkoutDays(){
        ArrayList<Date> workoutDays = user.getDates();
        Integer[] workoutDayTemp = new Integer[10];
        /**
         * TODO finish
         * **/
        for (int i =0;i<workoutDays.size();i++){

        }

        return workoutDayTemp;
    }

}

