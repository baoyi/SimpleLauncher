package com.nizi123.launcher;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		loadApps();
		AppAdapter adapter = new AppAdapter();
		allAppGv.setAdapter(adapter);

		allAppGv.setOnItemClickListener(appClickListener);
		
		allAppGv.setOnItemLongClickListener(appItemLongClickListener);

	}
	
	private AdapterView.OnItemLongClickListener appItemLongClickListener=new AdapterView.OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			ResolveInfo ri=(ResolveInfo) arg0.getItemAtPosition(arg2);
			Uri packageURI = Uri.parse("package:"+ri.activityInfo.packageName);   
			Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);   
			startActivity(uninstallIntent);
			return false;
		}
	};

	private AdapterView.OnItemClickListener appClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ResolveInfo ri=(ResolveInfo) arg0.getItemAtPosition(arg2);
			// 取到点击的包名
			Intent i = pm.getLaunchIntentForPackage(ri.activityInfo.packageName);
			// 如果该程序不可启动（像系统自带的包，有很多是没有入口的）会返回NULL
			if (i != null)
				startActivity(i);

		}
	};

	
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
	
	class AppAdapter extends BaseAdapter {
		private LayoutInflater li;

		public AppAdapter() {
			li = LayoutInflater.from(Launcher.this);
		}

		@Override
		public int getCount() {
			if (allAppList != null) {
				return allAppList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			if (allAppList != null) {
				return allAppList.get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

//		private ViewHolder holder;

		@Override
		public View getView(int arg0, View cv, ViewGroup arg2) {
//			if (cv == null) {
//				cv = li.inflate(R.layout.item_app, null);
//				holder = new ViewHolder();
//				holder.icon = (ImageView) cv.findViewById(R.id.appIcon);
//				holder.name = (TextView) cv.findViewById(R.id.appName);
//				cv.setTag(holder);
//			} else {
//				holder = (ViewHolder) cv.getTag();
//			}
			// PackageInfo pi=appList.get(arg0);
			// ApplicationInfo info=pi.applicationInfo;
			// holder.icon.setImageDrawable(pm.getApplicationIcon(info));
			// holder.name.setText(pm.getApplicationLabel(info));
			ResolveInfo ri = allAppList.get(arg0);
//			holder.icon.setImageDrawable(ri.activityInfo.loadIcon(pm));
//			holder.name.setText(ri.activityInfo.loadLabel(pm));
			cv= li.inflate(R.layout.app_icon, null);
			TextView tv=(TextView) cv.findViewById(R.id.app_icon);
			Drawable drawable=ri.activityInfo.loadIcon(pm);
			drawable.setBounds(0, 0, 80, 80);  
			tv.setCompoundDrawables(null,drawable , null,null);
			Log.d("ddv", "-----"+ri.activityInfo.loadIcon(pm));
			tv.setText(ri.activityInfo.loadLabel(pm));
//			tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80));
//			Log.i("ddv", ri.activityInfo.loadLabel(pm).toString());
			return cv;
		}

//		class ViewHolder {
//			ImageView icon;
//			TextView name;
//		}
	}

}
