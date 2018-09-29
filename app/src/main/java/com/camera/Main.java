package com.camera;

import android.app.Activity;
import android.os.Bundle;

import com.sun.hotelproject.R;

public class Main extends Activity {
	
	CameraPreview cp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		cp = (CameraPreview) findViewById(R.id.cp);
	}
	
}
