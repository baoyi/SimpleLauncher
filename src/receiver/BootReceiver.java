package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	 @Override    
	    public void onReceive(Context context, Intent intent){  
	        //接收安装广播   
	        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {     
	            String packageName = intent.getDataString();     
	            Log.i("ddv", "安装了:" +packageName + "包名的程序");       
	        }     
	        //接收卸载广播    
	        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {     
	            String packageName = intent.getDataString();     
	            Log.i("ddv", "卸载了:"  + packageName + "包名的程序");  
	   
	        }  
	 }
}
