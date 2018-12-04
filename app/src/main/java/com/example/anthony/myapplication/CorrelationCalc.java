package com.example.anthony.myapplication;

import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;

import static java.lang.Math.sqrt;

import java.util.ArrayList;

public class CorrelationCalc extends AsyncTask<ArrayList<Double>,Void,Integer> {
    public AsyncResponse delegate =null;
    /**
     * using the peterson correlation
     * **/
    private ArrayList<Double>dataSet1 = new ArrayList<>();
    private ArrayList<Double>dataSet2 = new ArrayList<>();
    private ArrayList<Double>d1Xd2 = new ArrayList<>();
    private ArrayList<Double>d1sqr = new ArrayList<>();
    private ArrayList<Double>d2sqr = new ArrayList<>();
    private Double sumofDataSet1=0.0;
    private Double sumofDataSet2=0.0;
    private Double sumofd1Xd2=0.0;
    private Double sumofd1sqr=0.0;
    private Double sumofd2sqr=0.0;

    /**
     *
     * TODO NEEDS TO BE CHANGED TO INTEGERS
     *
     * **/
    @Override
    protected Integer doInBackground(ArrayList<Double>... arrayLists) {
        /**
         * takes in an array list of Double data
         * in our case could be temp and speed
         * or wind and distance or any combination
         * and performs a correlation calculation on it
         * dataset1 or arraylist[0] should probably always be the fitness data
         * 
         * **/
        dataSet1 = arrayLists[0];
        dataSet2 =arrayLists[1];
        for(int i = 0;i<dataSet1.size();i++){
            d1Xd2.add( dataSet2.get(i)*dataSet1.get(i));
        }
        for(int i = 0; i<dataSet1.size();i++){
            d1sqr.add(dataSet1.get(i)*dataSet1.get(i));
        }
        for(int i = 0; i<dataSet2.size();i++){
            d2sqr.add(dataSet2.get(i)*dataSet2.get(i));
        }
        for(Double d: dataSet1){
            sumofDataSet1+=d;
        }

        for(Double d: dataSet2){
            sumofDataSet2+=d;
        }

        for(Double d: d1Xd2){
            sumofd1Xd2+=d;
        }

        for(Double d: d1sqr){
            sumofd1sqr+=d;
        }
        for(Double d: d2sqr){
            sumofd2sqr+=d;
        }

        return (int)((dataSet1.size()*sumofd1Xd2-(sumofDataSet1*sumofDataSet2))/
                sqrt((dataSet1.size()*sumofd1sqr-(sumofDataSet1*sumofDataSet1))*(dataSet1.size()*sumofd2sqr-(sumofDataSet2*sumofDataSet2))));
    }

    protected void onPostExecute(int cor) {

            delegate.proccessFinished(cor);
    }

}