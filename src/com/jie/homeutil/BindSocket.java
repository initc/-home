package com.jie.homeutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.jie.homeutil.LognAndSign.ReturnMessage;

public class BindSocket {
private String  parseIn="BindIn";

    //绑定服务器上的用户信息   
    // 返回用户是否绑定的信息
	public ReturnMessage Bind(String srcName, String dstName) {
		ReturnMessage result = ReturnMessage.Success;
		BufferedWriter out = null;
		BufferedReader in = null ;
		Socket client = null;
		try {
			
			client = new Socket();
			
			client.connect(new InetSocketAddress(IpDstport.ip, IpDstport.dstPort), 2000);

			out = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream()));
			in =new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.write(parseIn + "\r\n");
			out.write(srcName + "\r\n");
			out.write(dstName + "\r\n");
			out.flush();
		   int up =	Integer.parseInt(in.readLine());//得到server上的返回值  如果值為1則成功  否則是用戶名重複  -1为服务器错误
          if (up==0){
        	  result = ReturnMessage.UserNameRepeat;//用户已经和對方綁定
          }else if (up==-1){
        	  
        	  result = ReturnMessage.ServerError;
          }else if (up == 2){
        	  
        	  result = ReturnMessage.ParseError;
          }
		} catch (UnknownHostException e) {

			e.printStackTrace();
			result = ReturnMessage.InternetError;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = ReturnMessage.InternetError;
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
		return result;
	}
	
	
}
