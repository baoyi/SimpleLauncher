package com.inzi123.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.nizi123.launcher.R;

public class SettingsFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences_plus);
	}
}