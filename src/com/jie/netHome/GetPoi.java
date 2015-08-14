package com.jie.netHome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.jie.homeutil.IpDstport;

public class GetPoi implements Runnable {
         Handler handler ;
         Context context ;
         String parseIn = "GetPoi";
	public GetPoi(Handler handler,Context  context ){
		
		this.handler= handler ;
		this.context=context ;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		BufferedWriter out = null;
		BufferedReader in = null ;
		Socket client = null;
		try {
			
			client = new Socket();
			client.connect(new InetSocketAddress(IpDstport.ip, IpDstport.dstPort), 2000);
            File nameFile = new File(context.getFilesDir(),"user.jie");
            BufferedReader read = new BufferedReader(new FileReader(nameFile));
            String srcName = read.readLine();
            read.close();
            File bindFile = new File(context.getFilesDir(),"bind.jie");
            BufferedReader readB = new BufferedReader(new FileReader(bindFile));
            String bindName = readB.readLine();
            read.close();
			out = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream()));
			in =new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.write(parseIn + "\r\n");
			out.write(srcName + "\r\n");
			out.write(bindName + "\r\n");
			out.flush();
		   int re =	Integer.parseInt(in.readLine());
		   String time=null;
		   int x=0;
		   int y=0;
		   if(re==1){
            time =	in.readLine();
           x=Integer.parseInt(in.readLine());
           y=Integer.parseInt(in.readLine());
           
		   }else {
			   
			   handler.sendEmptyMessage(0x22);
			   return ;
		   }
		   if (x!=-1&&y!=-1){
        	 
			   //success
			   GeoPoint poi = new GeoPoint (x,y);
			   Message mes = new Message();
			   mes.obj=poi;
			  Bundle bu =mes.getData();
			  bu.putString("time", time);
			   mes.what=0x1;
			   handler.sendMessage(mes);
          }else {
        	  handler.sendEmptyMessage(0x22);
        	  return ;
          }
		} catch (UnknownHostException e) {
			 handler.sendEmptyMessage(0x22);
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 handler.sendEmptyMessage(0x22);
		}

		finally {
			
				try {
					if (out != null)
					out.close();
					if(in!=null)
						in.close();
					if (client.isConnected())
						client.close();
                   
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		
	}

}
