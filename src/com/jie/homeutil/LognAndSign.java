package com.jie.homeutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;

public class LognAndSign {
/*	private String ip = "192.168.139.1";
	private int dstPort = 8900;*/
	String parseIn = "LognIn";

	public ReturnMessage Logn(String name, String password) {
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
			out.write(name + "\r\n");
			out.write(password + "\r\n");
			out.flush();
		   int up =	Integer.parseInt(in.readLine());//得到server上的返回值  如果值為1則成功  否則是用戶名重複  -1为服务器错误
          if (up==0){
        	  result = ReturnMessage.UserNameRepeat;
          }else if (up==-1){
        	  
        	  result = ReturnMessage.ServerError;
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

	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	public static enum ReturnMessage {
		ParseError, InternetError, ServerError, Success,UserNameRepeat
	}
}
