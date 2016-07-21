package com.chucuaz.android.VirtualGDT;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnConectar;
    Button btnAyuda;
    ImageView imgSemaforo;
    TextView txtIP1;
    TextView txtIP2;
    TextView txtIP3;
    TextView txtIP4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSemaforo = (ImageView) findViewById(R.id.imageView);
        statusSemaforo();


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
               imgSemaforo.setImageResource(R.drawable.semaforo_amarillo);

               Globals.getInstance().getMynet().initSocket();

               statusSemaforo();

           }
        });

        btnAyuda = (Button) findViewById(R.id.btnAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug.DBG("MainActity", "-- this is the button is btnAyuda ---");
                imgSemaforo.setImageResource(R.drawable.semaforo_verde);
            }
        });
    }


    private void statusSemaforo() {
        if (Globals.getInstance().getConnectionStatus()) {
            imgSemaforo.setImageResource(R.drawable.semaforo_verde);
        } else {
            imgSemaforo.setImageResource(R.drawable.semaforo_rojo);
        }
    }



}
