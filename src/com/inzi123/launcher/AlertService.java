package com.inzi123.launcher;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
		am.setRepeating(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis(), 5*1000, sender);
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
