package net.alamoapps.launcherdfm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.preference.ListPreference;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IconPreference extends ListPreference
{
  public IconPreference(Context paramContext)
  {
    super(paramContext);
    init();
  }

  private void init() {
	// TODO Auto-generated method stub
	
}

public IconPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

}

/* Location:           D:\tool\dex2jar\sf_dex2jar.jar
 * Qualified Name:     net.alamoapps.launcherdfm.IconPreference
 * JD-Core Version:    0.6.2
 */