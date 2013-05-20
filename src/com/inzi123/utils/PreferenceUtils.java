package com.inzi123.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {
	public static final String FAV_GRIDVIEW_NUMCOLUMNS="fav_gridview_numcolumns";
	public static final String APP_GRIDVIEW_NUMCOLUMNS="app_gridview_numcolumns";
	public static final String FAV_ICONSIZE="fav_iconsize";
	public static final String APP_ICONSIZE="app_iconsize";
	public static final String FAV_HORIZONTALSPACING="fav_horizontalSpacing";
	public static final String APP_HORIZONTALSPACING="app_horizontalSpacing";
	public static final String FAV_TEXTSIZE="fav_textsize";
	public static final String APP_TEXTSIZE="app_textsize";
	
	public static void setIntValue(Context context,String key,int value){
		SharedPreferences sp=context.getSharedPreferences("gridViewSetting", Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getIntValue(Context context,String key){
		int value=-1;
		SharedPreferences sp=context.getSharedPreferences("gridViewSetting", Context.MODE_PRIVATE);
		value=sp.getInt(key, -1);
		return value;
	}
	
}
