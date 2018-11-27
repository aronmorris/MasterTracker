package com.example.anthony.myapplication;

import android.app.Activity;

import android.graphics.Color;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.moomeen.endo2java.model.Workout;

import javax.xml.datatype.Duration;

public class StatsActivity extends Activity implements AsyncResponse{
    private TextView Ename;/**for endomondo name, this is temporary/for testing purposes**/
    private TextView Average;
    private EndomondoWorkoutTask eWtask = new EndomondoWorkoutTask();
    private EndomondoAccountTask eATask = new EndomondoAccountTask();
    //private static final String EMAIL = "bobendo354@gmail.com";
    //private static final String PASSWORD = "concordia354";
    //private List<Workout> workouts;
    private ArrayList<Double> avgspeeds =new ArrayList();
    private ArrayList<Double> durations=new ArrayList();
    private ArrayList<Double> distances=new ArrayList();
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private LineGraphSeries<DataPoint> avgLine;
    ListView lv;

    private User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("User");
        //pass the string from begining....
        Ename = (TextView)findViewById(R.id.textView5);
        Average = (TextView)findViewById(R.id.textView_Average);
        eWtask.delegate=this;
        eATask.delegate=this;
        eWtask.execute(user.getEndomodoname(),user.getEndomondopass());
        eATask.execute(user.getEndomodoname(),user.getEndomondopass());


        //map to XML
        lv = (ListView) findViewById(R.id.ListStatsView);

        //convert XML options to presentable list items
        String[] strArr = getResources().getStringArray(R.array.graph_options);
        List<String> optList = Arrays.asList(strArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, optList);

        lv.setAdapter(adapter);
        /**Disable the button outright
         * then delay its activation by 3 seconds**/
        lv.setEnabled(false);
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        lv.setEnabled(true);
                    }
                });
            }
        }, 3000);

        /**the on click listener for the listview
         * check to see which item in the list view is clicked
         * gives int i for the specific item clicked
         * inside we check if each item in the list is activated
         * if it is not then it has already been clicked
         * and should not be clickable **/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    if(lv.getChildAt(0).isEnabled()){
                        double avgspeed = getAverage(avgspeeds);
                        DataPoint[] speedvalues = generateSpeedData();
                        GraphView graph=(GraphView) findViewById(R.id.graph);
                        graph.removeAllSeries();
                        GridLabelRenderer gridLabeX = graph.getGridLabelRenderer();
                        GridLabelRenderer gridLabeY = graph.getGridLabelRenderer();
                        series =new LineGraphSeries<>(speedvalues);
                        series.setColor(Color.RED);
                        series.setDrawDataPoints(true);
                        series.setDataPointsRadius(10);
                        series.setThickness(8);
                        series.setTitle("Average Speed (KM/h)");
                        avgLine = new LineGraphSeries<>(new DataPoint[] {
                                new DataPoint(0, avgspeed),
                                new DataPoint(speedvalues.length, avgspeed)
                        });
                        graph.addSeries(series);
                        graph.addSeries(avgLine);
                        graph.getViewport().setScalableY(true);
                        graph.getViewport().setScalable(true);
                        gridLabeX.setHorizontalAxisTitle("Sessions");
                        gridLabeY.setVerticalAxisTitle("Average Speed (KM/h)");
                        graph.getLegendRenderer().setVisible(true);
                        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                        Average.setText(String.format("Average Speed: %.2f km/h",avgspeed));
                        //lv.getChildAt(0).setEnabled(false);
                    }

                }else if (i==1){
                    if(lv.getChildAt(2).isEnabled()) {
                        double avgDuration = getAverage(durations);
                        DataPoint[] durationValues = generateDurationData();
                        GraphView graph = (GraphView) findViewById(R.id.graph);
                        graph.removeAllSeries();
                        GridLabelRenderer gridLabeX = graph.getGridLabelRenderer();
                        GridLabelRenderer gridLabeY = graph.getGridLabelRenderer();
                        series3 = new LineGraphSeries<>(durationValues);
                        series3.setColor(Color.BLUE);
                        series3.setDrawDataPoints(true);
                        series3.setDataPointsRadius(10);
                        series3.setThickness(4);
                        series3.setTitle("Duration (min)");
                        avgLine = new LineGraphSeries<>(new DataPoint[] {
                                new DataPoint(0, avgDuration),
                                new DataPoint(durationValues.length, avgDuration)
                        });
                        graph.addSeries(series3);
                        graph.addSeries(avgLine);
                        graph.getViewport().setScalableY(true);
                        graph.getViewport().setScalable(true);
                        gridLabeX.setHorizontalAxisTitle("Sessions");
                        gridLabeY.setVerticalAxisTitle("Duration (Minutes)");
                        graph.getLegendRenderer().setVisible(true);
                        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                        Average.setText(String.format("Average Duration: %.2f Minutes",avgDuration));
                        //lv.getChildAt(2).setEnabled(false);
                    }


                }else if(i==2){
                    if(lv.getChildAt(2).isEnabled()){
                        double avgDistance = getAverage(distances);
                        DataPoint[] distanceValues = generateDistanceData();
                        GraphView graph=(GraphView) findViewById(R.id.graph);
                        graph.removeAllSeries();
                        GridLabelRenderer gridLabeX = graph.getGridLabelRenderer();
                        GridLabelRenderer gridLabeY = graph.getGridLabelRenderer();
                        series2 =new LineGraphSeries<>(distanceValues);
                        series2.setColor(Color.GREEN);
                        series2.setDrawDataPoints(true);
                        series2.setDataPointsRadius(10);
                        series2.setThickness(4);
                        series2.setTitle("Distance (KM)");
                        avgLine = new LineGraphSeries<>(new DataPoint[] {
                                new DataPoint(0, avgDistance),
                                new DataPoint(distanceValues.length, avgDistance)
                        });
                        graph.addSeries(series2);
                        graph.addSeries(avgLine);
                        graph.getViewport().setScalableY(true);
                        graph.getViewport().setScalable(true);
                        gridLabeX.setHorizontalAxisTitle("Sessions");
                        gridLabeY.setVerticalAxisTitle("Distance (KM)");
                        graph.getLegendRenderer().setVisible(true);
                        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                        Average.setText(String.format("Average Distance: %.2f km",avgDistance));
                        //lv.getChildAt(2).setEnabled(false);
                    }

                }
            }
        });



    }

    @Override
    public void proccessFinished(String output) {
        Ename.setText("Statistics are for "+output+"'s account");
    }

    @Override
    public void proccessFinished(List<Workout> workouts) {

        for (Workout witer: workouts){
            durations.add((double)witer.getDuration().getStandardMinutes());
            distances.add(witer.getDistance());
            avgspeeds.add(witer.getSpeedAvg());
        }

    }

    @Override
    public void proccessFinished(boolean islogedin) {

    }
    private double getAverage(ArrayList<Double> data){
        double average=0;
        for (int i = 0; i<data.size();i++){
            average += data.get(i);
        }
        average = average/data.size();

        return average;
    }

    private DataPoint[] generateSpeedData(){
        int count=avgspeeds.size();
        DataPoint[] values = new DataPoint[count];

        for (int i=0;i<count;i++){
            DataPoint v = new DataPoint(i,avgspeeds.get(i));
            values[i]=v;
        }
        return values;
    }
    private DataPoint[] generateDistanceData(){
        int count=distances.size();
        DataPoint[] values = new DataPoint[count];

        for (int i=0;i<count;i++){
            DataPoint v = new DataPoint(i,distances.get(i));
            values[i]=v;
        }
        return values;
    }
    private DataPoint[] generateDurationData(){
        int count = durations.size();
        DataPoint[] values = new DataPoint[count];

        for (int i=0;i<count;i++){
            DataPoint v = new DataPoint(i,durations.get(i));
            values[i]=v;

        }
        return values;
    }

    /**
     * TODO: remove extremeties
     * TODO: selectable range up to 20 days
     * **/

}

