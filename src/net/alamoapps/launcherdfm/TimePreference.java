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
	}

	public TimePreference(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public void onProgressChanged(SeekBar paramSeekBar, int paramInt,
			boolean paramBoolean) {
		super.onProgressChanged(paramSeekBar, paramInt, paramBoolean);
		this.mStatusText.setVisibility(4);
		TextView localTextView = (TextView) this.v.findViewById(2131427349);
		localTextView.setVisibility(0);
	}

}

