package com.example.anthony.myapplication;

import android.os.AsyncTask;

import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.InvocationException;
import com.moomeen.endo2java.error.LoginException;
import com.moomeen.endo2java.model.Workout;

import java.util.List;


public class EndomondoAccountTask extends AsyncTask<String,Void,String> {
    public AsyncResponse delegate =null;
    @Override
    protected String doInBackground(String... strings) {
        EndomondoSession session = new EndomondoSession(strings[0], strings[1]);
        try{
            session.login();
            return session.getAccountInfo().getFirstName();

        }catch(LoginException e){

            e.printStackTrace();

        }catch(InvocationException e){
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(String str) {

        delegate.proccessFinished(str);
    }
}
