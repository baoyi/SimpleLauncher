package net.alamoapps.launcherdfm;

import com.inzi123.launcher.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
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
		setValuesFromXml(paramAttributeSet);
		this.mSeekBar = new SeekBar(paramContext, paramAttributeSet);
		this.mSeekBar.setMax(this.mMaxValue - this.mMinValue);
		this.mSeekBar.setOnSeekBarChangeListener(this);
	}

	private void setValuesFromXml(AttributeSet paramAttributeSet) {
		this.mMaxValue = paramAttributeSet.getAttributeIntValue(
				"http://schemas.android.com/apk/res/android", "max", 100);
		this.mMinValue = paramAttributeSet.getAttributeIntValue(
				"http://robobunny.com", "min", 0);
		this.mUnitsLeft = getAttributeStringValue(paramAttributeSet,
				"http://robobunny.com", "unitsLeft", "");
		this.mUnitsRight = getAttributeStringValue(
				paramAttributeSet,
				"http://robobunny.com",
				"unitsRight",
				getAttributeStringValue(paramAttributeSet,
						"http://robobunny.com", "units", ""));
		try {
			String str = paramAttributeSet.getAttributeValue(
					"http://robobunny.com", "interval");
			if (str != null)
				this.mInterval = Integer.parseInt(str);
			return;
		} catch (Exception localException) {
			while (true)
				Log.e(this.TAG, "Invalid interval value", localException);
		}
	}

	public void onBindView(View paramView) {
		super.onBindView(paramView);
		try {
			ViewParent localViewParent = this.mSeekBar.getParent();
			ViewGroup localViewGroup = (ViewGroup) paramView
					.findViewById(R.id.seekBarPrefBarContainer);
			if (localViewParent != localViewGroup) {
				if (localViewParent != null)
					((ViewGroup) localViewParent).removeView(this.mSeekBar);
				localViewGroup.removeAllViews();
				localViewGroup.addView(this.mSeekBar, -1, -2);
			}
			updateView(paramView);
			return;
		} catch (Exception localException) {
			while (true)
				Log.e(this.TAG,
						"Error binding view: " + localException.toString());
		}
	}

	protected View onCreateView(ViewGroup paramViewGroup) {
		RelativeLayout localRelativeLayout = null;
		try {
			localRelativeLayout = (RelativeLayout) ((LayoutInflater) getContext()
					.getSystemService("layout_inflater")).inflate(
					R.layout.seek_bar_preference, paramViewGroup, false);
			this.v = localRelativeLayout;
			return localRelativeLayout;
		} catch (Exception localException) {
			while (true)
				Log.e(this.TAG, "Error creating seek bar preference",
						localException);
		}
	}

	protected Object onGetDefaultValue(TypedArray paramTypedArray, int paramInt) {
		return Integer.valueOf(paramTypedArray.getInt(paramInt, 50));
	}

	public void onProgressChanged(SeekBar paramSeekBar, int paramInt,
			boolean paramBoolean) {
		int i = paramInt + this.mMinValue;
		if (i > this.mMaxValue)
			i = this.mMaxValue;
		paramSeekBar.setProgress(this.mCurrentValue - this.mMinValue);
		this.mCurrentValue = i;
		this.mStatusText.setText(String.valueOf(i));
	      persistInt(i);
	     

	}

	protected void onSetInitialValue(boolean paramBoolean, Object paramObject) {
	      this.mCurrentValue = getPersistedInt(this.mCurrentValue);

	}

	public void onStartTrackingTouch(SeekBar paramSeekBar) {
	}

	public void onStopTrackingTouch(SeekBar paramSeekBar) {
		notifyChanged();
	}

	public void setShowValue(boolean paramBoolean) {
		this.show = paramBoolean;
	}

	protected void updateView(View paramView) {
		try {
			RelativeLayout localRelativeLayout = (RelativeLayout) paramView;
			this.mStatusText = ((TextView) localRelativeLayout
					.findViewById(R.id.seekBarPrefValue));
			this.mStatusText.setText(String.valueOf(this.mCurrentValue));
			this.mStatusText.setMinimumWidth(30);
			this.mSeekBar.setProgress(this.mCurrentValue - this.mMinValue);
		} catch (Exception localException) {
			Log.e(this.TAG, "Error updating seek bar preference",
					localException);
		}
	}
}

/*
 * Location: D:\tool\dex2jar\sf_dex2jar.jar Qualified Name:
 * net.alamoapps.launcherdfm.SeekBarPreference JD-Core Version: 0.6.2
 */