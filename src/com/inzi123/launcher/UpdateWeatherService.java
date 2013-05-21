package com.inzi123.launcher;

import com.baidu.carapi.WeatherApi;
import com.baidu.carapi.Weatherinfo;

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
		new Thread(updateWeather).start();
		return super.onStartCommand(intent, flags, startId);
	}

	private Runnable updateWeather = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Weatherinfo info=		WeatherApi.findByCity("西安");
			System.out.println(info);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
