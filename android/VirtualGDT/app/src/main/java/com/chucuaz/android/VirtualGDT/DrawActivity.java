//Virtual Graphics Drawing Tablet
//Virtual GDT
package com.chucuaz.android.VirtualGDT;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

public class DrawActivity extends AppCompatActivity {


	public static DrawActivity mainContext;
	private int doClick = 0;
	private static String deviceScreensize = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		debug.DBG("onCreate", "onCreate: ");
		super.onCreate(savedInstanceState);
		mainContext = this;

		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_draw);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		deviceScreensize = metrics.widthPixels + "," + metrics.heightPixels + "," + 2 + ",";

	}

	public boolean onTouchEvent(final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			doClick = 1;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Globals.getInstance().getMythr().sendData(deviceScreensize);
			doClick = 1;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			doClick = 0;
		}

		if (Globals.getInstance().getMythr().isConnected()) {
			Globals.getInstance().getMythr().sendData(((int) event.getX()) + "," + (int) event.getY() + "," + doClick + ",");
		} else {
			initApp();
		}

		return true;
	}

	private void initApp() {
		new Thread(new myThreadPool()).start();
	}


}
