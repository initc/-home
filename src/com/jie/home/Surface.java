package com.jie.home;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class Surface extends Activity {

	
	
	Handler  myHander = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==0x88){
			Intent intent= new Intent(Surface.this,MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.wlecome_in, R.anim.stayp);
			finish();
			}
		}
	};
	//Boolean is = false ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.surface);
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
					myHander.sendEmptyMessage(0x88);
			}
		}, 1500);
		
	}
	
}
