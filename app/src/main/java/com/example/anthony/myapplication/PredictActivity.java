package com.example.anthony.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PredictActivity extends AppCompatActivity {
    private User user = new User();
    private GraphView graph;
    private LineGraphSeries<DataPoint> tempSeries;
    private LineGraphSeries<DataPoint> statSeries;
    SimpleDateFormat sd = new SimpleDateFormat("MMM-dd");
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




        AppDatabase db = AppDatabase.getDatabase(this);
        final WeatherDao weatherDao = db.weatherDao();
        weatherDao.deleteAll();
        //List<Weather> wList = weatherDao.getAll();
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==0){
                    


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




    }

    private DataPoint[] getTempOnWorkoutDays(WeatherDao weatherdao){
        ArrayList<Date> workoutDays = user.getDates();
        //Integer[] workoutDayTemp = new Integer[10];
        //ArrayList<Integer>y_temp = new ArrayList<>();
        SimpleDateFormat Month = new SimpleDateFormat("MM");
        SimpleDateFormat Day = new SimpleDateFormat("dd");
        double temp = 0;
        DataPoint[] dps = new DataPoint[workoutDays.size()];
        for (int i =0;i<workoutDays.size();i++){
            temp = weatherdao.findByDate(2018, Integer.parseInt(Month.format(workoutDays.get(i))), Integer.parseInt(Day.format(workoutDays.get(i)))).getMeanTemp();
            dps[i] = new DataPoint(workoutDays.get(i),temp);
        }
        //Integer[] y_axisTemp = y_temp.toArray(new Integer[y_temp.size()]);

        return dps;
    }
    private DataPoint[] getWindOnWorkoutDays(WeatherDao weatherdao){
        ArrayList<Date> workoutDays = user.getDates();
        //Integer[] workoutDayTemp = new Integer[10];
        ArrayList<Integer>y_wind = new ArrayList<>();
        SimpleDateFormat Month = new SimpleDateFormat("MM");
        SimpleDateFormat Day = new SimpleDateFormat("dd");
        double temp = 0;
        DataPoint[] dps = new DataPoint[workoutDays.size()];
        for (int i =0;i<workoutDays.size();i++){
            temp = weatherdao.findByDate(2018, Integer.parseInt(Month.format(workoutDays.get(i))), Integer.parseInt(Day.format(workoutDays.get(i)))).getWindSpeed();
            dps[i] = new DataPoint(workoutDays.get(i),temp);
            y_wind.add((int)temp);
        }
        //Integer[] y_axisWind = y_wind.toArray(new Integer[y_wind.size()]);
        return dps;
    }
    /**
     * TODO finish
     *
     * */
    private void displayGraph(DataPoint[] dps,int refresh){
        int numdates = user.getDates().size();
        graph=(GraphView) findViewById(R.id.graph);
        if(refresh==0){
            statSeries =new LineGraphSeries<>(dps);
            //graph.removeSeries(statSeries);
        }else if (refresh == 1){
            tempSeries = new LineGraphSeries<>(dps);
            //graph.removeSeries(tempSeries);
        }
        GridLabelRenderer gridLabeX = graph.getGridLabelRenderer();
        GridLabelRenderer gridLabeY = graph.getGridLabelRenderer();
        graph.addSeries(statSeries);
        graph.addSeries(tempSeries);
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX){
                    return sd.format(new Date((long)value));
                }else {
                    return super.formatLabel(value, isValueX);
                }

            }
        });
        //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(StatsActivity.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(8);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(45);
        //graph.getGridLabelRenderer().setNumVerticalLabels(13);
        graph.getViewport().setMinX(user.getDates().get(0).getTime());
        graph.getViewport().setMaxX(user.getDates().get(numdates-1).getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setLabelHorizontalHeight(110);
        graph.getGridLabelRenderer().setHumanRounding(false, true);
        //series.resetData(data);
        //movingAvg.resetData(movingAVG);


    }

}

