package com.example.anthony.myapplication;


import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.InvocationException;
import com.moomeen.endo2java.error.LoginException;
import com.moomeen.endo2java.model.AccountInfo;
import com.moomeen.endo2java.model.DetailedWorkout;
import com.moomeen.endo2java.model.Workout;



import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class EndoLogin {

    String userName="adrianna.kousik@gmail.com";
    String password="comp354project";

    public EndomondoSession loginTest() {
        EndomondoSession session = new EndomondoSession(userName, password);
        try{
            session.login();
        }catch(LoginException e){
            //LOG.error("exception while trying to login user: {}"+ userName+" "+ e);
            System.err.println("exception while trying to login user: {"+ userName+"} {"+ e+"}");
        }
        return session;
    }



}
