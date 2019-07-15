package com.chucuaz.android.virtualgdt;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;

import com.chucuaz.android.virtualgdt.engine.Engine;

import android.content.SharedPreferences;
import android.content.Context;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String Name = "serverIP";
    private static final Engine engine = new Engine();

    private SharedPreferences sharedpreferences;

    private int oldValue;

    Button btnConectar;
    Button btnAyuda;
    Button btnDibujar;
    Button btnAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Engine.DBG("MainActity", "-- onCreate() ---");

        setIp();
        statusSemaforo();

        //to validate the text box ip value
        validateTxt((EditText) findViewById(R.id.txtIP1));
        validateTxt((EditText) findViewById(R.id.txtIP2));
        validateTxt((EditText) findViewById(R.id.txtIP3));
        validateTxt((EditText) findViewById(R.id.txtIP4));

        //this is the button is btnAds
        btnAds = findViewById(R.id.btnAds);
        btnAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Engine.DBG("MainActity", "-- this is the button btnAds ---");

            }
        });

        //this is the button is btnConectar
        btnConectar = findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               Engine.DBG("MainActity", "-- this is the button btnConectar ---");

               engine.connect(getIp());

               SystemClock.sleep(200);
               statusSemaforo();
               }
        });

        btnDibujar = findViewById(R.id.btnDibujar);
        btnDibujar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DrawActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //this is the button is btnAyuda
        btnAyuda = findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Engine.DBG("MainActity", "-- this is the button btnAyuda ---");
                finishAffinity();
                System.exit(0);
            }
        });
    }
    // ----------------------------------------------------------------------------------------------------------------

    //To change the Semaforo's colors
    private void statusSemaforo() {

        ImageView imgSemaforo;
        imgSemaforo = findViewById(R.id.imageView);

        if (engine.isConnected()) {
            imgSemaforo.setImageResource(R.drawable.semaforo_verde); //green
        } else {
            imgSemaforo.setImageResource(R.drawable.semaforo_rojo); //red
        }
    }

    //To validate max value of the text.
    private void validateTxt(final EditText txtIp) {
        txtIp.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtIp.getText().toString().equals("")) {
                    int newValue = Integer.parseInt(txtIp.getText().toString());

                    if (newValue < 256 ) {
                        oldValue = newValue;
                    } else {
                        txtIp.setText(Integer.toString(oldValue));
                        txtIp.setSelection(2);
                    }
                }
            }
        });
    }

    private void setIp() {
        String [] splits;
        String ip = "127.0.0.1";

        sharedpreferences = getSharedPreferences(TAG, Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Name)) {
            ip = sharedpreferences.getString(Name, "");
        }

        splits = ip.split("\\.");

        EditText txtIP1 = findViewById(R.id.txtIP1);
        EditText txtIP2 = findViewById(R.id.txtIP2);
        EditText txtIP3 = findViewById(R.id.txtIP3);
        EditText txtIP4 = findViewById(R.id.txtIP4);

        txtIP1.setText(splits[0]);
        txtIP2.setText(splits[1]);
        txtIP3.setText(splits[2]);
        txtIP4.setText(splits[3]);
    }

    private String getIp() {
        EditText txtIP1 = findViewById(R.id.txtIP1);
        EditText txtIP2 = findViewById(R.id.txtIP2);
        EditText txtIP3 = findViewById(R.id.txtIP3);
        EditText txtIP4 = findViewById(R.id.txtIP4);

        String
        serverIP  = txtIP1.getText() + ".";
        serverIP  += txtIP2.getText() + ".";
        serverIP  += txtIP3.getText() + ".";
        serverIP  += txtIP4.getText();

        sharedpreferences = getSharedPreferences(TAG, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, serverIP);
        editor.commit();

        return serverIP;
    }
}
