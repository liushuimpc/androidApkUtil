package com.example.charginganimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	private CustomClipLoading ccl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ccl = (CustomClipLoading) findViewById(R.id.customClipLoading);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_startAnimation:
			ccl.start();
			break;
			
		case R.id.btn_stopAnimation:
			ccl.stop();
			break;
		default:
			break;
		}
	}

}
