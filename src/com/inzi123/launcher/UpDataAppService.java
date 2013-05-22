package com.inzi123.launcher;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class UpDataAppService extends Service {

	private Launcher launcher;
	
	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}
	public class MyBinder extends Binder {
		public UpDataAppService getService() {
			return UpDataAppService.this;
		}
	}

	private MyBinder myBinder = new MyBinder();

	public void MyMethod(Launcher launcher) {
		Log.i("ddv", "BindService-->MyMethod()");
		this.launcher=launcher;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(launcher!=null){
			launcher.loadApps();
			if(intent.getStringExtra("type")!=null&&intent.getStringExtra("type").equals("unstall")){
				launcher.delByPackageName(intent.getStringExtra("packageName"));
				launcher.loadFavoriteApp();
			}
			Log.i("ddv", "更新列表");
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
}
