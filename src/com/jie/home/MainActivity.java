package com.jie.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends Activity {
	
	
	public static LocationClient loc ;
	private static Boolean isExit = false;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x11) {
				isExit = false;
			}else if (msg.what==0x99){
				
				Toast.makeText(MainActivity.this, "请检查你的网络设置", 0).show();
			}

		};

	};
	LocationClient mLocClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		OnClickEvent.context=this;
		OnClickEvent.handler=handler;
		
		Intent intent = new Intent(this,BackService.class);
	   
		startService(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			exit();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void HimLocation_OnClick(View e) {
        OnClickEvent.HimLocation_OnClick(e);
		
	}

	public void MyLocation_OnClick(View e) {

		OnClickEvent.MyLocation_OnClick(e);
	}

	public void SignInOrOLognIn_OnClick(View e) {

		OnClickEvent.SignInOrLognIn_OnClick(e);
		
	}

	public void About_OnClick(View e) {
		
		OnClickEvent.AboutOnclick(e);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessageDelayed(0x11, 1000);
		} else {
          moveTaskToBack(false);
			//finish();
			//System.exit(0);

		}
	}
}
