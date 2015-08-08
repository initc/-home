package com.jie.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class OnClickEvent {
  public static  Activity context = null ;
  public static Handler handler ;
	public static void HimLocation_OnClick(View e){
		
		
		Toast.makeText(context, "HimLocation_OnClick",0).show();
		
		
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
