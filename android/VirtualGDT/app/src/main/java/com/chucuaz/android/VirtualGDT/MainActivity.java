package com.chucuaz.android.VirtualGDT;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnConectar;
    Button btnAyuda;
    TextView txtIP1;
    TextView txtIP2;
    TextView txtIP3;
    TextView txtIP4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debug.DBG("MainActity", "-- onCreate() ---");

        statusSemaforo(false);
        frameIP(true);


        ///-----------------------
        txtIP1 = (TextView) findViewById(R.id.txtIP1);
        txtIP2 = (TextView) findViewById(R.id.txtIP2);
        txtIP3 = (TextView) findViewById(R.id.txtIP3);
        txtIP4 = (TextView) findViewById(R.id.txtIP4);
        //-------------------------

        //Initialization button
        btnConectar = (Button) findViewById(R.id.btnConectar);

        btnConectar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               debug.DBG("MainActity", "-- this is the button is btnConectar ---");
               statusSemaforo(true);

               if (Globals.getInstance().getPingStatus() == false) {
                   Globals.getInstance().getMynet().initSocket();
               }

               statusSemaforo(false);
               frameIP(Globals.getInstance().getAutoConnectStatus());

           }
        });

        btnAyuda = (Button) findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug.DBG("MainActity", "-- this is the button is btnAyuda ---");
                statusSemaforo(true);
            }
        });
    }


    private void statusSemaforo(boolean isYellow) {

        ImageView imgSemaforo;
        imgSemaforo = (ImageView) findViewById(R.id.imageView);

        if (isYellow) {
            imgSemaforo.setImageResource(R.drawable.semaforo_amarillo);
        } else if (Globals.getInstance().getConnectionStatus()) {
            imgSemaforo.setImageResource(R.drawable.semaforo_verde);
        } else {
            imgSemaforo.setImageResource(R.drawable.semaforo_rojo);
        }
    }

    private void frameIP(boolean showit) {
        FrameLayout layout = (FrameLayout)findViewById((R.id.frameIP));

        if (!showit) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }

    }


}
