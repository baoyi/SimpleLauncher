package com.inzi123.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.GridView;
import android.widget.Toast;

public class Utils {
	private static final String SCHEME = "package";
	/**
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
	 */
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	/**
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
	 */
	private static final String APP_PKG_NAME_22 = "pkg";
	/**
	 * InstalledAppDetails所在包名
	 */
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	/**
	 * InstalledAppDetails类名
	 */
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

	public static void showInstalledAppDetails(Context context,
			String packageName) {
		
		
//		   Intent localIntent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", ((ResolveInfo)AppsFragment.this.ress.get(paramAnonymousInt)).activityInfo.packageName, null));
//           localIntent.setFlags(276824064);
		Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
		} else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
			// 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
			final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
					: APP_PKG_NAME_21);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME,
					APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPkgName, packageName);
		}
		context.startActivity(intent);
	}

	public static void showMessage(Context context,String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void loadGridViewSet(GridView gv,int type){
		
	}
	
	 /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param scale（DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(float pxValue, float scale) {
     return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param scale（DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(float dipValue, float scale) {
     return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, float fontScale) {
     return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, float fontScale) {
     return (int) (spValue * fontScale + 0.5f);
    }
}
