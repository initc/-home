package com.jie.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

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
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.jie.netHome.GetPoi;

public class OtherLocation extends Activity {
	MapView map;
	MKRoute route = null;// 保存路线的点的容器  一个具体的路线 肯定包含多个点组成的节点
	TransitOverlay transitOverlay = null;// 保存公交路线图层数据的变量，供浏览节点时使用
	RouteOverlay routeOverlay = null;//一个多个点组成的位子的路线图层 应该是map的图层子类
	boolean useDefaultIcon = false;
	MKSearch mSearch = null;//进行搜索定位的接口类   两个GeoPoint  可为具体的经纬度或者地域名称
	LocationClient mLocClient;//获取自己本身的位子信息   可以设置多长时间进行刷新
	GeoPoint myPoi; //位子的存储类  是具体的 经纬度
	MyLocationOverlay myLocationOverlay;
	LocationData data ;
	 MapController  controll ;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == 0x1) {
				if (po == 1) {
					enNode = new MKPlanNode();
					enNode.pt = (GeoPoint) msg.obj;
					mSearch.drivingSearch(null, stNode, null, enNode);
					po = -1;
				}
				if (po == 2) {
					enNode = new MKPlanNode();
					enNode.pt = (GeoPoint) msg.obj;
					mSearch.transitSearch(null, stNode, enNode);
					po=-1;
				}
				if (po == 3) {
					enNode = new MKPlanNode();
					enNode.pt = (GeoPoint) msg.obj;
					mSearch.walkingSearch(null, stNode, null, enNode);
					po=-1;
				}
			}

			if (msg.what == 0x22) {

				Toast.makeText(OtherLocation.this, "发生错误请稍后重试",
						Toast.LENGTH_SHORT).show();
			}

		}

	};

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
		setContentView(R.layout.otherlocation);
		map = (MapView) findViewById(R.id.map_view);
		//map.getController().setZoom(15);
		controll=map.getController();
		mSearch = new MKSearch();
		data = new LocationData();
		myLocationOverlay =new MyLocationOverlay(map);//接受百度服务器发过来的位子信息
		mSearch.init(app.mBMapManager, new MKSearchListener() {

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
				if (error != 0 || res == null) {
					Toast.makeText(OtherLocation.this, "抱歉，未找到结果",
							Toast.LENGTH_SHORT).show();
					return;
				}

				routeOverlay = new RouteOverlay(OtherLocation.this, map);
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				map.getOverlays().clear();
				map.getOverlays().add(routeOverlay);
				map.refresh();
				map.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
						routeOverlay.getLonSpanE6());
				map.getController().animateTo(res.getStart().pt);
				route = res.getPlan(0).getRoute(0);

			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
				
				if (error != 0 || res == null) {
					Toast.makeText(OtherLocation.this, "抱歉，未找到结果",
							Toast.LENGTH_SHORT).show();
					return;
				}

				transitOverlay = new TransitOverlay(OtherLocation.this, map);
				transitOverlay.setData(res.getPlan(0));
				map.getOverlays().clear();
				map.getOverlays().add(transitOverlay);
				map.refresh();
				map.getController().zoomToSpan(transitOverlay.getLatSpanE6(),
						transitOverlay.getLonSpanE6());
				map.getController().animateTo(res.getStart().pt);
				Toast.makeText(OtherLocation.this, "you are int ",
						Toast.LENGTH_SHORT).show();
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
				
				if (error != 0 || res == null) {
					Toast.makeText(OtherLocation.this, "抱歉，未找到结果",
							Toast.LENGTH_SHORT).show();
					return;
				}

				routeOverlay = new RouteOverlay(OtherLocation.this, map);
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				// 清除其他图层
				map.getOverlays().clear();
				// 添加路线图层
				map.getOverlays().add(routeOverlay);
				// 执行刷新使生效
				map.refresh();
				// 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
				map.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
						routeOverlay.getLonSpanE6());
				// 移动地图到起点
				map.getController().animateTo(res.getStart().pt);
				// 将路线数据保存给全局变量
				route = res.getPlan(0).getRoute(0);
				// 重置路线节点索引，节点浏览时使用

			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
			}

			public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			public void onGetPoiDetailSearchResult(int type, int iError) {
			}

			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {

			}
		});

		mLocClient = new LocationClient(this);//定位的类
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setScanSpan(1000);// 每一秒钟跟新一次
		option.setAddrType("all");
		mLocClient.setLocOption(option);//设置定位参数
		mLocClient.registerLocationListener(new MyLocationListenner());//注册定位返回监听时间
		mLocClient.start();//开始定位
		map.getController().setZoom(16);
		myLocationOverlay.setData(data);//设置位子图层的位子坐标  并没有用此代码
		map.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		myLocationOverlay.setLocationMode(LocationMode.NORMAL);
		myLocationOverlay.setMarker(null);
		map.refresh();//并没有用此代码
        
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            io=false;
			finish();
			overridePendingTransition(R.anim.stayp, R.anim.welcome_out);
		}

		return super.onKeyDown(keyCode, event);
	}
	
         private boolean is = true;
         private boolean io = true ;
	public class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
      // 获取定位得到的位子信息  坐标
			myPoi = new GeoPoint((int) (location.getLatitude() * 1e6),
					(int) (location.getLongitude() * 1e6));
		
			data.latitude = location.getLatitude();
			data.longitude = location.getLongitude();
			data.accuracy = location.getRadius();
			data.direction = location.getDerect();
			myLocationOverlay.setData(data);
			//百度api的bug解决办法
			if(io)
			map.refresh();
			//这有第一定位才会把自己的位子放置在地图的中央
			if (is) {
               
				controll.animateTo(new GeoPoint((int) (data.latitude * 1e6),
						(int) (data.longitude * 1e6)));
                is=false ;
			}
			
			 

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		map.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		map.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		map.destroy();
	}
	 @Override
	    protected void onSaveInstanceState(Bundle outState) {
	    	super.onSaveInstanceState(outState);
	    	map.onSaveInstanceState(outState);
	    	
	    }
	    
	    @Override
	    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    	super.onRestoreInstanceState(savedInstanceState);
	    	map.onRestoreInstanceState(savedInstanceState);
	    }
	public void DriveClick(View e) {
		serach(e);
	}

	public void BusClick(View e) {
		serach(e);
	}

	private void getpoi() {

		new Thread(new GetPoi(handler,this)).start();
		
	}

	public void WalkClick(View e) {
		serach(e);
	}

	private int po = -1;
	MKPlanNode stNode;
	MKPlanNode enNode;

	private void serach(View e) {
		route = null;
		routeOverlay = null;
		transitOverlay = null;
		// 处理搜索按钮响应
		// 对起点终点的name进行赋值，也可以直接对坐标赋值，赋值坐标则将根据坐标进行搜索
		/*double cLat = 36.650948;
		double cLon = 117.118971;*/
		stNode = new MKPlanNode();
		if (myPoi == null)
			return;
		stNode.pt = myPoi;
		getpoi();
		// enNode = new MKPlanNode();
		// enNode.pt=new GeoPoint(36650948,118115869);

		// 实际使用中请对起点终点城市进行正确的设定
		if (e.equals(findViewById(R.id.drive))) {
			po = 1;
			// mSearch.drivingSearch(null, stNode, null, enNode);
		}
		if (e.equals(findViewById(R.id.bus))) {
			// mSearch.transitSearch(null,stNode, enNode);
			po = 2;
		}
		if (e.equals(findViewById(R.id.walk))) {
			// mSearch.walkingSearch(null, stNode, null, enNode);
			po = 3;
		}
	}
}
