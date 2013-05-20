package com.inzi123.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.inzi123.launcher.R;

public class SettingsFragment extends PreferenceFragment {
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
	}
}
