package com.example.anthony.myapplication;

import android.app.Activity;

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
import com.moomeen.endo2java.model.Sport;
import com.moomeen.endo2java.model.Workout;

import javax.xml.datatype.Duration;

public class StatsActivity extends Activity implements AsyncResponse{
    private Button EndLog;/**the button to login, this is temporary/for testing purposes**/
    private TextView Ename;/**for endomondo name, this is temporary/for testing purposes**/
    private EndomondoTask Etask = new EndomondoTask();
    private static final String EMAIL = "bobendo354@gmail.com";
    private static final String PASSWORD = "concordia354";
    private List<Workout> workouts;
    private ArrayList<Double> avgspeeds =new ArrayList();
    private ArrayList durations=new ArrayList();
    private ArrayList<Double> distances=new ArrayList();
    private List<DataPoint> dur_over_s;
    private List<DataPoint> di_over_s;
    private LineGraphSeries<DataPoint> series;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        EndLog =(Button) findViewById(R.id.bt_LogAttmpt);
        Ename=(TextView)findViewById(R.id.tv_Endo_AccountName);
        GraphView graph=(GraphView) findViewById(R.id.graph);

        series =new LineGraphSeries<>(generateSpeedDate());
        graph.addSeries(series);

        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(1, 5),
                new DataPoint(2, 10),
                new DataPoint(3, 15),
                new DataPoint(4, 12),
                new DataPoint(5, 6)
        });

        graph.addSeries(series);*/

        //map to XML
        lv = (ListView) findViewById(R.id.ListStatsView);

        //convert XML options to presentable list items
        String[] strArr = getResources().getStringArray(R.array.graph_options);
        List<String> optList = Arrays.asList(strArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, optList);

        lv.setAdapter(adapter);
        Etask.delegate=this;
        EndLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Etask.execute("adrianna.kousik@gmail.com","comp354project");

            }
        });


    }

    @Override
    public void proccessFinished(String output) {
        Ename.setText(output);
    }

    @Override
    public void proccessFinished(EndomondoSession session) {
        try{
            workouts = session.getWorkouts();
        }catch(InvocationException e){
            e.printStackTrace();
        }
        for (Workout witer: workouts){
            durations.add(witer.getDuration());
            distances.add(witer.getDistance());
            avgspeeds.add(witer.getSpeedAvg());
        }

    }

    private DataPoint[] generateSpeedDate(){
        int count=avgspeeds.size();
        DataPoint[] values = new DataPoint[count];

        for (int i=0;i<count;i++){
            DataPoint v = new DataPoint(i,avgspeeds.get(i));
            values[i]=v;
        }
        return values;
    }
}

