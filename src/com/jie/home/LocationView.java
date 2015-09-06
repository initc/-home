package com.jie.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class LocationView extends Activity {
	private MyLocationOverlay myLocationOverlay = null;
	private MapView myView = null;
	private MapController control = null;
	private LocationClient mLocClient = null;
	private LocationData locData = null;
	private boolean is = true;
	private Button send = null;
 private  boolean io =true ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DemoApplication app = (DemoApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplicationContext());
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(new DemoApplication.MyGeneralListener());
		}
		setContentView(R.layout.location_map);
		
		
		myView = (MapView) findViewById(R.id.map_view);
		control = myView.getController();
		control.setZoom(16);
		control.enableClick(true);
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		send = (Button) findViewById(R.id.sendLocation);
		// 添加监听事件 监听用户按下按钮发�?�位置信�?
		send.setOnClickListener(new SendListenner());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setScanSpan(1000);// 每一秒钟跟新�?�?
		option.setAddrType("all");
		mLocClient.setLocOption(option);
		mLocClient.registerLocationListener(new MyLocationListenner());
		mLocClient.start();
		myLocationOverlay = new MyLocationOverlay(myView);
		myLocationOverlay.setData(locData);
		// 添加定位图层
		myView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
		//绘制我的位置的图
		myLocationOverlay.setMarker(null);
		// 修改定位数据后刷新图层生
		myView.refresh();

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
			io=false ;
			finish();
			overridePendingTransition(R.anim.stayp, R.anim.welcome_out);
		}
		
		return super.onKeyDown(keyCode, event);
	}
	class SendListenner implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		myView.onResume();
		super.onResume();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		myView.onPause();
		super.onPause();
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		myView.destroy();
		super.onDestroy();
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		myView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		myView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			location.getAddrStr();
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			myLocationOverlay.setData(locData);
			if(io)
			myView.refresh();
			if (is) {

				control.animateTo(new GeoPoint((int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));

			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

}
