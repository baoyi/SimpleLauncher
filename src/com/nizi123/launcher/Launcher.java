package com.nizi123.launcher;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

public class Launcher extends Activity {

	private GridView allAppGv;
	private ArrayList<ResolveInfo> allAppList = new ArrayList<ResolveInfo>();
	PackageManager pm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		allAppGv=(GridView) findViewById(R.id.allAppGv);
		pm=getPackageManager();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void loadApps() {

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> apps = pm.queryIntentActivities(mainIntent, 0);
		// Collections.sort(apps, new
		// ResolveInfo.DisplayNameComparator(manager));
		for (ResolveInfo resolveInfo : apps) {
			allAppList.add(resolveInfo);
		}
		Log.i("ddv", allAppList.size() + "*********");
		// List<PackageInfo> data=pm.getInstalledPackages(0);
		// for (PackageInfo pi : data) {
		//
		// if((pi.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)<=0){
		// appList.add(pi);
		// }
		// }

	}

}
