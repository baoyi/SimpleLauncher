package com.inzi123.launcher;

import android.app.Application;
import android.os.Process;
import android.os.Vibrator;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.inzi123.cache.IconCache;

public class LauncherApplication extends Application {

	public LocationClient mLocationClient = null;
	// public LocationClient locationClient = null;
	// public LocationClient LocationClient = null;
	private String mData;
	// public MyLocationListenner listener = new MyLocationListenner();
	// public MyLocationListenner locListener = new MyLocationListenner();
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

		super.onCreate();
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());

		iconCache = new IconCache(this);
		
	}

	public IconCache getIconCache() {
		return iconCache;
	}
}