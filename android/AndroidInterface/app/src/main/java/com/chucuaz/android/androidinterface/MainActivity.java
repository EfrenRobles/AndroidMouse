package com.chucuaz.android.androidinterface;

import android.support.v7.app.AppCompatActivity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    myThreadPool mythr = new myThreadPool();
	private int doClick = 0;
	private static String deviceScreensize = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("onCreate", "onCreate: ");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mythr.initSocket();

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		deviceScreensize = metrics.heightPixels + "," + metrics.widthPixels + "," + 2 + ",";

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
			finish();
			System.exit(0);
		}

		return super.onOptionsItemSelected(item);
	}

	public boolean onTouchEvent(final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			doClick = 1;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mythr.sendData(deviceScreensize);
			doClick = 2;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			doClick = 0;
		}

		if (mythr.isConnected()) {
			mythr.sendData(((int) event.getX()) + "," + (int) event.getY() + "," + doClick + ",");
		} else {
			initApp();
		}

		return true;
	}

    private void initApp() {
		new Thread(new myThreadPool()).start();
    }
}
