package com.example.anthony.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
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

public class EndoLoginScreen extends AppCompatActivity implements AsyncResponse{

    private EditText Email2;
    private EditText Password2;
    private TextView Info;
    private Button Login2;
    private EndomondoSession endoAcc;
    private String email;
    private String password;
    private boolean isloggedin=false;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endo_login_screen);
        Email2 = (EditText)findViewById(R.id.etEmail);
        Password2 = (EditText)findViewById(R.id.etPass);
        Login2 = (Button) findViewById(R.id.loginB);


        Login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =getIntent();
                User u1=(User)i.getSerializableExtra("User");
                email = Email2.getText().toString();
                password = Password2.getText().toString();
                Intent intent =new Intent(EndoLoginScreen.this, Main2Activity.class);
                finish();
                u1.setEndomodoname(email);
                u1.setEndomondopass(password);
                intent.putExtra("User",u1);
                startActivity(intent);
                //validate(Email2.getText().toString(), Password2.getText().toString());
                //new BackgroundTask().execute(Email2.getText().toString(), Password2.getText().toString());
                //eVC.execute(email,password);
                //validate(isloggedin);
            }
        });

    }

    private void validate(Boolean res)  {
        if(res){
            Intent intent =new Intent(EndoLoginScreen.this, Main2Activity.class);
            finish();//addded finish before starting new page to close the current page before moving on so back button doesn't go through unnecessary pages


            intent.putExtra("email",email);
            intent.putExtra("password",password);

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


    @Override
    public void proccessFinished(String output) {

    }

    @Override
    public void proccessFinished(boolean logedin) {
            isloggedin=logedin;
    }
}











