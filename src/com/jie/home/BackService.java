package com.jie.home;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.jie.netHome.PostPoi;

public class BackService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
LocationClient mLocClient ;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		super.onCreate();
		mLocClient = new LocationClient(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setScanSpan(5000);// 每5秒钟
		option.setAddrType("all");
		mLocClient.setLocOption(option);
		mLocClient.registerLocationListener(new MyLocationListenner());
		mLocClient.start();
		
		
	}

	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	GeoPoint myPoi ;
	public class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			myPoi = new GeoPoint((int) (location.getLatitude() * 1e6),
					(int) (location.getLongitude() * 1e6));
		    File file = new File (getApplicationContext().getFilesDir(),"user.jie");
		    if(file.exists())
			new Thread(new PostPoi(getApplicationContext(),myPoi)).start();

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}
}
