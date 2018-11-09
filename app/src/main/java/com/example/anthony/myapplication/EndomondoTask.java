package com.example.anthony.myapplication;


import android.os.AsyncTask;
import android.widget.Toast;

import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.InvocationException;
import com.moomeen.endo2java.error.LoginException;
import com.moomeen.endo2java.model.AccountInfo;
import com.moomeen.endo2java.model.DetailedWorkout;
import com.moomeen.endo2java.model.Workout;





class EndomondoTask extends AsyncTask<String,Void,String> {
    //String userName="adrianna.kousik@gmail.com";
   // String password="comp354project";
    public AsyncResponse delegate =null;


    @Override
    protected String doInBackground(String... strings) {
        EndomondoSession session = new EndomondoSession(strings[0], strings[1]);
        try{
            session.login();
            return session.getAccountInfo().getFirstName();
        }catch(LoginException e){
            //LOG.error("exception while trying to login user: {}"+ userName+" "+ e);
            //Toast.makeText("There was an error loging you in",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            // System.err.println("exception while trying to login user: {"+ userName+"} {"+ e+"}");
        }catch(InvocationException e){
            e.printStackTrace();
        }
        return null;
    }



   protected void onPostExecute(String str) {
        delegate.proccessFinished(str);
       // TODO: do something with the feed
    }

}
