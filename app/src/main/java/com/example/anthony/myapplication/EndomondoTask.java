package com.example.anthony.myapplication;


import android.os.AsyncTask;
import android.widget.Toast;

import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.InvocationException;
import com.moomeen.endo2java.error.LoginException;
import com.moomeen.endo2java.model.AccountInfo;
import com.moomeen.endo2java.model.DetailedWorkout;
import com.moomeen.endo2java.model.Workout;





class EndomondoTask extends AsyncTask<String,Void,Boolean> {
    //String userName="adrianna.kousik@gmail.com";
   // String password="comp354project";
    String email;
    String password;
    public AsyncResponse delegate =null;


    @Override
    protected Boolean doInBackground(String... strings) {
        EndomondoSession session = new EndomondoSession("bobendo354@gmail.com", "concordia354");
        try{
            session.login();
            return true;
        }catch(LoginException e){
            //LOG.error("exception while trying to login user: {}"+ userName+" "+ e);

            //Toast.makeText("There was an error loging you in",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
            // System.err.println("exception while trying to login user: {"+ userName+"} {"+ e+"}");
        }catch(InvocationException e){
            e.printStackTrace();
        }
       return false;
    }



//    protected void onPostExecute(AccountInfo info) {
//        delegate.proccessFinished(info.getFirstName());
//        // TODO: do something with the feed
//    }

}
