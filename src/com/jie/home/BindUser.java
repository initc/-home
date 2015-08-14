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
import android.widget.EditText;
import android.widget.Toast;

import com.jie.homeutil.CheckIs;
import com.jie.homeutil.CheckStatus;
import com.jie.netHome.BindNet;

public class BindUser extends Activity {
  private EditText user = null ;
  private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		if (msg.what == 0x22) {
			// 添加成功
			// 添加成功后 在本地建立一个用户数据 来判断此用户是否已经登录了
			// 之后的登录需要进行判断 该用户是否可以进行登录
			
			Toast.makeText(BindUser.this, "绑定成功", 0).show();
			File bindFile =	new File(BindUser.this.getFilesDir(),"bind.jie");
			if(!bindFile.exists()){
				BufferedWriter w=null;
				try {
					bindFile.createNewFile();
					 w = new BufferedWriter(new FileWriter(bindFile));
					w.write(user.getText().toString());
					w.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					
					try {
						if(w!=null)
						w.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			return;
		}
		if (msg.what == 0x99) {// 未实现
			Toast.makeText(BindUser.this, "對方沒有註冊", 0).show();
			return;
		}
		if (msg.what == 0x88) {// 用户名重复
			Toast.makeText(BindUser.this, "你已經和對方綁定", 0).show();
			return;
		}
		if (msg.what == 0x33) {// 服务器错误
			Toast.makeText(BindUser.this, "服务器繁忙", 0).show();
			return;
		}
		if (msg.what == 0x11) {// 网络故障
			Toast.makeText(BindUser.this, "请检查你的网络连接", 0).show();
			return;
		}
		
	}
	  
	  
	  
  };
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
		CheckIs is = CheckStatus.fileIsSave(this);
		if (is==CheckIs.NOSAVE){
			Toast.makeText(this, "你还没有账户 请注册账户再试",0).show();
			return ;
		}
		File bindFile =	new File(BindUser.this.getFilesDir(),"bind.jie");
		if(bindFile.exists()){
			Toast.makeText(this, "你已经绑定了",0).show();
			return ;
		}
		new Thread(new BindNet(this,srcName,handler)).start();
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
