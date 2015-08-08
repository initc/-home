package com.jie.home;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jie.homeutil.LognAndSign;
import com.jie.netHome.LognIn;

public class SignActivity extends Activity {
	private String password = null ;
	private String name = null ;
	private String encodePass = null ;
	private Button bt = null ;
	private EditText nameEdit= null ;
	private EditText passwordEdit= null ;
private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		if(msg.what==0x22){//添加成功
			
			File file = new File(SignActivity.this.getFilesDir(), "user.jie");
			try {
				BufferedWriter write = new BufferedWriter(new FileWriter(file));
				write.write(name+"\r\n");
				write.write(encodePass+"\r\n");
				write.close();
				Toast.makeText(SignActivity.this, "添加用户名成功", 0).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(SignActivity.this, "添加用户名失败", 0).show();
			}
			
			return;
		}
		if(msg.what==0x99){//未实现
			Toast.makeText(SignActivity.this, "请检查你的网络连接", 0).show();
			return;
		}
		if(msg.what==0x88){//用户名重复
			Toast.makeText(SignActivity.this, "此用户名已被注册", 0).show();
			return;
		}
		if(msg.what==0x33){//服务器错误
			Toast.makeText(SignActivity.this, "服务器繁忙", 0).show();
			return;
		}
		if(msg.what==0x11){//网络故障
			Toast.makeText(SignActivity.this, "请检查你的网络连接", 0).show();
			return;
		}
		
		
	}
	
};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sign);
		nameEdit=(EditText) findViewById(R.id.name);
		passwordEdit=(EditText) findViewById(R.id.password);
		
	}
	public void Sign_OnClick(View e ){
		
		name=nameEdit.getText().toString().trim();
		password=passwordEdit.getText().toString().trim();
		if (name.equals("")||password.equals("")){
			Toast.makeText(SignActivity.this, "用户名或密码不可为空", 0).show();
			return ;
		}
		if(name.length()>20){
			Toast.makeText(SignActivity.this, "用户名必须在20个字符以内", 0).show();
			return ;
		}
		encodePass = LognAndSign.string2MD5(password);
		new Thread(new LognIn( name, encodePass,handler)).start();
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
