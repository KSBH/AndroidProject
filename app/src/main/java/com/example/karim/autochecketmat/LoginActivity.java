package com.example.karim.autochecketmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    String admin = "android";

    private CheckBox cb_rw_switcharonoff;
    private CheckBox cb_rw_switchseonoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cb_rw_switcharonoff = (CheckBox)findViewById(R.id.cb_rw_switcharonoff);
        cb_rw_switchseonoff = (CheckBox)findViewById(R.id.cb_rw_switchseonoff);

    }
    public void onSignIn(View v){

        if(v.getId()==R.id.bt_login_login){

            EditText username = (EditText)findViewById(R.id.et_login_username);
            EditText passph = (EditText)findViewById(R.id.et_login_pass);

            String usernamestr = username.getText().toString();
            String passphstr = passph.getText().toString();

           String password = helper.searchPass(usernamestr);

            if(usernamestr.equals(admin)&& passphstr.equals(password)){
                Intent i = new Intent(LoginActivity.this,Admin.class);
                startActivity(i);
                finish();
            }
            else if(passphstr.equals(password)){
                Intent x = new Intent(LoginActivity.this,ReadAndWrite.class);
                startActivity(x);
                finish();
                //cb_rw_switcharonoff.setVisibility(View.INVISIBLE);
                //cb_rw_switchseonoff.setVisibility(View.INVISIBLE);


            }
            else{
                Toast.makeText(LoginActivity.this,"Incorrect Login",Toast.LENGTH_LONG).show();
            }

        }

    }
    public void toSignUp(View v){

        Intent sig = new Intent(LoginActivity.this , SignUp.class);
        startActivity(sig);
        finish();

    }
}
