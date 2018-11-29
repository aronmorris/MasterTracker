package com.example.anthony.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.util.Log;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Graph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_graph);


       /* GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);*/

     final  Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_choice, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.graph_choice, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        final Button button = findViewById(R.id.button_id);



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String x = ((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString();
                String y = ((Spinner)findViewById(R.id.spinner2)).getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(),"we here tho" + x, Toast.LENGTH_LONG).show();
                Log.d("String x " ,x + " String y " + y);
                get_json(x,y);


            }
        });


    }


    ArrayList<Integer> date = new ArrayList<>();
    ArrayList<Integer> y_choice = new ArrayList<>();
    public void get_json(String x, String y) {
        String json;

        try {
            InputStream is = getAssets().open("historical_weather.json");
            Toast.makeText(getApplicationContext(),"yay!", Toast.LENGTH_LONG).show();
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();


            json = new String(buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                if(obj.getString("Month").compareTo(x) == 0) {
                    date.add(obj.getInt("Day"));
                    y_choice.add(obj.getInt(y));

//Ookok so basically, I need user input to choose what the x values need to be and what the y values need to be...
                }
            }

            Integer[] y_axis = y_choice.toArray(new Integer[y_choice.size()]);
            Integer[] x_axis = date.toArray(new Integer[date.size()]);


            GraphView graph;
            graph = (GraphView) findViewById(R.id.graph);
            graph.removeAllSeries();

           //  int[] y = {0,1,2,3,4,5,6,7,8,9,10};
            DataPoint[] dp = new DataPoint[x_axis.length];

            for(int i = 0;i < x_axis.length; i++){
                dp[i] = new DataPoint(x_axis[i], y_axis[i]);
                Log.d("Creation", x_axis[i] + "");
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);

        graph.addSeries(series);


           // Toast.makeText(getApplicationContext(), y_choice.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
//going to make it so they selsct the monnth, year and the y axis