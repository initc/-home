package com.jie.netHome;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.jie.homeutil.LognAndSign;
import com.jie.homeutil.LognAndSign.ReturnMessage;

public class LognIn implements Runnable {
	private Context context;
	private String name;
	private String password;
	private Handler handler;

	public LognIn(String name, String password, Handler handler) {
		this.name = name;
		this.password = password;
		this.handler = handler;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		LognAndSign logn = new LognAndSign();
		ReturnMessage result = logn.Logn(name, password);//得到登陸的結果
		/*if (result != ReturnMessage.Success) {//如果不成功 
			if (result == ReturnMessage.UserNameRepeat) {//用戶名重複  
				handler.sendEmptyMessage(0x88);
			} else {
				handler.sendEmptyMessage(0x99);
			}
		} else {

			handler.sendEmptyMessage(0x22);
		}*/
		switch(result){
		case InternetError:  handler.sendEmptyMessage(0x11);break ;
		case ParseError:handler.sendEmptyMessage(0x99);break;
		case ServerError:handler.sendEmptyMessage(0x33);break ;
		case Success :handler.sendEmptyMessage(0x22); break ;
		case UserNameRepeat :handler.sendEmptyMessage(0x88);break ;
		
		}

	}

}
