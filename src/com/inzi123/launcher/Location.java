package com.inzi123.launcher;

import com.baidu.location.*;
import com.inzi123.cache.IconCache;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;
import android.os.Process;
import android.os.Vibrator;

public class Location extends Application {

	public LocationClient mLocationClient = null;
	// public LocationClient locationClient = null;
	// public LocationClient LocationClient = null;
	private String mData;
	public MyLocationListenner myListener = new MyLocationListenner();
	// public MyLocationListenner listener = new MyLocationListenner();
	// public MyLocationListenner locListener = new MyLocationListenner();
	public NotifyLister mNotifyer = null;
	public Vibrator mVibrator01;
	public static String TAG = "LocTestDemo";
	private IconCache iconCache;

	@Override
	public void onCreate() {
		mLocationClient = new LocationClient(this);
		LocationClientOption option=new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");
		option.setPoiExtraInfo(true);
		mLocationClient.setLocOption(option);
		// locationClient = new LocationClient( this );
		// LocationClient = new LocationClient( this );
		mLocationClient.registerLocationListener(myListener);
		// locationClient.registerLocationListener( listener );
		// LocationClient.registerLocationListener( locListener );
		// 位锟斤拷锟斤拷锟斤拷锟斤拷卮锟斤拷锟�
		// mNotifyer = new NotifyLister();
		// mNotifyer.SetNotifyLocation(40.047883,116.312564,3000,"gps");//4锟斤拷锟斤拷锟斤拷锟斤拷要位锟斤拷锟斤拷锟窖的碉拷锟斤拷锟疥，锟斤拷锟藉含锟斤拷锟斤拷锟斤拷为锟斤拷纬锟饺ｏ拷锟斤拷锟饺ｏ拷锟斤拷锟诫范围锟斤拷锟斤拷锟较碉拷锟斤拷锟�gcj02,gps,bd09,bd09ll)
		// mLocationClient.registerNotify(mNotifyer);

		super.onCreate();
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());

		iconCache = new IconCache(this);
		
	}

	public IconCache getIconCache() {
		return iconCache;
	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			String city = poiLocation.getCity();
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
			mVibrator01.vibrate(1000);
		}
	}
}