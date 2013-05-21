package com.inzi123.launcher;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.carapi.Weather;
import com.baidu.carapi.WeatherApi;
import com.baidu.carapi.Weatherinfo;
import com.inzi123.utils.PreferenceUtils;

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
		if (v != null) {
			String old = PreferenceUtils.getStringValue(this, "weatherinfo");
			if (!old.equals(v)) {
				Intent intent = new Intent("com.change.weather");
				intent.putExtra("weather", v);
				sendBroadcast(intent);
				PreferenceUtils.setStringValue(this, "weatherinfo", v);
			}

		}

	}

	private String getWeather(Weatherinfo info) {
		String resutl = null;
		if (info != null) {
			List<Weather> ws = info.getResults();
			if (ws != null && ws.size() > 0) {
				Weather w = ws.get(0);
				resutl =info.getCurrentCity()+"的天气\n"+ w.getWeather() + "\n" + w.getTemperature();
			}
		}
		return resutl;
	}

	private Runnable updateWeather = new Runnable() {

		@Override
		public void run() {
			String city=PreferenceUtils.getStringValue(UpdateWeatherService.this, "cityname", "长寿");
			Weatherinfo info = WeatherApi.findByCity(city);
			sendWeather(info);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
