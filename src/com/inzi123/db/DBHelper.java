package com.inzi123.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.inzi123.entity.ApplicationInfo;
import com.inzi123.entity.FavoriteApp;

public class DBHelper extends SQLiteOpenHelper {

	private final static String NAME = "inzi_launcher_db";
	private final static int VERSION = 1;
	
	public static final String ID="_id";
	public static final String TITLE="title";
	public static final String ICONPACKAGE="iconpackage";
	public static final String ICONRESOURCE="iconresource";
	public static final String URI="uri";
	public static final String DISPLAYMODE="displaymode";
	
	private SQLiteDatabase db;

	public DBHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		db=arg0;
		String sql = "create table favoriteapp(" +
				ID+" integer primary key autoincrement," +
				TITLE+" varchar(32),intent varchar(200)," +
				ICONPACKAGE+" varchar(200), " +
				ICONRESOURCE+" varchar(200), " +
				URI+" varchar(200), " +
				DISPLAYMODE+" varchar(200));";
		arg0.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
	
	public void addApp(ApplicationInfo info,String title){
		db=getWritableDatabase();
		String sql="insert into favoriteapp (title,intent,iconpackage,iconresource,uri,displaymode) values(?,?,?,?,?,?);";
		db.execSQL(sql, new Object[]{title,info.intent,info.intent.getPackage(),info.intent,info.intent.toUri(0),0});
		db.close();
	}
	
	public ArrayList<FavoriteApp> getApps(){
		ArrayList<FavoriteApp> apps=new ArrayList<FavoriteApp>();
		db=getReadableDatabase();
		String sql="select * from favoriteapp";
		Cursor cursor=db.rawQuery(sql,null);
		while(cursor.moveToNext()){
			FavoriteApp app=new FavoriteApp();
			app.setId(cursor.getInt(cursor.getColumnIndex(ID)));
			app.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
			app.setIconpackage(cursor.getString(cursor.getColumnIndex(ICONPACKAGE)));
			app.setIconresource(cursor.getString(cursor.getColumnIndex(ICONRESOURCE)));
			app.setUri(cursor.getString(cursor.getColumnIndex(URI)));
			app.setDisplaymode(cursor.getString(cursor.getColumnIndex(DISPLAYMODE)));
			apps.add(app);
		}
		cursor.close();
		db.close();
		return apps;
		
	}
	
	public int delAppById(int id){
		int count=0;
		db=getWritableDatabase();
//		String sql="delete from favoriteapp where "+ID+"=?";
//		db.execSQL(sql, new Object[]{id});
		count=db.delete("favoriteapp", "_id=?", new String[]{id+""});
		return count;
	}

	public int delFavByPackageName(String packagename){
		int count=0;
		db=getWritableDatabase();
		count=db.delete("favoriteapp", "intent like ?", new String[]{"%cmp="+packagename+"/%"});
		return count;
		
	}
		
	
}
