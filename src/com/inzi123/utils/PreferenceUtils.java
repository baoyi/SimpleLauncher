package com.inzi123.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {
	public static final String FAVCOLUMNS="favColumns";
	public static final String ALLCOLUMNS="allColumns";
	public static final String FAVSIZE="favSize";
	public static final String ALLSIZE="allSize";
	public static final String ALLLINES="allLines";
	public static final String FAVLINES="favLines";
	public static final String FAVTEXT="favText";
	public static final String ALLTEXT="allText";
	
	public PreferenceUtils (){
		
	}
	
	
	public static void setIntValue(Activity activity,String key,int value){
		SharedPreferences sp=activity.getPreferences(Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getIntValue(Activity activity,String key){
		int value=-1;
		SharedPreferences sp=activity.getPreferences(Context.MODE_PRIVATE);
		value=sp.getInt(key, -1);
		return value;
	}
	
}
