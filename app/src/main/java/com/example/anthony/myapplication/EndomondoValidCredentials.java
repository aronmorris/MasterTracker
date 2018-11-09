package com.example.anthony.myapplication;

import android.os.AsyncTask;

import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.LoginException;
import com.moomeen.endo2java.model.AccountInfo;

public class EndomondoValidCredentials extends AsyncTask<String,Void,Boolean> {

    AsyncResponse delegate = null;

    @Override
    protected Boolean doInBackground(String... strings) {
        EndomondoSession session = new EndomondoSession(strings[0],strings[1]);
        try {
            session.login();
            return true;
        }

        catch (LoginException e) {
            e.printStackTrace();
            return false;
        }

    }

    protected void onPostExecute(boolean islogedin) {
        delegate.proccessFinished(islogedin);

    }
}
