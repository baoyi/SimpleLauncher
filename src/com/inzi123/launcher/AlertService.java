package com.inzi123.launcher;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlertService extends Service {

	public AlertService() {
	}

	@Override
	public void onCreate() {
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);  
		
		Intent intent=new Intent(this,UpdataPicService.class);
		PendingIntent sender=PendingIntent.getService(this, 0, intent, 0);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.currentThreadTimeMillis(), 5*1000, sender);
		
		
		Intent updateWeather=new Intent(this,UpdateWeatherService.class);
		PendingIntent pendingupdateweather=PendingIntent.getService(this, 0, updateWeather, 0);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME,SystemClock.currentThreadTimeMillis(), 5*1000, pendingupdateweather);

		Intent UpdateCityService=new Intent(this,UpdateCityService.class);
		PendingIntent pendingUpdateCityService=PendingIntent.getService(this, 0, UpdateCityService, 0);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.currentThreadTimeMillis(), 5*1000, pendingUpdateCityService);

		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
