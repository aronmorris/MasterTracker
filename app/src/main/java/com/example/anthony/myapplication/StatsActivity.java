package com.example.anthony.myapplication;

import android.app.Activity;

import android.graphics.Color;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.moomeen.endo2java.model.Workout;

import javax.xml.datatype.Duration;

public class StatsActivity extends Activity implements AsyncResponse{

    private TextView Ename;/**for endomondo name, this is temporary/for testing purposes**/
    private TextView Average;
    private EndomondoAccountTask eATask = new EndomondoAccountTask();
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> movingAvg;
    ListView lv;
    private RangeSeekBar rangeSeekBar;
    private GraphView graph;
    private User user=new User();
    SimpleDateFormat sd = new SimpleDateFormat("MMM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("User");
        //pass the string from begining....
        Ename = (TextView)findViewById(R.id.textView5);
        Average = (TextView)findViewById(R.id.textView_Average);
        eATask.delegate=this;
        eATask.execute(user.getEndomodoname(),user.getEndomondopass());




        //map to XML
        lv = (ListView) findViewById(R.id.ListStatsView);

        //convert XML options to presentable list items
        String[] strArr = getResources().getStringArray(R.array.graph_options);
        List<String> optList = Arrays.asList(strArr);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, optList);

        rangeSeekBar = (RangeSeekBar)findViewById(R.id.rangeBar);
        rangeSeekBar.setIndicatorTextDecimalFormat("0");

        /**the on click listener for the listview
         * check to see which item in the list view is clicked
         * gives int i for the specific item clicked
         * inside we check if each item in the list is activated
         * if it is not then it has already been clicked
         * and should not be clickable **/
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int icopy = i;
                if(i==0){
                    if(lv.getChildAt(0).isEnabled()){

                        double avgspeed = getAverage(user.getAvgspeeds());
                        final DataPoint[] speedvalues = generateSpeedData();
                        final DataPoint[] movingAverage=getMovingAverage(user.getAvgspeeds());
                        /**Display the initial graph with all the data*/
                        DisplayGraph(speedvalues,movingAverage,icopy);
                        rangeSeekBar.setRange(0,speedvalues.length);
                        rangeSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
                            @Override
                            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                                //leftValue is left seekbar value, rightValue is right seekbar value
                                DataPoint[] datacopy = Arrays.copyOfRange(speedvalues, (int)leftValue,(int)rightValue);
                                DataPoint[] mavgcopy = Arrays.copyOfRange(movingAverage, (int)leftValue,(int)rightValue);
                                /**Display the graph with the edited data*/
                                DisplayGraph(datacopy,mavgcopy,icopy);
                            }
                            @Override
                            public void onStartTrackingTouch(RangeSeekBar view,  boolean isLeft) {/* start tracking touch*/}

                            @Override
                            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {/*stop tracking touch*/}
                        });

                        Average.setText(String.format("Average Speed: %.2f km/h",avgspeed));
                        //lv.getChildAt(0).setEnabled(false);
                    }

                }else if (i==1){
                    if(lv.getChildAt(2).isEnabled()) {
                        double avgDuration = getAverage(user.getDurations());
                        final DataPoint[] durationValues = generateDurationData();
                        final DataPoint[] movingAverage=getMovingAverage(user.getDurations());
                        /**Display the initial graph with all the data*/
                        DisplayGraph(durationValues,movingAverage,icopy);
                        rangeSeekBar.setRange(0,durationValues.length);
                        rangeSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
                            @Override
                            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                                //leftValue is left seekbar value, rightValue is right seekbar value
                                DataPoint[] datacopy = Arrays.copyOfRange(durationValues, (int)leftValue,(int)rightValue);
                                DataPoint[] mavgcopy = Arrays.copyOfRange(movingAverage, (int)leftValue,(int)rightValue);
                                /**Display the graph with the edited data*/
                                DisplayGraph(datacopy,mavgcopy,icopy);
                            }
                            @Override
                            public void onStartTrackingTouch(RangeSeekBar view,  boolean isLeft) {/* start tracking touch*/}

                            @Override
                            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {/*stop tracking touch*/}
                        });


                        Average.setText(String.format("Average Duration: %.2f Minutes",avgDuration));
                        //lv.getChildAt(2).setEnabled(false);
                    }


                }else if(i==2){
                    if(lv.getChildAt(2).isEnabled()){
                        double avgDistance = getAverage(user.getDistances());
                        final DataPoint[] distanceValues = generateDistanceData();
                        final DataPoint[] movingAverage=getMovingAverage(user.getDistances());
                        /**Display the initial graph with all the data*/
                        DisplayGraph(distanceValues,movingAverage,icopy);
                        rangeSeekBar.setRange(0,distanceValues.length);
                        rangeSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
                            @Override
                            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                                //leftValue is left seekbar value, rightValue is right seekbar value
                                DataPoint[] datacopy = Arrays.copyOfRange(distanceValues, (int)leftValue,(int)rightValue);
                                DataPoint[] mavgcopy = Arrays.copyOfRange(movingAverage, (int)leftValue,(int)rightValue);
                                /**Display the graph with the edited data*/
                                DisplayGraph(datacopy,mavgcopy,icopy);

                            }
                            @Override
                            public void onStartTrackingTouch(RangeSeekBar view,  boolean isLeft) {/* start tracking touch*/}

                            @Override
                            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {/*stop tracking touch*/}
                        });

                        Average.setText(String.format("Average Distance: %.2f km",avgDistance));
                        //lv.getChildAt(2).setEnabled(false);
                    }

                }
            }
        });




    }
    private void DisplayGraph(DataPoint[] data,DataPoint[] movingAVG, int whichgraph){
        int numdates = user.getDates().size();
        graph=(GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        GridLabelRenderer gridLabeX = graph.getGridLabelRenderer();
        GridLabelRenderer gridLabeY = graph.getGridLabelRenderer();

        series =new LineGraphSeries<>(data);
        movingAvg = new LineGraphSeries<>(movingAVG);
        graph.addSeries(series);
        graph.addSeries(movingAvg);
        movingAvg.setTitle("Moving Average");
        movingAvg.setColor(Color.MAGENTA);

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


        graph.getViewport().setScalableY(true);
        graph.getViewport().setScalable(true);
        gridLabeX.setHorizontalAxisTitle("Dates");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        if(whichgraph == 0){
            gridLabeY.setVerticalAxisTitle("Average Speed (KM/h)");
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            series.setThickness(8);
            series.setTitle("Average Speed (KM/h)");
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Date d = new Date((long) dataPoint.getX());
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                    String formatted = format1.format(d.getTime());
                    DecimalFormat var = new DecimalFormat("#.##");
                    Toast.makeText(StatsActivity.this, formatted +"\n"+ var.format(dataPoint.getY())+"KM/h", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(StatsActivity.this, "Series1: On Data Point clicked: "+dataPoint.getX()+"- "+dataPoint.getY(), Toast.LENGTH_SHORT).show();
                }
            });
        }else if(whichgraph == 1){
            gridLabeY.setVerticalAxisTitle("Duration (min)");
            series.setColor(Color.BLUE);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            series.setThickness(4);
            series.setTitle("Duration (min)");
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Date d = new Date((long) dataPoint.getX());
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                    String formatted = format1.format(d.getTime());
                    DecimalFormat var = new DecimalFormat("#.##");
                    Toast.makeText(StatsActivity.this, formatted +"\n"+ var.format(dataPoint.getY())+" Minutes", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(StatsActivity.this, "Series1: On Data Point clicked: "+dataPoint.getX()+"- "+dataPoint.getY(), Toast.LENGTH_SHORT).show();
                }
            });
        }else if(whichgraph==2){
            gridLabeY.setVerticalAxisTitle("Distance (KM)");
            series.setColor(Color.GREEN);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            series.setThickness(4);
            series.setTitle("Distance (KM)");
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Date d = new Date((long) dataPoint.getX());
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                    String formatted = format1.format(d.getTime());
                    DecimalFormat var = new DecimalFormat("#.##");
                    Toast.makeText(StatsActivity.this, formatted +"\n"+ var.format(dataPoint.getY())+" KM", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(StatsActivity.this, "Series1: On Data Point clicked: "+dataPoint.getX()+"- "+dataPoint.getY(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    private double getAverage(ArrayList<Double> data){
        double average=0;
        for (int i = 0; i<data.size();i++){
            average += data.get(i);
        }
        average = average/data.size();

        return average;
    }
    private DataPoint[] getMovingAverage(ArrayList<Double> data){
        double average=0;

        DataPoint[] values = new DataPoint[data.size()];
        for (int i = 0;i<data.size();i++){
            average+=data.get(i);
            DataPoint v = new DataPoint(user.getDates().get(i),average/(i+1));
            values[i]= v;
        }

        return values;
    }

    private DataPoint[] generateSpeedData(){
        int count=user.getAvgspeeds().size();
        DataPoint[] values = new DataPoint[count];

        for (int i=0;i<count;i++){
            DataPoint v = new DataPoint(user.getDates().get(i),user.getAvgspeeds().get(i));
            values[i]=v;
        }
        return values;
    }
    private DataPoint[] generateDistanceData(){
        int count=user.getDistances().size();
        DataPoint[] values = new DataPoint[count];

        for (int i=0;i<count;i++){
            DataPoint v = new DataPoint(user.getDates().get(i),user.getDistances().get(i));
            values[i]=v;
        }
        return values;
    }
    private DataPoint[] generateDurationData(){
        int count = user.getDurations().size();
        DataPoint[] values = new DataPoint[count];

        for (int i=0;i<count;i++){
            DataPoint v = new DataPoint(user.getDates().get(i),user.getDurations().get(i));
            values[i]=v;

        }
        return values;
    }
    @Override
    public void proccessFinished(String output) {
        Ename.setText("Statistics are for "+output+"'s account");
    }

    @Override
    public void proccessFinished(List<Workout> workouts) {

    }

    @Override
    public void proccessFinished(boolean islogedin) {

    }

    @Override
    public void proccessFinished(DataPoint[] dP) {
        movingAvg = new LineGraphSeries<>(dP);
    }

    @Override
    public void proccessFinished(int cor) {

    }
}

