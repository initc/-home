package com.jie.homeutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;

public   class CheckStatus {
  public static  CheckIs checkIsLognAndCreate(Context context,String name ,String encodePass){
	  CheckIs is = CheckIs.NOLOGN;
	  File file = new File(context.getFilesDir(), "user.jie");
		try {
			if(!file.exists()){
				file.createNewFile();
			BufferedWriter write = new BufferedWriter(new FileWriter(file));
			write.write(name+"\r\n");
			write.write(encodePass+"\r\n");
			write.close();
			
			}else {
				
				is=CheckIs.ISLOGN;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	  
	  
	  return  is ;
  }
	public static CheckIs fileIsSave (Context context){
		CheckIs is = CheckIs.NOSAVE;
		
		File file = new File (context.getFilesDir(),"user.jie");
		if (file.exists()){
			
			is= CheckIs.ISSAVE;
		}
		
		return is ;
	}
	
	
}
