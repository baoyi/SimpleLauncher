package net.alamoapps.launcherdfm;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class TimePreference extends SeekBarPreference {
	public TimePreference(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		setShowValue(false);
	}

	public TimePreference(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		setShowValue(false);
	}

	public void onProgressChanged(SeekBar paramSeekBar, int paramInt,
			boolean paramBoolean) {
		super.onProgressChanged(paramSeekBar, paramInt, paramBoolean);
		setShowValue(false);
		this.mStatusText.setVisibility(4);
		TextView localTextView = (TextView) this.v.findViewById(2131427349);
		localTextView.setVisibility(0);
	}

	protected void updateView(View paramView) {
		super.updateView(paramView);
		this.mStatusText.setVisibility(4);
		TextView localTextView = (TextView) paramView.findViewById(2131427349);
		localTextView.setVisibility(0);
		if (DateFormat.is24HourFormat(getContext()))
			localTextView.setText(this.mCurrentValue + ":00");
	}
}

/*
 * Location: D:\tool\dex2jar\sf_dex2jar.jar Qualified Name:
 * net.alamoapps.launcherdfm.TimePreference JD-Core Version: 0.6.2
 */