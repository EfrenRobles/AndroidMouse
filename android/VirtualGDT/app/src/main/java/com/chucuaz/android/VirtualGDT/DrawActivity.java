//Virtual Graphics Drawing Tablet
//Virtual GDT
package com.chucuaz.android.VirtualGDT;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

public class DrawActivity extends AppCompatActivity {


	public static DrawActivity mainContext;
	private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    myThreadPool mythr = new myThreadPool();
	private int doClick = 0;
	private static String deviceScreensize = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		debug.DBG("onCreate", "onCreate: ");
		super.onCreate(savedInstanceState);
		mainContext = this;

		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		//initPermission();
		mythr.initSocket();

		deviceScreensize = metrics.widthPixels + "," + metrics.heightPixels + "," + 2 + ",";

	}

	public boolean onTouchEvent(final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			doClick = 1;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mythr.sendData(deviceScreensize);
			doClick = 1;
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

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		debug.DBG("DrawActivity", " --- onRequestPermissionsResult step 1 ---");
		switch (requestCode) {
			case REQUEST_CODE_ASK_PERMISSIONS:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					debug.INFO("DrawActivity", " Permission was granted");
					// Permission Granted
				} else {
					// Permission Denied
					debug.INFO("DrawActivity", " Permission was denied");
					Toast.makeText(DrawActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}


	private void initPermission () {
		debug.DBG("DrawActivity", " --- initPermission step 1 ---");
		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.INTERNET);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			debug.DBG("DrawActivity", " --- initPermission step 2 ---");
			ActivityCompat.requestPermissions(DrawActivity.this, new String[] {Manifest.permission.INTERNET}, REQUEST_CODE_ASK_PERMISSIONS);
			return;
		}

		hasWriteContactsPermission = ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.ACCESS_NETWORK_STATE);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			debug.DBG("DrawActivity", " --- initPermission step 2.1 ---");
			ActivityCompat.requestPermissions(DrawActivity.this, new String[] {Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
			return;
		}

		hasWriteContactsPermission = ContextCompat.checkSelfPermission(DrawActivity.this, Manifest.permission.ACCESS_WIFI_STATE);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			debug.DBG("DrawActivity", " --- initPermission step 2.2 ---");
			ActivityCompat.requestPermissions(DrawActivity.this, new String[] {Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
			return;
		}

		debug.DBG("DrawActivity", " --- initPermission step 3 ---");
		mythr.initSocket();
	}

}
