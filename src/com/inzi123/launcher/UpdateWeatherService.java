package com.inzi123.launcher;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.carapi.Weather;
import com.baidu.carapi.WeatherApi;
import com.baidu.carapi.Weatherinfo;

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

	public void sendWeather(Weatherinfo info) {
		String v = getWeather(info);
		Intent intent = new Intent("com.change.weather");
		intent.putExtra("weather", v);
		sendBroadcast(intent);
	}

	private String getWeather(Weatherinfo info) {
		String resutl="";
		if(info!=null){
			List<Weather> ws=	info.getResults();
			if(ws!=null&&ws.size()>0){
				resutl=ws.get(0).getWeather();
			}
		}
		return resutl;
	}

	private Runnable updateWeather = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Weatherinfo info = WeatherApi.findByCity("西安");
			sendWeather(info);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
