package com.example.anthony.myapplication;

import com.jjoe64.graphview.series.DataPoint;
import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.model.Workout;

import java.util.List;

public interface AsyncResponse {

    void proccessFinished(String output);

    void proccessFinished(List<Workout> workouts);

    void proccessFinished(boolean islogedin);

    void proccessFinished(DataPoint[] dP);

}
