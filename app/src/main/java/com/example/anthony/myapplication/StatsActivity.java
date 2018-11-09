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

public class StatsActivity extends Activity{
    private LineGraphSeries<DataPoint> series;
    ListView lv;

    private EndomondoSession endoAcc;
    private String email;
    private String password;
    private List<Workout> listOfWorkouts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        password = intent.getExtras().getString("password");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
         //pass the string from begining....
         endoAcc = new EndomondoSession(email,password);
         new BackgroundTask(endoAcc).execute();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        double n;
        if(listOfWorkouts.size()>0){
            n = listOfWorkouts.get(0).getDistance();
        }
        else{
            n = 44;
        }


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0.1, 1),
                new DataPoint(0.2, 2),
                new DataPoint(0.3, 3),
                new DataPoint(0.4, 4),
                new DataPoint(n, 5)
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





    public class BackgroundTask extends AsyncTask<EndomondoSession,Void,List<Workout>> {
        private EndomondoSession endoAcc;
        private List<Workout> workOutList;

        BackgroundTask(EndomondoSession endoAcc){
            this.endoAcc=endoAcc;
        }


//

        protected List<Workout> doInBackground(EndomondoSession... endomondoSessions) {



                try {

                    workOutList = new ArrayList<Workout>(endoAcc.getWorkouts());
                    return workOutList;
                }
                catch (InvocationException e) {
                    e.printStackTrace();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }

            return workOutList;
        }

        protected void onPostExecute(List<Workout> result) {
            listOfWorkouts = new ArrayList<Workout>(result);


        }


    }


}

