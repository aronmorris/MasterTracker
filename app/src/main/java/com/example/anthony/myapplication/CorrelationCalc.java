package com.example.anthony.myapplication;

import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;

import static java.lang.Math.sqrt;

import java.util.ArrayList;

public class CorrelationCalc extends AsyncTask<DataPoint[],Void,Integer> {
    public AsyncResponse delegate =null;
    /**
     * using the peterson correlation
     * **/
    private DataPoint[] dataSet1;
    private DataPoint[] dataSet2;

    //private DataPoint[] d1Xd2;
    private ArrayList<Double>d1Xd2 = new ArrayList<>();
    //private DataPoint[] d1sqr;
    private ArrayList<Double>d1sqr = new ArrayList<>();
    //private DataPoint[] d2sqr;
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
    protected Integer doInBackground(DataPoint[]... DataPointArrays) {
        /**
         * takes in an array list of Double data
         * in our case could be temp and speed
         * or wind and distance or any combination
         * and performs a correlation calculation on it
         * dataset1 or arraylist[0] should probably always be the fitness data
         * 
         * **/
        dataSet1 = DataPointArrays[0];
        dataSet2 =DataPointArrays[1];
        for(int i = 0;i<dataSet1.length;i++){
            d1Xd2.add( dataSet2[i].getY()*dataSet1[i].getY());
        }
        for(int i = 0; i<dataSet1.length;i++){
            d1sqr.add(dataSet1[i].getY()*dataSet1[i].getX());
        }
        for(int i = 0; i<dataSet2.length;i++){
            d2sqr.add(dataSet2[i].getY()*dataSet2[i].getY());
        }
        for(int i = 0;i<dataSet1.length;i++){
            sumofDataSet1+=dataSet1[i].getY();
        }

        for(int i = 0;i<dataSet2.length;i++){
            sumofDataSet2+=dataSet2[i].getY();
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

        return (int)((dataSet1.length*sumofd1Xd2-(sumofDataSet1*sumofDataSet2))/
                sqrt((dataSet1.length*sumofd1sqr-(sumofDataSet1*sumofDataSet1))*(dataSet1.length*sumofd2sqr-(sumofDataSet2*sumofDataSet2))));
    }

    protected void onPostExecute(int cor) {

            delegate.proccessFinished(cor);
    }

}