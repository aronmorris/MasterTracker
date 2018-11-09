package com.example.anthony.myapplication;

import android.app.Activity;

import android.os.AsyncTask;
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
import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.LoginException;
import com.moomeen.endo2java.model.AccountInfo;

public class StatsActivity extends Activity{
    private LineGraphSeries<DataPoint> series;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(1, 1),
                new DataPoint(2, 2),
                new DataPoint(3, 3),
                new DataPoint(4, 4),
                new DataPoint(5, 5)
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

    public class BackgroundTask extends AsyncTask<String,Void,Boolean> {


        protected Boolean doInBackground(String... strings) {
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //It seems you can not just enter the passed data dirrectly eg new EndomondoSession(strings[0],strings[1]);
            //will not read it correcly :/
            String em = strings[0];
            String pa = strings[1];
            EndomondoSession endoAcc = new EndomondoSession(em,pa);
            try {
                endoAcc.login();
                return true;
            }
            catch (LoginException e) {
                e.printStackTrace();
                return false;
                //e.printStackTrace();
            }
        }

        protected void onPostExecute(Boolean result) {
            //validate(result);

        }
    }


}

