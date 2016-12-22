package com.chucuaz.android.VirtualGDT;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    Button btnConectar;
    Button btnAyuda;
    private int oldValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debug.DBG("MainActity", "-- onCreate() ---");

        statusSemaforo(false);
        frameIP(true);

        //to validate the text box ip value
        validateTxt((EditText) findViewById(R.id.txtIP1));
        validateTxt((EditText) findViewById(R.id.txtIP2));
        validateTxt((EditText) findViewById(R.id.txtIP3));
        validateTxt((EditText) findViewById(R.id.txtIP4));

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

    //To validate max value of the text.
    private void validateTxt(final EditText txtIp) {
        txtIp.addTextChangedListener(new IPFilterMinMax(txtIp) {
            @Override
            public void validate(EditText ev, String text) {
                debug.DBG("MainActity", "-- this is the value of txt :  " + txtIp.getText()+ "  ---");
                if (!txtIp.getText().toString().equals("")) {
                    int newValue = Integer.parseInt(txtIp.getText().toString());
                    if (newValue < 256 ) {
                        oldValue = newValue;
                    } else {
                        txtIp.setText( Integer.toString(oldValue));
                        txtIp.setSelection(2);

                    }
                }
            }
        });
    }
}
