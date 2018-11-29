package com.example.anthony.myapplication;

import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

/**
 * THIS IS NOT USED BUT,
 * it would be the proper way
 * to do calculations on android
 * rather than on the main ui thread
 * **/

public class MovingAverageClac extends AsyncTask<ArrayList<Double>,Void,DataPoint[]> {
    public AsyncResponse delegate =null;

    @Override
    protected DataPoint[] doInBackground(ArrayList<Double>... arrayLists) {
        double average=0;

        DataPoint[] values = new DataPoint[arrayLists[0].size()];
        for (int i = 0;i<arrayLists[0].size();i++){
            average+=arrayLists[0].get(i);
            DataPoint v = new DataPoint(i,average/(i+1));
            values[i]= v;
        }

        return values;
    }

    protected void onPostExecute(DataPoint[] dP) {

        delegate.proccessFinished(dP);
    }
}
