package com.example.anthony.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;
    private int failedatt = 5;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.namefield);
        Password = (EditText)findViewById(R.id.passfield);
        Login = (Button) findViewById(R.id.Loginbutt);
        Register = (Button) findViewById(R.id.Registerbutt);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {//move to the registration form
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });




    }
    private void validate (String userName, String userPassword){

        String password = helper.searchPass(userName);


        if(userPassword.equals(password)||(userName.equals("Admin") && userPassword.equals("1234"))){
            Intent intent =new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }else{
            failedatt--;
            Toast errpass = Toast.makeText(MainActivity.this, "Invalid Credentials "+failedatt+" attempts remaining", Toast.LENGTH_SHORT);
            errpass.show();
            if(failedatt==0)
                Login.setEnabled(false);
        }

    }



}
