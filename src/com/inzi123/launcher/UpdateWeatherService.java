package com.inzi123.launcher;

import com.baidu.carapi.WeatherApi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateWeatherService extends Service {
	public UpdateWeatherService() {
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
		WeatherApi.findByCity(city);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
