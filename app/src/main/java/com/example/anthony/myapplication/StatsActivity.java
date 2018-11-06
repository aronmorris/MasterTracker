package com.example.anthony.myapplication;

import android.app.Activity;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.Arrays;
import java.util.List;

public class StatsActivity extends Activity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //map to XML
        lv = (ListView) findViewById(R.id.ListStatsView);

        //convert XML options to presentable list items
        String[] strArr = getResources().getStringArray(R.array.graph_options);
        List<String> optList = Arrays.asList(strArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, optList);

        lv.setAdapter(adapter);

    }
}

