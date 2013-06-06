package com.inzi123.launcher;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.inzi123.cache.IconCache;
import com.inzi123.data.Datas;
import com.inzi123.db.DBHelper;
import com.inzi123.entity.ApplicationInfo;
import com.inzi123.entity.FavoriteApp;
import com.inzi123.fragment.SettingsFragment;
import com.inzi123.launcher.UpDataAppService.MyBinder;
import com.inzi123.utils.PreferenceUtils;
import com.inzi123.utils.Utils;
import com.inzi123.widget.MySurfaceView;
import com.inzi123.widget.ScrollLayout;
import com.inzi123.widget.ScrollLayout1;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Launcher extends Activity implements OnTouchListener{

	private GridView allAppGv;
	private GridView favAppGv;
	ScrollLayout1 childscrollLayout;
	private ArrayList<ApplicationInfo> allAppList = new ArrayList<ApplicationInfo>();
	private ArrayList<FavoriteApp> favoriteAppList = new ArrayList<FavoriteApp>();

	PackageManager pm;
	IconCache iconCache;
	private LauncherApplication application;

	private DBHelper dbHelper;
	private LayoutInflater li;
	FavortieGvAdapter favortieGvAdapter;
	View layout_weather;
	private float scale;
	private AppAdapter appAdapter;
	public static MySurfaceView view;
	

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MyBinder binder = (MyBinder) service;
			UpDataAppService bindService = binder.getService();
			bindService.MyMethod(Launcher.this);
		}
	};
	private List<AppWidgetProviderInfo> widgetList;
	private AppWidgetManager appWidgetManager;
	public static final int APPWIDGET_HOST_ID = 1024;
	AppWidgetHost host;
	int id;
	private static final int REQUEST_BIND_APPWIDGET = 11;

	ScrollLayout scrollLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view=new MySurfaceView(this);
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
		li = LayoutInflater.from(Launcher.this);
		scale = getResources().getDisplayMetrics().density;
		application = (LauncherApplication) getApplication();
		iconCache = application.getIconCache();
		dbHelper = new DBHelper(this);
		allAppGv = (GridView) findViewById(R.id.allAppGv);
		favAppGv = (GridView) findViewById(R.id.favAppGv);
		weathtext = (TextView) findViewById(R.id.weathtext);
		layout_weather = findViewById(R.id.layout_weather);
		scrollLayout= (ScrollLayout) findViewById(R.id.scrollLayout);
		childscrollLayout= (ScrollLayout1) findViewById(R.id.childscrollLayout);
		scrollLayout.setToScreen(1);
		pm = getPackageManager();
		appAdapter = new AppAdapter();
		favortieGvAdapter = new FavortieGvAdapter();
		Intent bindservice=new Intent(this, UpDataAppService.class);
		bindService(bindservice, conn, Context.BIND_AUTO_CREATE);
		
		loadApps();
		appAdapter = new AppAdapter();
		favortieGvAdapter = new FavortieGvAdapter();

		allAppGv.setOnItemClickListener(appClickListener);

		allAppGv.setOnItemLongClickListener(appItemLongClickListener);
		loadFavoriteApp();

		favAppGv.setOnItemClickListener(favClickListener);
		favAppGv.setOnItemLongClickListener(favItemLongClickListener);
		SettingsFragment f = new SettingsFragment();
		f.getView();
		city = PreferenceUtils.getStringValue(this, "city","芝加哥");
		SharedPreferences sp = getSharedPreferences(
				"com.inzi123.launcher_preferences", Context.MODE_PRIVATE);
		city = sp.getString("city", "奥斯汀");
		layout_weather.setBackgroundResource(Datas.daypics.get(city));
		flushAppGv();
		flushFavGv();

		Intent alert = new Intent(this, AlertService.class);
		startService(alert);
		String text = PreferenceUtils.getStringValue(this, "weatherinfo");
		weathtext.setText(text);
		
		
		appWidgetManager = AppWidgetManager.getInstance(this);
		widgetList = appWidgetManager.getInstalledProviders();
		host = new AppWidgetHost(this, APPWIDGET_HOST_ID);
		host.startListening();
		
		id = host.allocateAppWidgetId();
		AppWidgetProviderInfo appWidgetProviderInfo = widgetList.get(1);
		boolean success = false;
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		addContentView(view, lp);
		view.setZOrderOnTop(true);
        view.getHolder().setFormat(PixelFormat.TRANSLUCENT);

//		success = appWidgetManager.bindAppWidgetIdIfAllowed(id,
//				appWidgetProviderInfo.provider);
//		if (success) {
//			AppWidgetHostView hostView = host.createView(
//					Launcher.this, id, appWidgetProviderInfo);
//			childscrollLayout.addView(hostView);
//			childscrollLayout.requestLayout();
//		} else {
//			Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_BIND);
//			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
//			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER,
//					appWidgetProviderInfo.provider);
//			// TODO: we need to make sure that this accounts for the options
//			// bundle.
//			// intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_OPTIONS,
//			// options);
//			startActivityForResult(intent, REQUEST_BIND_APPWIDGET);
//		}

	}
	
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        view.onTouchEvent(event);
        Log.d("ddv", "activity_ontouch"+event.getAction());
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        view.onTouchEvent(event);
        Log.d("ddv", "activity_onTouchEvent"+event.getAction());
        return false;
    }
    

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 直接返回，没有选择任何一项 ，例如按Back键
		if (resultCode == RESULT_CANCELED)
			return;

		switch (requestCode) {
		case MY_REQUEST_APPWIDGET:
			Log.i(TAG, "MY_REQUEST_APPWIDGET intent info is -----> " + data);
			int appWidgetId = data.getIntExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);

			Log.i(TAG, "MY_REQUEST_APPWIDGET : appWidgetId is ----> "
					+ appWidgetId);

			// 得到的为有效的id
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				// 查询指定appWidgetId的 AppWidgetProviderInfo对象 ，
				// 即在xml文件配置的<appwidget-provider />节点信息
				AppWidgetProviderInfo appWidgetProviderInfo = appWidgetManager
						.getAppWidgetInfo(appWidgetId);

				// 如果配置了configure属性 ， 即android:configure = ""
				// ，需要再次启动该configure指定的类文件,通常为一个Activity
				if (appWidgetProviderInfo.configure != null) {

					Log.i(TAG,
							"The AppWidgetProviderInfo configure info -----> "
									+ appWidgetProviderInfo.configure);

					// 配置此Action
					Intent intent = new Intent(
							AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
					intent.setComponent(appWidgetProviderInfo.configure);
					intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
							appWidgetId);

					startActivityForResult(intent, MY_CREATE_APPWIDGET);
				} else
					// 直接创建一个AppWidget
					onActivityResult(MY_CREATE_APPWIDGET, RESULT_OK, data); // 参数不同，简单回调而已
			}
			break;
		case MY_CREATE_APPWIDGET:
			completeAddAppWidget(data);
			break;
		}

	}
	
	private static String TAG = "AddAppWidget";
	private static final int MY_REQUEST_APPWIDGET = 1;
	private static final int MY_CREATE_APPWIDGET = 2;

	// 向当前视图添加一个用户选择的
	private void completeAddAppWidget(Intent data) {
		Bundle extra = data.getExtras();
		int appWidgetId = extra.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		// 等同于上面的获取方式
		Log.i(TAG, "completeAddAppWidget : appWidgetId is ----> " + appWidgetId);
		if (appWidgetId == -1) {
			Toast.makeText(Launcher.this, "添加窗口小部件有误",
					Toast.LENGTH_SHORT);
			return;
		}
		AppWidgetProviderInfo appWidgetProviderInfo = appWidgetManager
				.getAppWidgetInfo(appWidgetId);
		AppWidgetHostView hostView = host.createView(Launcher.this,
				appWidgetId, appWidgetProviderInfo);
		// linearLayout.addView(hostView) ;
		Rect padding = hostView.getDefaultPaddingForWidget(this,
				appWidgetProviderInfo.provider, null);
		int widget_minWidht = appWidgetProviderInfo.minWidth + padding.left
				+ padding.right;
		int widget_minHeight = appWidgetProviderInfo.minHeight + padding.top
				+ padding.bottom;
		// 设置长宽 appWidgetProviderInfo 对象的 minWidth 和 minHeight 属性
		LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
				widget_minWidht, widget_minHeight);
		// 添加至LinearLayout父视图中
		childscrollLayout.addView(hostView);
	}
	

	String city;

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.change.image");
		registerReceiver(receiver, intentFilter);

		IntentFilter timeFilter = new IntentFilter();
		timeFilter.addAction("com.update.time");
		registerReceiver(timereceiver, timeFilter);

		IntentFilter weatherFilter = new IntentFilter();
		weatherFilter.addAction("com.change.weather");
		registerReceiver(weatherreceiver, weatherFilter);

	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
		unregisterReceiver(timereceiver);
		unregisterReceiver(weatherreceiver);

	}

	public TextView weathtext;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.change.image")) {
				city = intent.getStringExtra("city");
				layout_weather.setBackgroundResource(Datas.dawnpics.get(city));
			}
		}
	};
	private String weather;
	private BroadcastReceiver weatherreceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.change.weather")) {
				weather = intent.getStringExtra("weather");
				weathtext.setText(weather);
			}
		}
	};
	private BroadcastReceiver timereceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.update.time")) {
				Log.i("ada", "更新时间");
				Calendar calendar = Calendar.getInstance();
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				hour = hour / 6;
				if (hour == 0) {
					layout_weather.setBackgroundResource(Datas.dawnpics
							.get(city));
				}
				if (hour == 1) {
					layout_weather.setBackgroundResource(Datas.duskpics
							.get(city));
				}
				if (hour == 2) {
					layout_weather.setBackgroundResource(Datas.daypics
							.get(city));
				}
				if (hour == 3) {
					layout_weather.setBackgroundResource(Datas.nightpics
							.get(city));
				}
			}
		}
	};

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			scrollLayout.setToScreen(1);
		}
		return true;

	};

	private HashMap<String, Integer> appSetting = new HashMap<String, Integer>();
	private HashMap<String, Integer> favSetting = new HashMap<String, Integer>();

	private void loadAppSet() {
		appSetting.put(PreferenceUtils.ALLCOLUMNS,
				PreferenceUtils.getIntValue(this, PreferenceUtils.ALLCOLUMNS));
		appSetting.put(PreferenceUtils.ALLSIZE,
				PreferenceUtils.getIntValue(this, PreferenceUtils.ALLSIZE));
		appSetting.put(PreferenceUtils.ALLLINES,
				PreferenceUtils.getIntValue(this, PreferenceUtils.ALLLINES));
		appSetting.put(PreferenceUtils.ALLTEXT,
				PreferenceUtils.getIntValue(this, PreferenceUtils.ALLTEXT));
	}

	private void loadFavSet() {
		favSetting.put(PreferenceUtils.FAVCOLUMNS,
				PreferenceUtils.getIntValue(this, PreferenceUtils.FAVCOLUMNS));
		favSetting.put(PreferenceUtils.FAVSIZE,
				PreferenceUtils.getIntValue(this, PreferenceUtils.FAVSIZE));
		favSetting.put(PreferenceUtils.FAVLINES,
				PreferenceUtils.getIntValue(this, PreferenceUtils.FAVLINES));
		favSetting.put(PreferenceUtils.FAVTEXT,
				PreferenceUtils.getIntValue(this, PreferenceUtils.FAVTEXT));
	}

	private boolean appTextShow = true;
	private int appTextSize = 4;
	private int appIconSize = 8;
	private int favIconSize = 4;
	private boolean favTextShow = true;
	private int favTextSize = 8;

	private void setAppGv() {
		// 列数
		allAppGv.setNumColumns(appSetting.get(PreferenceUtils.ALLCOLUMNS));
		// 图标大小
		appIconSize = Utils.dip2px(appSetting.get(PreferenceUtils.ALLSIZE),
				scale);
		// 行高
		int num = appSetting.get(PreferenceUtils.ALLLINES);
		if (num > 0) {
			allAppGv.setVerticalSpacing(num);
			appTextShow = true;
		} else {
			appTextShow = false;
		}
		// 文本大小
		appTextSize = Utils.sp2px(appSetting.get(PreferenceUtils.ALLTEXT),
				scale);
		appAdapter.notifyDataSetChanged();
	}

	private void setFavGv() {
		if (favAppGv != null) {
			// 列数
			int i = favSetting.get(PreferenceUtils.FAVCOLUMNS);
			favAppGv.setNumColumns(i);
			// 图标大小
			favIconSize = Utils.dip2px(favSetting.get(PreferenceUtils.FAVSIZE),
					scale);
			// 行高
			int num = favSetting.get(PreferenceUtils.FAVLINES);
			if (num > 0) {
				favAppGv.setVerticalSpacing(num);
				favTextShow = true;
			} else {
				favTextShow = false;
			}
			// 文本大小
			favTextSize = Utils.sp2px(favSetting.get(PreferenceUtils.FAVTEXT),
					scale);
		}
		favortieGvAdapter.notifyDataSetChanged();
	}

	public class AsyncAppLoadSet extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			loadAppSet();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setAppGv();
		}
	}

	public class AsyncFavLoadSet extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			loadFavSet();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			setFavGv();
		}
	}

	public void flushAppGv() {
		new AsyncAppLoadSet().execute();
	}

	public void flushFavGv() {
		new AsyncFavLoadSet().execute();
	}

	private AdapterView.OnItemClickListener favClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			FavoriteApp f = (FavoriteApp) parent.getItemAtPosition(position);
			try {
				Intent i = Intent.parseUri(f.getUri(), 0);
				startActivity(i);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	};

	private AdapterView.OnItemLongClickListener appItemLongClickListener = new AdapterView.OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			showAppMenu(arg1, arg0, arg2);
			return false;
		}
	};

	private AdapterView.OnItemLongClickListener favItemLongClickListener = new AdapterView.OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			FavoriteApp app = (FavoriteApp) parent.getItemAtPosition(position);
			int count = dbHelper.delAppById(app.getId());
			if (count > 0) {
				loadFavoriteApp();
				Log.d("ddv", "删除收藏");
			}
			return true;
		}
	};

	private AdapterView.OnItemClickListener appClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ApplicationInfo ai = (ApplicationInfo) arg0.getItemAtPosition(arg2);
			Intent i = pm.getLaunchIntentForPackage(ai
					.getPackageName(ai.intent));
			// String url = i.toUri(0);
			// try {
			// i = Intent.parseUri(url, 0);
			// } catch (URISyntaxException e) {
			// e.printStackTrace();
			// }
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

	protected void showAppMenu(View anchor, final AdapterView<?> arg0,
			final int arg2) {
		final ApplicationInfo ai = (ApplicationInfo) arg0
				.getItemAtPosition(arg2);
		final PopupMenu menu = new PopupMenu(this, anchor);
		Menu m = menu.getMenu();
		m.add(0, 0, 1, "详情");
		m.add(0, 1, 2, "收藏");
		m.add(0, 2, 3, "卸载");
		menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case 0: {
					Utils.showInstalledAppDetails(Launcher.this,
							ai.getPackageName(ai.intent));
					menu.dismiss();
					break;
				}
				case 1: {
					dbHelper.addApp(ai, ai.resolveInfo.loadLabel(pm).toString());
					loadFavoriteApp();
					Log.d("ddv", "收藏");
					menu.dismiss();
					break;
				}
				case 2: {

					Uri packageURI = Uri.parse("package:"
							+ ai.getPackageName(ai.intent));
					Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
							packageURI);
					startActivity(uninstallIntent);
					menu.dismiss();
					break;
				}
				}
				return false;
			}
		});
		menu.show();

	}

	public void loadApps() {

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> apps = pm.queryIntentActivities(mainIntent, 0);
		for (ResolveInfo resolveInfo : apps) {

			ApplicationInfo ai = new ApplicationInfo(getPackageManager(),
					resolveInfo, iconCache, null);
			allAppList.add(ai);
		}
		allAppGv.setAdapter(appAdapter);
	}

	public void loadFavoriteApp() {
		favoriteAppList = dbHelper.getApps();

		favAppGv.setAdapter(favortieGvAdapter);
		favortieGvAdapter.notifyDataSetChanged();
	}

	class AppAdapter extends BaseAdapter {

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

		TextView tv;

		@Override
		@SuppressWarnings("deprecation")
		public View getView(int arg0, View cv, ViewGroup arg2) {
			if (cv == null) {
				cv = li.inflate(R.layout.app_icon, null);
				tv = (TextView) cv.findViewById(R.id.app_icon);
				cv.setTag(tv);
			} else {
				tv = (TextView) cv.getTag();
			}
			ApplicationInfo ai = allAppList.get(arg0);
			BitmapDrawable drawable = new BitmapDrawable(
					iconCache.getIcon(ai.intent));
			drawable.setBounds(0, 0, appIconSize, appIconSize);
			tv.setCompoundDrawables(null, drawable, null, null);
			tv.setTextSize(appTextSize);
			if (appTextShow) {
				tv.setText(ai.resolveInfo.loadLabel(getPackageManager()));
			} else {
				tv.setText("");
			}

			return cv;
		}

	}

	private class FavortieGvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (favoriteAppList != null) {
				return favoriteAppList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (favoriteAppList != null) {
				return favoriteAppList.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		TextView tv;

		@SuppressWarnings("deprecation")
		@Override
		public View getView(int position, View cv, ViewGroup parent) {
			if (cv == null) {
				cv = li.inflate(R.layout.app_icon, null);
				tv = (TextView) cv.findViewById(R.id.app_icon);
				cv.setTag(tv);
			} else {
				tv = (TextView) cv.getTag();
			}
			FavoriteApp app = favoriteAppList.get(position);
			BitmapDrawable drawable = null;
			try {
				drawable = new BitmapDrawable(iconCache.getIcon(Intent
						.parseUri(app.uri, 0)));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			drawable.setBounds(0, 0, favIconSize, favIconSize);
			tv.setCompoundDrawables(null, drawable, null, null);
			tv.setTextSize(favTextSize);
			if (favTextShow) {
				tv.setText(app.getTitle());
			} else {
				tv.setText("");
			}
			return cv;
		}
	}
	public void delByPackageName(String packageName){
		dbHelper.delFavByPackageName(packageName);
	}
	
}
