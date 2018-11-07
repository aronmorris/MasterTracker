package com.example.anthony.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class PredictActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // x:time , y=distance


        GraphView graph=(GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 5),
                new DataPoint(2, 10),
                new DataPoint(3, 15),
                new DataPoint(4, 12),
                new DataPoint(5, 6)
        });
        //  series=new LineGraphSeries<DataPoint>();

        //  int numDataPoints=500;
        //   for(int i=0;i<numDataPoints;i++){
        //   x=x+0.1;
        //   y=Math.sin(x);

        //    series.appendData(new DataPoint(x,y),true,500);
        //  }
        graph.addSeries(series);


    }
}
