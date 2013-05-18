package com.nizi123.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private final static String NAME = "inzi_launcher_db";
	private final static int VERSION = 1;

	public DBHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String sql = "create table favoriteapp(" +
				"_id integer primary key autoincrement," +
				"title varchar(32),intent varchar(200)," +
				"iconpackage varchar(200), " +
				"iconresource varchar(200), " +
				"uri varchar(200), " +
				"displaymode varchar(200));";
		arg0.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
