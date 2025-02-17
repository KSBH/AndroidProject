package com.example.karim.autochecketmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

public class SignUp extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void onSignUp(View v){

        EditText username = (EditText)findViewById(R.id.et_signup_username);
        EditText password = (EditText)findViewById(R.id.et_signup_passwd);
        EditText fristname = (EditText)findViewById(R.id.et_signup_fristname);
        EditText name = (EditText)findViewById(R.id.et_signup_name);

        //Create a random UUID to use as id for the user
        UUID uid = UUID.randomUUID();

        //Get the text from the editexts
        String usernamestr = username.getText().toString();
        String passwordstr = password.getText().toString();
        String fristnamestr = fristname.getText().toString();
        String namestr = name.getText().toString();
        String id = uid.toString();

        //Using the strings above ,setting the values for database
        Users u = new Users();
        u.setUsername(usernamestr);
        u.setPassword(passwordstr);
        u.setFristname(fristnamestr);
        u.setName(namestr);
        u.setId(id);
        u.setIsactive();

        helper.insertUser(u);
    }
    public void onToLogIn(View v){

        Intent i = new Intent(SignUp.this,LoginActivity.class);
        startActivity(i);
        finish();

    }
}
