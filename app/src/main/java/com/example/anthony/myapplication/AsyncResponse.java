package com.example.anthony.myapplication;

import com.moomeen.endo2java.EndomondoSession;

public interface AsyncResponse {

    void proccessFinished(String output);

    void proccessFinished(EndomondoSession session);

}
