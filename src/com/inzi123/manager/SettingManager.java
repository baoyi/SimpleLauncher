package com.inzi123.manager;

import com.nizi123.launcher.R;
import com.nizi123.launcher.TitleSettingsActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingManager implements OnClickListener {

	public void init(Activity context) {
		this.context = context;
		main = (TextView) context.findViewById(R.id.main);
		main.setOnClickListener(this);
	}

	private TextView main;
	private Activity context;

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.main) {
			Intent intent = new Intent(context, TitleSettingsActivity.class);
			context.startActivity(intent);

		}

	}
}
