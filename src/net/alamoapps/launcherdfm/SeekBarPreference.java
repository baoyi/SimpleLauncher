package net.alamoapps.launcherdfm;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarPreference extends Preference implements
		SeekBar.OnSeekBarChangeListener {
	private static final String ANDROIDNS = "http://schemas.android.com/apk/res/android";
	private static final int DEFAULT_VALUE = 50;
	private static final String ROBOBUNNYNS = "http://robobunny.com";
	private final String TAG = getClass().getName();
	protected int mCurrentValue;
	private int mInterval = 1;
	private int mMaxValue = 100;
	private int mMinValue = 0;
	private SeekBar mSeekBar;
	protected TextView mStatusText;
	private String mUnitsLeft = "";
	private String mUnitsRight = "";
	private boolean show = true;
	protected View v;

	public SeekBarPreference(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initPreference(paramContext, paramAttributeSet);
	}

	public SeekBarPreference(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		initPreference(paramContext, paramAttributeSet);
	}

	private String getAttributeStringValue(AttributeSet paramAttributeSet,
			String paramString1, String paramString2, String paramString3) {
		String str = paramAttributeSet.getAttributeValue(paramString1,
				paramString2);
		if (str == null)
			str = paramString3;
		return str;
	}

	private void initPreference(Context paramContext,
			AttributeSet paramAttributeSet) {
		this.mSeekBar = new SeekBar(paramContext, paramAttributeSet);
		this.mSeekBar.setOnSeekBarChangeListener(this);
	}

	public void onBindView(View paramView) {
		super.onBindView(paramView);
		try {
			ViewParent localViewParent = this.mSeekBar.getParent();
			ViewGroup localViewGroup = (ViewGroup) paramView
					.findViewById(2131427353);
			if (localViewParent != localViewGroup) {
				if (localViewParent != null)
					((ViewGroup) localViewParent).removeView(this.mSeekBar);
				localViewGroup.removeAllViews();
				localViewGroup.addView(this.mSeekBar, -1, -2);
			}
			return;
		} catch (Exception localException) {
			while (true)
				Log.e(this.TAG,
						"Error binding view: " + localException.toString());
		}
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

}
