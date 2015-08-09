package com.jie.netHome;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.jie.homeutil.BindSocket;
import com.jie.homeutil.LognAndSign.ReturnMessage;

public class BindNet implements Runnable {

	Context context;
	String dstName;
	String srcName;
	Handler handler;
	public BindNet(Context context, String name,Handler handler) {
		this.context = context;
		this.dstName = name;
		this.handler=handler;
	}

	@Override
	public void run() {
		
		BindSocket bind = new BindSocket();
		File file = new File(context.getFilesDir(), "user.jie");
		if (!file.exists()) {
			Toast.makeText(context, "用户信息出错    user.jie不存在", 0).show();
			return;
		}
		try {
			
			BufferedReader read = new BufferedReader(new FileReader(file));
			srcName = read.readLine();
			if(srcName==dstName){
				Toast.makeText(context,"不能和自己绑定" , Toast.LENGTH_SHORT).show();
				return ;
			}
			ReturnMessage result=bind.Bind(srcName, dstName);
			switch(result){
			case InternetError:  handler.sendEmptyMessage(0x11);break ;
			case ParseError:handler.sendEmptyMessage(0x99);break;
			case ServerError:handler.sendEmptyMessage(0x33);break ;
			case Success :handler.sendEmptyMessage(0x22); break ;
			case UserNameRepeat :handler.sendEmptyMessage(0x88);break ;
			
			}
		}

		catch (FileNotFoundException e) {
			
			Toast.makeText(context, "綁定失敗", 0).show();
			e.printStackTrace();
			
		} catch (IOException e) {
			Toast.makeText(context, "綁定失敗", 0).show();
			e.printStackTrace();
		}

	}

}
