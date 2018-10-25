package com.example.anthony.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper helper = new DatabaseHelper(this);
    private Button Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Register = (Button)findViewById(R.id.RFbutt);

        //button action
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.RFbutt){
                    //making the variables
                    EditText uname = (EditText)findViewById(R.id.et_RF_UserName);
                    EditText name = (EditText)findViewById(R.id.et_RF_Name);
                    EditText pass = (EditText)findViewById(R.id.et_RF_Pass);
                    EditText cpass = (EditText)findViewById(R.id.et_RF_CPass);
                    //I guess the text in the text box isn't a string???
                    String namestr = name.getText().toString();
                    String unamestr = uname.getText().toString();
                    String passstr = pass.getText().toString();
                    String cpassstr = cpass.getText().toString();
                    //checking if passwords match
                    if(!passstr.equals(cpassstr)){
                        Toast errpass = Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT);
                        errpass.show();
                    }else{
                        //insert details in db
                        User u = new User();
                        u.setName(namestr);
                        u.setUname(unamestr);
                        u.setPass(cpassstr);
                        //inserting new user into db
                        helper.insertUser(u);

                        Intent intent =new Intent(RegisterActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        });

    }




}
