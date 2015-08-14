package com.jie.home;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class OnClickEvent {
  public static  Activity context = null ;
  public static Handler handler ;
	public static void HimLocation_OnClick(View e){
		
		
		//Toast.makeText(context, "HimLocation_OnClick",0).show();
		File bindFile = new  File(context.getFilesDir(),"bind.jie");
		if(!bindFile.exists()){
			Toast.makeText(context, "你还没有绑定  请绑定后再试", Toast.LENGTH_SHORT).show();
			return ;
		}
		Intent intent = new Intent(context,OtherLocation.class);
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.wlecome_in, R.anim.stayp);
		
	}
	
	public static void MyLocation_OnClick(View e){
		//Toast.makeText(context, "MyLocation_OnClick(",0).show();
		Intent intent = new Intent(context,LocationView.class);
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.wlecome_in, R.anim.stayp);
	}
	public static void SignInOrLognIn_OnClick(View e){
		//Toast.makeText(context, "SignInOrLognIn_OnClick",0).show();
	  // new Thread(new LognIn( "jie", "123456",handler)).start();
		
		Intent intent = new Intent(context,SignActivity.class);
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.wlecome_in, R.anim.stayp);
	}
	public static void AboutOnclick(View e){
		
		//Toast.makeText(context, "AboutOnclick",0).show();
		Intent intent = new Intent(context,BindUser.class);
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.wlecome_in, R.anim.stayp);
		
		
	}
	
}
