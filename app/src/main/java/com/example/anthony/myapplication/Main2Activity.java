package com.example.anthony.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;
import com.moomeen.endo2java.model.Workout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AsyncResponse{
    //private Calendar calendar = Calendar.getInstance();
    private User user=new User();
    private TextView changecreds;
    private TextView tDates;
    private EndomondoWorkoutTask eWtask = new EndomondoWorkoutTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        user=(User)i.getSerializableExtra("User");
        changecreds = (TextView)findViewById(R.id.textView3);
        tDates = (TextView)findViewById(R.id.textViewDates);
        eWtask.delegate=this;
        eWtask.execute(user.getEndomodoname(),user.getEndomondopass());

        changecreds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(Main2Activity.this, EndoLoginScreen.class);
                i2.putExtra("User",user);
                finish();
                startActivity(i2);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //getting the name
        //EndomondoSession session = loginTest(userName,password);
        String endoname="You have successfully connected your Endomondo account to Master Tracker! \n" +
                "Use the drop down menu at the top left corner to view your statistics";

        TextView textview = findViewById(R.id.tv_EndoInfo);
        textview.setText(endoname);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Main2Activity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    //navigation drawer options
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_stats) {
            Intent intent = new Intent(Main2Activity.this, StatsActivity.class);
            intent.putExtra("User",user);
            startActivity(intent);
        } else if (id == R.id.nav_weather) {
            Intent intent = new Intent(Main2Activity.this, WeatherActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_graph){
            Intent intent = new Intent(Main2Activity.this, Graph.class);
            startActivity(intent);
        } else if (id == R.id.nav_predic) {
            Intent intent = new Intent(Main2Activity.this, PredictActivity.class);
            intent.putExtra("User",user);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(Main2Activity.this, LocalLoginActivity.class);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        NavigationView navigationView= findViewById(R.id.nav_view);

        Menu menuNav=navigationView.getMenu();
        final MenuItem nav_item2 = menuNav.findItem(R.id.nav_stats);

        /**Disable the button outright
        * then delay its activation by 3 seconds**/
        nav_item2.setEnabled(false);
        nav_item2.setEnabled(false);
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        nav_item2.setEnabled(true);
                    }
                });
            }
        }, 2000);
        return true;
    }

    @Override
    public void proccessFinished(String output) {

    }

    @Override
    public void proccessFinished(List<Workout> workouts) {
        ArrayList<Double> avgspeeds=new ArrayList<Double>();
        ArrayList<Double> durations=new ArrayList<Double>();
        ArrayList<Double> distances=new ArrayList<Double>();
        ArrayList<Date> dates = new ArrayList<Date>();

        for (Workout witer: workouts){
            if(!(witer.getSpeedAvg()>30||witer.getDistance()<38)){
                durations.add((double)witer.getDuration().getStandardMinutes());
                distances.add(witer.getDistance());
                avgspeeds.add(witer.getSpeedAvg());
                dates.add(witer.getStartTime().toDate());
            }
        }

        //had to reverse the data becasue it takes from newest to oldest
        //we need oldest to newest
        Collections.reverse(durations);
        user.setDurations(durations);
        Collections.reverse(avgspeeds);
        user.setAvgspeeds(avgspeeds);
        Collections.reverse(distances);
        user.setDistances(distances);
        Collections.reverse(dates);
        user.setDates(dates);
        for(int i = 0; i<user.getDates().size();i++){
            tDates.append(user.getDates().get(i).toString());
        }

    }

    @Override
    public void proccessFinished(boolean islogedin) {

    }

    @Override
    public void proccessFinished(DataPoint[] dP) {

    }

    @Override
    public void proccessFinished(int cor) {

    }
}
