package com.jie.netHome;

import com.jie.homeutil.BindSocket;

public class BindNet implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		BindSocket bind =new  BindSocket();
		bind.Bind("", "");
		
		
		
		
		
	}

	
	
}
