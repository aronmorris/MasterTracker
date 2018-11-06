package com.example.anthony.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.InvocationException;
import com.moomeen.endo2java.error.LoginException;

public class StatsActivity extends AppCompatActivity {
    //blank activity
    private final String userName="adrianna.kousik@gmail.com";
    private final String password="comp354project";
    private Button Login_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);


        /**
         * Changed the endo2java library
         * to a a fork from
         * https://github.com/tomleb/endo2java
         * works slightly better i now know that
         * the error from beyond this point is because
         * it is not able to login
         * **/
        //getting the name
        Login_Button = (Button) findViewById(R.id.bt_logAttmpt);
        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndomondoSession session = new EndomondoSession(userName, password);
                try{
                    session.login();
                }catch(LoginException e){
                    e.printStackTrace();
                    //LOG.error("exception while trying to login user: {}"+ userName+" "+ e);
                    System.err.println("exception while trying to login user: {"+ userName+"} {"+ e+"}");
                }
                String endoname="fghfghfg";

                try{
                    endoname= session.getAccountInfo().getFirstName();
                }catch(InvocationException e){
                    e.printStackTrace();
                }

                TextView textview = findViewById(R.id.tv_EndoInfo);
                textview.setText(endoname);
            }
        });

    }

    /*public EndomondoSession loginTest(String userName, String password) {
        EndomondoSession session = new EndomondoSession(userName, password);
        try{
            session.login();
        }catch(LoginException e){
            //LOG.error("exception while trying to login user: {}"+ userName+" "+ e);
            System.err.println("exception while trying to login user: {"+ userName+"} {"+ e+"}");
        }
        return session;
    }*/
}
