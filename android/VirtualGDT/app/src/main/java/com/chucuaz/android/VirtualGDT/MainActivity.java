package com.chucuaz.android.virtualgdt;

import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    static boolean showIpFrame=false;
    static String serverIP = "000.000.000.000";
    private int oldValue;

    Button btnConectar;
    Button btnAyuda;
    Button btnDibujar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debug.DBG("MainActity", "-- onCreate() ---");

        statusSemaforo();
        frameIP();

        //to validate the text box ip value
        validateTxt((EditText) findViewById(R.id.txtIP1));
        validateTxt((EditText) findViewById(R.id.txtIP2));
        validateTxt((EditText) findViewById(R.id.txtIP3));
        validateTxt((EditText) findViewById(R.id.txtIP4));

        //this is the button is btnConectar
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           debug.DBG("MainActity", "-- this is the button is btnConectar ---");
           if (!showIpFrame) {
               autoConnect();
           } else {
               manualConnect();
           }

           SystemClock.sleep(200);
           statusSemaforo();
           }
        });

        //this is the button is btnAyuda
        btnAyuda = (Button) findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            debug.DBG("MainActity", "-- this is the button is btnAyuda ---");
            statusSemaforo();
            }
        });

        btnDibujar = (Button) findViewById(R.id.btnDibujar);
        btnDibujar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), DrawActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }

        );


    }

    private void manualConnect() {
        Globals.getInstance().setSERVER_IP(removeZerosIP());
        //Globals.getInstance().setSERVER_IP(serverIP);
        new Thread(new myThreadPool()).start();
    }

    //To check if we can autoConnect with the server.
    private void autoConnect() {
        debug.DBG("MainActity", "--- Starting autoConnect() ---");

        if (Globals.getInstance().getPingStatus() == false) {
            Globals.getInstance().getMynet().initSocket();
        }

        showIpFrame = !Globals.getInstance().getAutoConnectStatus();
        frameIP();
    }

    //To change the Semaforo's colors
    private void statusSemaforo() {

        ImageView imgSemaforo;
        imgSemaforo = (ImageView) findViewById(R.id.imageView);

        if (Globals.getInstance().getConnectionStatus()) {
            imgSemaforo.setImageResource(R.drawable.semaforo_verde); //green
        } else {
            imgSemaforo.setImageResource(R.drawable.semaforo_rojo); //red
        }

        debug.DBG("MainActity", "--- Globals.getInstance().getConnectionStatus() " + Globals.getInstance().getConnectionStatus() + " ---");
    }

    //To show or hide the frame IP layout
    private void frameIP() {
        final FrameLayout layout = (FrameLayout)findViewById((R.id.frameIP));

        if (showIpFrame) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }

    }

    //To validate max value of the text.
    private void validateTxt(final EditText txtIp) {
        txtIp.addTextChangedListener(new IPFilterMinMax(txtIp) {
            @Override
            public void validate(EditText ev, String text) {
                if (!txtIp.getText().toString().equals("")) {
                    int newValue = Integer.parseInt(txtIp.getText().toString());

                    if (newValue < 256 ) {
                        oldValue = newValue;
                    } else {
                        txtIp.setText( Integer.toString(oldValue));
                        txtIp.setSelection(2);
                    }
                    updateIP(txtIp.getText().toString(), txtIp.getId());
                }
            }
        });
    }

    private void updateIP (String value, int index) {
        value = set3LenghtString(value);

        switch (index) {
            case R.id.txtIP1: serverIP = value + serverIP.substring(3); break;
            case R.id.txtIP2: serverIP = serverIP.substring(0,4) + value + serverIP.substring(7); break;
            case R.id.txtIP3: serverIP = serverIP.substring(0,8) + value + serverIP.substring(11); break;
            case R.id.txtIP4: serverIP = serverIP.substring(0,12) + value; break;
        }
    }

    //ok always set ip with 3 of lenght like "000" format
    private String set3LenghtString ( String value) {
        switch (value.length()) {
            case 0: value = "000"; break;
            case 1: value = "00" + value; break;
            case 2: value = "0" + value; break;
        }
         return value;
    }

    //damm need this to connect with the server this is funny

    private String removeZerosIP() {
        String svalue = "";

        String [] splits = serverIP.split("\\.");
        for (int i=0; i < splits.length; i++) {
            svalue = svalue + Integer.parseInt(splits[i]) + ".";
        }
        return svalue.substring(0, svalue.length() -1);
    }

}
