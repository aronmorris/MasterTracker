package com.example.anthony.myapplication;

import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.model.Workout;

import java.util.List;

public interface AsyncResponse {

    void proccessFinished(String output);

    void proccessFinished(List<Workout> workouts);

}