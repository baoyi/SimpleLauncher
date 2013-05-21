package com.inzi123.data;

import java.util.HashMap;

import com.inzi123.launcher.R;

public class Datas {

	public static HashMap<String,Integer> dawnpics;
	public static HashMap<String,Integer> duskpics;
	public static HashMap<String,Integer> nightpics;
	public static HashMap<String,Integer> daypics;

	static{
		dawnpics=new HashMap<String, Integer>();
		dawnpics.put("奥斯汀", R.drawable.dawn);
		dawnpics.put("海滩", R.drawable.dawn_a);
		dawnpics.put("柏林", R.drawable.dawn_b);
		dawnpics.put("芝加哥", R.drawable.dawn_c);
		dawnpics.put("通用", R.drawable.dawn_g);
		dawnpics.put("大平原", R.drawable.dawn_gp);
		dawnpics.put("伦敦", R.drawable.dawn_l);
		dawnpics.put("纽约", R.drawable.dawn_ny);
		dawnpics.put("旧金山", R.drawable.dawn_p);
		dawnpics.put("西雅图", R.drawable.dawn_s);
		dawnpics.put("塔霍湖", R.drawable.dawn_t);

		
		duskpics=new HashMap<String, Integer>();
		duskpics.put("奥斯汀", R.drawable.dusk);
		duskpics.put("海滩", R.drawable.dusk_a);
		duskpics.put("柏林", R.drawable.dusk_b);
		duskpics.put("芝加哥", R.drawable.dusk_c);
		duskpics.put("通用", R.drawable.dusk_g);
		duskpics.put("大平原", R.drawable.dusk_gp);
		duskpics.put("伦敦", R.drawable.dusk_l);
		duskpics.put("纽约", R.drawable.dusk_ny);
		duskpics.put("旧金山", R.drawable.dusk_p);
		duskpics.put("西雅图", R.drawable.dusk_s);
		duskpics.put("塔霍湖", R.drawable.dusk_t);
		
		nightpics=new HashMap<String, Integer>();
		nightpics.put("奥斯汀", R.drawable.night);
		nightpics.put("海滩", R.drawable.night_a);
		nightpics.put("柏林", R.drawable.night_b);
		nightpics.put("芝加哥", R.drawable.night_c);
		nightpics.put("通用", R.drawable.night_g);
		nightpics.put("大平原", R.drawable.night_gp);
		nightpics.put("伦敦", R.drawable.night_l);
		nightpics.put("纽约", R.drawable.night_ny);
		nightpics.put("旧金山", R.drawable.night_p);
		nightpics.put("西雅图", R.drawable.night_s);
		nightpics.put("塔霍湖", R.drawable.night_t);
		
		
		daypics=new HashMap<String, Integer>();
		daypics.put("奥斯汀", R.drawable.day);
		daypics.put("海滩", R.drawable.day_a);
		daypics.put("柏林", R.drawable.day_b);
		daypics.put("芝加哥", R.drawable.day_c);
		daypics.put("通用", R.drawable.day_g);
		daypics.put("大平原", R.drawable.day_gp);
		daypics.put("伦敦", R.drawable.day_l);
		daypics.put("纽约", R.drawable.day_ny);
		daypics.put("旧金山", R.drawable.day_p);
		daypics.put("西雅图", R.drawable.day_s);
		daypics.put("塔霍湖", R.drawable.day_t);

	}
}
