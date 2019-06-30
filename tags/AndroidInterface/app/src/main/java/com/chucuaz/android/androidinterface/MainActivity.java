package com.chucuaz.android.androidinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Socket socket;
    private static final int SERVERPORT = 1800;
    private static String SERVER_IP = "192.168.42.39";

	private boolean l_ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new ClientThread()).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //return true;
            sendData("test");
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onTouchEvent(final MotionEvent event)     {
            final int x = (int) event.getX();
            final int y = (int) event.getY();


            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                sendData(x + "," + y + ";" );
               // nativeTouchMoved(x, y, 0);

            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //nativeTouchPressed(x, y, 0);

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //nativeTouchReleased(x, y, 0);
            }
        return true;
    }

    public void sendData(String data) {
		//Log.d("sendData", "Send data: " + data);
		if (l_ready) {
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				out.println(data);
			} catch (UnknownHostException e) {
				Log.e("sendData", "sendData fail 1: " + e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("sendData", "sendData fail 1: " + e.toString());
				e.printStackTrace();
			} catch (Exception e) {
				Log.e("sendData", "sendData fail 1: " + e.toString());
				e.printStackTrace();
			}
		//} else {
			//Log.i("sendData", "Error on init socket");
		}
    }


    class ClientThread implements  Runnable {
        @Override
        public void run() {
            Log.d("ClientThread", "run");
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
				l_ready = true;
            } catch (UnknownHostException e1) {
                Log.e("ClientThread", "ClientThread fail 1: " + e1.toString());
                e1.printStackTrace();
				l_ready=false;
            } catch (IOException e1) {
                Log.e("ClientThread", "ClientThread fail 2: " + e1.toString());
                e1.printStackTrace();
				l_ready=false;
            }
        }
    }


}
