package com.jie.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class BindUser extends Activity {
  private EditText user = null ;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homebind);
		user=(EditText) findViewById(R.id.srcName);
	}
	public void Bind_OnClick(View e ){
		
		String srcName = user.getText().toString().trim();
		if (srcName.equals("")){
			
			Toast.makeText(this, "请填写用户名",0).show();
			return ;
			
		}
		
		
		
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
			
			finish();
			overridePendingTransition(R.anim.stayp, R.anim.welcome_out);
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
