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

import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.jie.homeutil.IpDstport;

public class PostPoi implements Runnable {
	Context context ;
	GeoPoint  poi ;
	String parseIn = "service";
public PostPoi (Context context,GeoPoint  poi ){
	
	this.poi=poi;
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
			out = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream()));
			in =new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.write(parseIn + "\r\n");
			out.write(srcName + "\r\n");
			int x=poi.getLatitudeE6();
			int y = poi.getLongitudeE6();
			out.write(x+ "\r\n");
			out.write(y + "\r\n");
			out.flush();
		   
		  
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
