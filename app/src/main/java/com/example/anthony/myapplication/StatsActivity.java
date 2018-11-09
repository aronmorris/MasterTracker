package com.example.anthony.myapplication;

import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class StatsActivity extends Activity implements AsyncResponse{
    private TextView Ename;/**for endomondo name, this is temporary/for testing purposes**/
    private EndomondoWorkoutTask eWtask = new EndomondoWorkoutTask();
    private EndomondoAccountTask eATask = new EndomondoAccountTask();
    private static final String EMAIL = "bobendo354@gmail.com";
    private static final String PASSWORD = "concordia354";
    //private List<Workout> workouts;
    private ArrayList<Double> avgspeeds =new ArrayList();
    private ArrayList durations=new ArrayList();
    private ArrayList<Double> distances=new ArrayList();
    private List<DataPoint> dur_over_s;
    private List<DataPoint> di_over_s;
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Ename = (TextView)findViewById(R.id.textView5);
        eWtask.delegate=this;
        eATask.delegate=this;
        eWtask.execute("adrianna.kousik@gmail.com","comp354project");
        eATask.execute("adrianna.kousik@gmail.com","comp354project");


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
                        GraphView graph=(GraphView) findViewById(R.id.graph);
                        GridLabelRenderer gridLabeX = graph.getGridLabelRenderer();
                        GridLabelRenderer gridLabeY = graph.getGridLabelRenderer();
                        series =new LineGraphSeries<>(generateSpeedData());
                        series.setColor(Color.RED);
                        series.setDrawDataPoints(true);
                        series.setDataPointsRadius(10);
                        series.setThickness(8);
                        series.setTitle("Average Speed (KM/h)");
                        graph.addSeries(series);
                        graph.getViewport().setScalableY(true);
                        graph.getViewport().setScalable(true);
                        gridLabeX.setHorizontalAxisTitle("Sessions");
                        gridLabeY.setVerticalAxisTitle("Average Speed (KM/h)");
                        graph.getLegendRenderer().setVisible(true);
                        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                        lv.getChildAt(0).setEnabled(false);
                    }

                }else if (i==1){

                }else if(i==2){
                    if(lv.getChildAt(2).isEnabled()){
                        GraphView graph=(GraphView) findViewById(R.id.graph);
                        GridLabelRenderer gridLabeX = graph.getGridLabelRenderer();
                        GridLabelRenderer gridLabeY = graph.getGridLabelRenderer();
                        series2 =new LineGraphSeries<>(generateDistanceData());
                        series2.setColor(Color.GREEN);
                        series2.setDrawDataPoints(true);
                        series2.setDataPointsRadius(10);
                        series2.setThickness(4);
                        series2.setTitle("Distance (KM)");
                        graph.addSeries(series2);
                        graph.getViewport().setScalableY(true);
                        graph.getViewport().setScalable(true);
                        gridLabeX.setHorizontalAxisTitle("Sessions");
                        gridLabeY.setVerticalAxisTitle("Distance (KM)");
                        graph.getLegendRenderer().setVisible(true);
                        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                        lv.getChildAt(2).setEnabled(false);
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
            durations.add(witer.getDuration());
            distances.add(witer.getDistance());
            avgspeeds.add(witer.getSpeedAvg());
        }

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

}

