package com.example.anthony.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moomeen.endo2java.EndomondoSession;
import com.moomeen.endo2java.error.InvocationException;
import com.moomeen.endo2java.error.LoginException;

public class EndoLoginScreen extends AppCompatActivity{

    private EditText Email2;
    private EditText Password2;
    private TextView Info;
    private Button Login2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endo_login_screen);
        Email2 = (EditText)findViewById(R.id.etEmail);
        Password2 = (EditText)findViewById(R.id.etPass);
        Login2 = (Button) findViewById(R.id.loginB);

        //the action for buttons login
        //Need to disable if already pressed, on put on timer....
        Login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = Email2.getText().toString();
                String pa = Password2.getText().toString();
                //validate(Email2.getText().toString(), Password2.getText().toString());
                new BackgroundTask().execute(Email2.getText().toString(), Password2.getText().toString());

            }
        });

    }

    private void validate(Boolean res)  {
        if(res){
            Intent intent =new Intent(EndoLoginScreen.this, Main2Activity.class);
            finish();//addded finish before starting new page to close the current page before moving on so back button doesn't go through unnecessary pages
            startActivity(intent);
        }

        else
            {
                Toast errpass = Toast.makeText(EndoLoginScreen.this, "Login failed, please enter" +
                        " a valid email address and password connected to " +
                        "your Endomondo account./n" +
                        "If you do not have an account, please register one at:\n" +
                        "https://www.endomondo.com/", Toast.LENGTH_SHORT);
                errpass.show();

        }

    }

    public class BackgroundTask extends AsyncTask<String,Void,Boolean> {


        protected Boolean doInBackground(String... strings) {
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //It seems you can not just enter the passed data dirrectly eg new EndomondoSession(strings[0],strings[1]);
            //will not read it correcly :/
            String em = strings[0];
            String pa = strings[1];
            EndomondoSession endoAcc = new EndomondoSession(em,pa);
            try {
                endoAcc.login();
                return true;
            }
            catch (LoginException e) {
                e.printStackTrace();
                return false;
                //e.printStackTrace();
            }
        }

        protected void onPostExecute(Boolean result) {
            validate(result);

        }
    }


    }











