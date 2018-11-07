package com.example.anthony.myapplication;

import android.app.Activity;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatsActivity extends Activity {
    private Button EndLog;/**the button to login, this is temporary/for testing purposes**/
    private TextView Ename;/**for endomondo name, this is temporary/for testing purposes**/
    private static final String EMAIL = "bobendo354@gmail.com";
    private static final String PASSWORD = "concordia354";
    private LineGraphSeries<DataPoint> series;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        EndLog =(Button) findViewById(R.id.bt_LogAttmpt);
        Ename=(TextView)findViewById(R.id.tv_Endo_AccountName);
        GraphView graph=(GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 5),
                new DataPoint(2, 10),
                new DataPoint(3, 15),
                new DataPoint(4, 12),
                new DataPoint(5, 6)
        });

        graph.addSeries(series);

        //map to XML
        lv = (ListView) findViewById(R.id.ListStatsView);

        //convert XML options to presentable list items
        String[] strArr = getResources().getStringArray(R.array.graph_options);
        List<String> optList = Arrays.asList(strArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, optList);

        lv.setAdapter(adapter);

        EndLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EndomondoTask().execute("adrianna.kousik@gmail.com","comp354project");

            }
        });


    }
}

