package com.inzi123.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.inzi123.launcher.R;
import com.inzi123.utils.Utils;
import com.inzi123.launcher.Launcher;

public class SettingsFragment extends PreferenceFragment  implements OnSharedPreferenceChangeListener{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.a);
		findPreference("city").setOnPreferenceChangeListener(
				new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference arg0,
							Object arg1) {
						Log.i("ada", "" + arg1);

						Intent intent = new Intent("com.change.image");
						intent.putExtra("city", arg1.toString());
						getActivity().sendBroadcast(intent);
						return true;
					}
				});
		PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
		
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if(getActivity()!=null){
			if(key.equals("allColumns")||key.equals("allSize")||key.equals("allLines")||key.equals("allText")){
				((Launcher)getActivity()).flushAppGv();
			}
			if(key.equals("favColumns")||key.equals("favSize")||key.equals("favLines")||key.equals("favText")){
				((Launcher)getActivity()).flushFavGv();
			}
		}
		
	}


}
