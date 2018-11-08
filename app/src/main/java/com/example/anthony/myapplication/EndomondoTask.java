package com.example.anthony.myapplication;


import android.os.AsyncTask;
import android.widget.Toast;

import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.InvocationException;
import com.moomeen.endo2java.error.LoginException;
import com.moomeen.endo2java.model.AccountInfo;
import com.moomeen.endo2java.model.DetailedWorkout;
import com.moomeen.endo2java.model.Sport;
import com.moomeen.endo2java.model.Workout;

import java.util.List;


class EndomondoTask extends AsyncTask<String,Void,List<Workout>> {
    String userName="adrianna.kousik@gmail.com";
    String password="comp354project";
    public AsyncResponse delegate =null;

    @Override
    protected List<Workout> doInBackground(String... strings) {
        EndomondoSession session = new EndomondoSession(strings[0], strings[1]);
        List<Workout> workouts;
        try{
            session.login();
            workouts=session.getWorkouts();
            return workouts;

        }catch(LoginException e){
            //LOG.error("exception while trying to login user: {}"+ userName+" "+ e);

            //Toast.makeText(,"There was an error loging you in",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            // System.err.println("exception while trying to login user: {"+ userName+"} {"+ e+"}");
        }catch(InvocationException e){
            e.printStackTrace();
        }
        return null;
    }


    /**this method is called automatically
    * and fed the return type of doInBackground
     * after the execution of doInBackground succeeds**/
    protected void onPostExecute(List<Workout> workouts) {

        delegate.proccessFinished(workouts);


        /*try{
            delegate.proccessFinished(session.getAccountInfo().getFirstName());
            delegate.proccessFinished(session.getWorkouts(Sport.CYCLING_SPORT.description()));
        }catch(InvocationException e){
            e.printStackTrace();
        }*/

        // TODO: do something with the feed
    }

}
