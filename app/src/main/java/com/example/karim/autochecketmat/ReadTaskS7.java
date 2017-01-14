package com.example.karim.autochecketmat;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.atomic.AtomicBoolean;


import SimaticS7.S7;
import SimaticS7.S7Client;
import SimaticS7.S7OrderCode;

public class ReadTaskS7 {
    private static final int MESSAGE_PRE_EXECUTE = 1;
    private static final int MESSAGE_PROGRESS_UPDATE = 2;
    private static final int MESSAGE_POST_EXECUTE = 3;
    private static final int MESSAGE_POST_EXECUTEMAJ = 4;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private ProgressBar pb_main_progressionS7;
    private Button bt_rw_connexion;
    private View vi_main_ui;
    private TextView tv_rw_plc;
    private TextView tv_rw_nbrbot;
    private TextView tv_rw_se_onoff;
    private TextView tv_rw_ar_onoff;
    private TextView tv_rw_rcomp5;
    private TextView tv_rw_rcomp10;
    private TextView tv_rw_rcomp15;

    private TextView tv_rw_van1;
    private TextView tv_rw_van2;
    private TextView tv_rw_van3;
    private TextView tv_rw_van4;
    private TextView tv_rw_van5;

    private TextView tv_read;


    private AutomateS7 plcS7;
    private Thread readThread;

    private S7Client comS7;
    private String[] param = new String[10];
    private byte[] datasPLC = new byte[512];
    protected String tvtest;
    protected String tvseonoff;
    protected String tvaronoff;
    protected String tvrcomp5;
    protected String tvrcomp10;
    protected String tvrcomp15;
    protected String tvv1;
    protected String tvv2;
    protected String tvv3;
    protected String tvv4;
    protected String tvv5;




    public ReadTaskS7(View v, Button b,TextView t,TextView tv,TextView tv2,TextView tv3,TextView tv4,TextView tv5,TextView tv6,
                      TextView tv7,TextView tv8,TextView tv9,TextView tv10,TextView tv11) {
        vi_main_ui = v;
        bt_rw_connexion = b;
        tv_rw_plc = t;
        tv_rw_nbrbot = tv;
        tv_rw_se_onoff = tv2;
        tv_rw_ar_onoff = tv3;
        tv_rw_rcomp5 = tv4;
        tv_rw_rcomp10 = tv5;
        tv_rw_rcomp15 = tv6;
        tv_rw_van1 = tv7;
        tv_rw_van2 = tv8;
        tv_rw_van3 = tv9;
        tv_rw_van4 = tv10;
        tv_rw_van5 = tv11;



        comS7 = new S7Client();
        plcS7 = new AutomateS7();

        readThread = new Thread(plcS7);
    }


    public void Stop() {
        isRunning.set(false);
        comS7.Disconnect();
        readThread.interrupt();
    }

    public void Start(String a, String r, String s) {
        if (!readThread.isAlive()) {
            param[0] = a;
            param[1] = r;
            param[2] = s;

            readThread.start();
            isRunning.set(true);
        }
    }

    private void downloadOnPreExecute (int t){
        Toast.makeText(vi_main_ui.getContext(), "Le traitement de la tâche de fond est démarré !" + "\n", Toast.LENGTH_SHORT).show();
        tv_rw_plc.setText("PLC : " + String.valueOf(t));
    }

    private void downloadOnProgressUpdate (int progress){
        //pb_main_progressionS7.setProgress(progress);
    }

    private void downloadOnPostExecute(){
        Toast.makeText(vi_main_ui.getContext(), "Le traitement de la tâche de fond est terminé !", Toast.LENGTH_LONG).show();
        //pb_main_progressionS7.setProgress(0);
        tv_rw_plc.setText("PLC: /!\\");
    }
    private void downloadOnPostExecuteMaj(){

        tv_rw_nbrbot.setText(tvtest);
        tv_rw_ar_onoff.setText(tvaronoff);
        tv_rw_se_onoff.setText(tvseonoff);
        tv_rw_rcomp5.setText(tvrcomp5);
        tv_rw_rcomp10.setText(tvrcomp10);
        tv_rw_rcomp15.setText(tvrcomp15);

        tv_rw_van1.setText(tvv1);
        tv_rw_van2.setText(tvv2);
        tv_rw_van3.setText(tvv3);
        tv_rw_van4.setText(tvv4);
        tv_rw_van5.setText(tvv5);

    }

    private Handler monHandler = new Handler(){
        @Override
        public void handleMessage (Message msg){
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_PRE_EXECUTE:
                    downloadOnPreExecute(msg.arg1);
                    break;
                case MESSAGE_PROGRESS_UPDATE:
                    downloadOnProgressUpdate(msg.arg1);
                    break;
                case MESSAGE_POST_EXECUTE:
                    downloadOnPostExecute();
                    break;
                case MESSAGE_POST_EXECUTEMAJ:
                    downloadOnPostExecuteMaj();
                default:
                    break;
            }
        }
    };

    private class AutomateS7 implements Runnable{


        @Override
        public void run(){
            try {
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res = comS7.ConnectTo(param[0], Integer.valueOf(param[1]), Integer.valueOf(param[2]));

                S7OrderCode OrderCode = new S7OrderCode();
                Integer result = comS7.GetOrderCode(OrderCode);
                int numCPU = -1;
                if (res.equals(0) && result.equals(0)) {
                    numCPU = Integer.valueOf(OrderCode.Code().toString().substring(5, 8));
                } else numCPU = 0000;

                sendPreExecuteMessage(numCPU);

                while (isRunning.get()) {
                    if (res.equals(0)) {
                        int retInfo = comS7.ReadArea(S7.S7AreaDB, 5, 0, 18, datasPLC);
                        int data = 0;
                        int dataB = 0;
                        if (retInfo == 0) {
                            data = S7.GetDIntAt(datasPLC, 0);

                            //sendProgressMessage(data);
                        }
                        //Log.i("Variable A.P.I. -> ", String.valueOf(data));
                        Log.i("Nombre de bouteilles", S7.GetWordAt(datasPLC, 16) + "");
                        //S7.SetBitAt(datasPLC, 0, 0, true);
                        //Log.i("I8", S7.GetBitAt(datasPLC, 0, 0) + "");
                        //Log.i("I8", S7.GetBitAt(datasPLC, 0, 1) + "");
                        //Log.i("SE Vanne 4", S7.GetBitAt(datasPLC, 0, 4) + "");
                        //Log.i("position 15 =>",  Integer.toString(data));

                        //Log.i("I8 =>",  datasPLC[0] + "");

                        tvtest=(Integer.toString(S7.GetWordAt(datasPLC, 16)));
                        tvseonoff = Boolean.toString(S7.GetBitAt(datasPLC,0,0));
                        tvaronoff = Boolean.toString(S7.GetBitAt(datasPLC,1,1));
                        tvrcomp5 = Boolean.toString(S7.GetBitAt(datasPLC,4,3));
                        tvrcomp10 = Boolean.toString(S7.GetBitAt(datasPLC,4,4));
                        tvrcomp15 = Boolean.toString(S7.GetBitAt(datasPLC,4,5));

                        tvv1 = Boolean.toString(S7.GetBitAt(datasPLC,0,1));
                        tvv2 = Boolean.toString(S7.GetBitAt(datasPLC,0,2));
                        tvv3 = Boolean.toString(S7.GetBitAt(datasPLC,0,3));
                        tvv4 = Boolean.toString(S7.GetBitAt(datasPLC,0,4));
                        tvv5 = Boolean.toString(S7.GetBitAt(datasPLC,0,5));


                    }
                    sendProgressMessageMaj();

                }
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                sendPostExecuteMessage();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        private void sendPostExecuteMessage(){
            Message postExecuteMsg = new Message();
            postExecuteMsg.what = MESSAGE_POST_EXECUTE;
            monHandler.sendMessage(postExecuteMsg);
        }

        private void sendPreExecuteMessage(int v) {
            Message preExecuteMsg = new Message();
            preExecuteMsg.what = MESSAGE_PRE_EXECUTE;
            preExecuteMsg.arg1 = v;
            monHandler.sendMessage(preExecuteMsg);
        }

        private void sendProgressMessage(int i) {
            Message progressMsg = new Message();
            progressMsg.what = MESSAGE_PROGRESS_UPDATE;
            progressMsg.arg1 = i;
            monHandler.sendMessage(progressMsg);
        }
        private void sendProgressMessageMaj(){

            Message progressMaj = new Message();
            progressMaj.what = MESSAGE_POST_EXECUTEMAJ;
            monHandler.sendMessage(progressMaj);

        }
    }
}
