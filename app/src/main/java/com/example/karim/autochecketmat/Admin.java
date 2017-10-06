package com.example.karim.autochecketmat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    private EditText et_admin_add ;
    private EditText et_admin_rack;
    private EditText et_admin_slot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        EditText etad = (EditText)findViewById(R.id.et_admin_add);
        et_admin_add = etad;
        et_admin_rack = (EditText)findViewById(R.id.et_admin_rack);
        et_admin_slot = (EditText)findViewById(R.id.et_admin_slot);
    }
    public void goToSignUp(View v){
        if(v.getId()==R.id.bt_admin_tosignup) {

            Intent signup = new Intent(Admin.this, SignUp.class);
            startActivity(signup);
            finish();
        }

    }
    public void goToReadAndWrite(View v){

        Intent randw = new Intent(Admin.this,ReadAndWrite.class);
        startActivity(randw);
        finish();

    }
    public String getAdress(){
        return et_admin_add.getText().toString();
    }
    public void setAdress(View v){

       /* final EditText etad = (EditText)findViewById(R.id.et_admin_add);
        etad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etad.setText(etad.getText().toString());
            }
        });*/

        if(et_admin_add.getText().toString().equals("192.168.1.6") && et_admin_slot.getText().toString().equals("2") && et_admin_rack.getText().toString().equals("0")){
            Toast.makeText(Admin.this,"nothing changed",Toast.LENGTH_LONG).show();
        }
        else{
            et_admin_add.setText(et_admin_add.getText().toString());
            et_admin_slot.setText(et_admin_slot.getText().toString());
            Toast.makeText(this,et_admin_add.getText().toString(),Toast.LENGTH_LONG).show();
        }

    }
    public void goToListUsers(View v){

        Intent lu = new Intent(Admin.this, ListUsers.class);
        startActivity(lu);
        finish();

   }
}
