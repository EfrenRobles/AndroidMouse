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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

	private static Socket socket;
	private static final int SERVERPORT = 1800;
	private static String SERVER_IP = "192.168.42.39";
	private static final int SERERTIMEOUT = 50;

	private static boolean l_ready = false;
	int doClick = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("onCreate", "onCreate: ");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(!l_ready) {
			new Thread(new ClientThread()).start();
		}
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
			//sendData("1500,300,0");
			new Thread(new ClientThread()).start();

		}

		return super.onOptionsItemSelected(item);
	}

	public boolean onTouchEvent(final MotionEvent event) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			doClick = 1;
			// nativeTouchMoved(x, y, 0);

		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//nativeTouchPressed(x, y, 0);

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//nativeTouchReleased(x, y, 0);
			doClick = 0;
		}

		sendData(x + "," + y + "," + doClick + ",");

		return true;
	}

	public void sendData(String data) {
		if (l_ready) {
			Log.d("sendData", "Send data: " + data);
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
		}
	}

	class ClientThread implements Runnable {
		@Override
		public void run() {
			Log.d("ClientThread", "run");
			l_ready = true;
			InetSocketAddress scktAddrss = new InetSocketAddress(SERVER_IP, SERVERPORT);
			socket = new Socket();

			Log.e("ClientThread", "try to connection to:" + scktAddrss.toString());

			try {
				socket.connect(scktAddrss, SERERTIMEOUT);
			} catch (Throwable e) {
				Log.e("ClientThread", "connection problem:" + e.toString());
				try {
					Log.i("ClientThread", "connection is closed thank you ");
					l_ready = false;
					socket.close();
				} catch (Exception e1) {
					Log.e("ClientThread", "connection problem 2:" + e1.toString());
					// ignore
				}
			}

		}
	}
}
