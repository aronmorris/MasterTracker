package com.example.anthony.myapplication;

import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.InvocationException;
import com.moomeen.endo2java.error.LoginException;
import com.moomeen.endo2java.model.AccountInfo;
import com.moomeen.endo2java.model.Workout;
import com.moomeen.endo2java.schema.response.WorkoutsResponse;

public class StatsActivity extends Activity implements AsyncResponse{
    private LineGraphSeries<DataPoint> series;
    ListView lv;

    //private EndomondoSession endoAcc;
    private User user=new User();
    private List<Workout> listOfWorkouts;
    private EndomondoTask eTask = new EndomondoTask();
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("User");
        //pass the string from begining....
         //endoAcc = new EndomondoSession(email,password);
         tv = (TextView)findViewById(R.id.textView6);
         eTask.delegate=this;
         eTask.execute(user.getEndomodoname(),user.getEndomondopass());

         GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0.1, 1),
                new DataPoint(0.2, 2),
                new DataPoint(0.3, 3),
                new DataPoint(0.4, 4),
                new DataPoint(1, 5)
        });

        graph.addSeries(series);




        //map to XML
        lv = (ListView) findViewById(R.id.ListStatsView);

        //convert XML options to presentable list items
        String[] strArr = getResources().getStringArray(R.array.graph_options);
        List<String> optList = Arrays.asList(strArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, optList);

        lv.setAdapter(adapter);


    }

    @Override
    public void proccessFinished(String output) {
        tv.setText(output);
    }

    @Override
    public void proccessFinished(boolean logedin) {

    }



}

