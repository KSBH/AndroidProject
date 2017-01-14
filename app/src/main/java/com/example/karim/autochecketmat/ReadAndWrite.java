package com.example.karim.autochecketmat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReadAndWrite extends AppCompatActivity implements View.OnClickListener{

    private NetworkInfo network;
    private ConnectivityManager connexStatus;

    private Button bt_rw_connexion;
    private TextView tv_rw_plc;

    private ReadTaskS7 r7;


    private EditText et_admin_add;
    private TextView tv_rw_nbrbot;
    private TextView tv_rw_ar_onoff;
    private TextView tv_rw_se_onoff;
    private TextView tv_rw_rcomp5;
    private TextView tv_rw_rcomp10;
    private TextView tv_rw_rcomp15;
    private RelativeLayout lyout_comp;
    private RelativeLayout lyout_reg;
    private Button reg;
    private Button bt_rw_comp;
    private TextView tv_rw_van1;
    private TextView tv_rw_van2;
    private TextView tv_rw_van3;
    private TextView tv_rw_van4;
    private TextView tv_rw_van5;
    private EditText et_admin_rack;
    private EditText et_admin_slot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_and_write);

        LayoutInflater echo = getLayoutInflater();
        View activity_admin = echo.inflate(R.layout.activity_admin,null);


       EditText et_admin_rack = (EditText)activity_admin.findViewById(R.id.et_admin_rack);
       EditText et_admin_slot = (EditText)activity_admin.findViewById(R.id.et_admin_slot);

        connexStatus = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();
        EditText et_admin_add = (EditText)activity_admin.findViewById(R.id.et_admin_add);
        TextView tv_rw_nbrbot = (TextView)findViewById(R.id.tv_rw_nbrbot);
        bt_rw_connexion = (Button) findViewById(R.id.bt_rw_connexion);
        tv_rw_plc = (TextView)findViewById(R.id.tv_rw_plc);
        tv_rw_ar_onoff = (TextView)findViewById(R.id.tv_rw_ar_onoff);
        tv_rw_se_onoff = (TextView)findViewById(R.id.tv_rw_se_onoff);
        tv_rw_rcomp5 = (TextView)findViewById(R.id.tv_rw_rcomp5);
        tv_rw_rcomp10 = (TextView)findViewById(R.id.tv_rw_rcomp10);
        tv_rw_rcomp15 = (TextView)findViewById(R.id.tv_rw_rcomp15);
        lyout_comp = (RelativeLayout)findViewById(R.id.lyout_comp);
        lyout_reg =(RelativeLayout)findViewById(R.id.lyout_reg);
        this.reg = (Button)findViewById(R.id.reg);
        this.reg.setOnClickListener(this);
        this.bt_rw_comp =(Button)findViewById(R.id.bt_rw_comp);
        this.bt_rw_comp.setOnClickListener(this);

        this.tv_rw_nbrbot = tv_rw_nbrbot;
        this.et_admin_add = et_admin_add;
        this.et_admin_slot = et_admin_slot;
        this.et_admin_rack  = et_admin_rack;

        tv_rw_van1 = (TextView)findViewById(R.id.tv_rw_van1);
        tv_rw_van2 = (TextView)findViewById(R.id.tv_rw_van2);
        tv_rw_van3 = (TextView)findViewById(R.id.tv_rw_van3);
        tv_rw_van4 = (TextView)findViewById(R.id.tv_rw_van4);
        tv_rw_van5 = (TextView)findViewById(R.id.tv_rw_vanmanu);

    }

    public void connecting(View v) {

        if (network != null && network.isConnectedOrConnecting()) {
            if (bt_rw_connexion.getText().toString().equals("Connexion S7")) {

                Toast.makeText(this, network.getTypeName(), Toast.LENGTH_SHORT).show();
                bt_rw_connexion.setText("Déconnexion S7");
                r7 = new ReadTaskS7(v, bt_rw_connexion,tv_rw_plc,tv_rw_nbrbot,tv_rw_se_onoff,tv_rw_ar_onoff
                        ,tv_rw_rcomp5,tv_rw_rcomp10,tv_rw_rcomp15,tv_rw_van1,tv_rw_van2,tv_rw_van3,tv_rw_van4,tv_rw_van5);
                r7.Start(et_admin_add.getText().toString(), et_admin_rack.getText().toString(),et_admin_slot.getText().toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            else{
                r7.Stop();
                bt_rw_connexion.setText("Connexion S7");
                Toast.makeText(getApplication(), "Traitement interrompu par l'utilisateur !!! ",
                        Toast.LENGTH_LONG).show();
                //writeS7.Stop();
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == bt_rw_comp){

            lyout_comp.setVisibility(View.VISIBLE);
            lyout_reg.setVisibility(View.INVISIBLE);

        }
        if (v == reg){

            lyout_reg.setVisibility(View.VISIBLE);
            lyout_comp.setVisibility(View.INVISIBLE);

        }
    }
}
