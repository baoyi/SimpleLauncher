package com.inzi123.launcher;

import com.baidu.carapi.WeatherApi;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.inzi123.utils.PreferenceUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateCityService extends Service {
	public UpdateCityService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		final LauncherApplication location = (LauncherApplication) getApplication();
		location.mLocationClient
				.registerLocationListener(new BDLocationListener() {

					@Override
					public void onReceivePoi(BDLocation arg0) {
						// TODO Auto-generated method stub

						if (arg0 == null) {
							return;
						}
						String city = arg0.getCity();
						if(city!=null){
							saveCity(city);
							location.mLocationClient.stop();
						}
					
					}


					@Override
					public void onReceiveLocation(BDLocation arg0) {
						if (arg0 == null) {
							return;
						}
						String city = arg0.getCity();
						if(city!=null){
							saveCity(city);
							location.mLocationClient.stop();
						}

					}
				});
		location.mLocationClient.start();
		return super.onStartCommand(intent, flags, startId);
	}

	public void saveCity(String name) {
		PreferenceUtils.setStringValue(this, "cityname", name);
	}
}
