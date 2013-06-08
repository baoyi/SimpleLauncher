package com.inzi123.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.inzi123.entity.AppSort;
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
	
	public static final String APPNAME="name";
	public static final String MINST="mInst";
	public static final String FREQUENCY="frequency";
	public static final String PACKAGENAME="package";
	
	public static final String ASC="asc";
	public static final String DESC="desc";
	
	private SQLiteDatabase db;

	public DBHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		db=arg0;
		String createFav = "create table favoriteapp(" +
				ID+" integer primary key autoincrement," +
				TITLE+" varchar(32),intent varchar(200)," +
				ICONPACKAGE+" varchar(200), " +
				ICONRESOURCE+" varchar(200), " +
				URI+" varchar(200), " +
				DISPLAYMODE+" varchar(200));";
		arg0.execSQL(createFav);
		
		String createAppSort="create table appsort ( " +
				ID+" integer primary key autoincrement," +
				APPNAME+" varchar(50), " +
				MINST+" long, " +
				FREQUENCY+" integer," +
				PACKAGENAME+" varchar(200) unique);";
		arg0.execSQL(createAppSort);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
	
	public void addAppToFav(ApplicationInfo info){
		db=getWritableDatabase();
		String sql="insert into favoriteapp (title,intent,iconpackage,iconresource,uri,displaymode) values(?,?,?,?,?,?);";
		db.execSQL(sql, new Object[]{info.getmName(),info.intent,info.intent.getPackage(),info.intent,info.intent.toUri(0),0});
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
		db.close();
		return count;
	}

	public int delFavByPackageName(String packagename){
		int count=0;
		db=getWritableDatabase();
		count=db.delete("favoriteapp", "intent like ?", new String[]{"%cmp="+packagename+"/%"});
		db.close();
		return count;
	}
	
	public long addAppToSort(ApplicationInfo info){
		long count=0;
		db=getWritableDatabase();
		ContentValues cv=new ContentValues(); 
		cv.put(APPNAME, info.getmName());
		cv.put(MINST, info.getmInst());
		cv.put(FREQUENCY, 0);
		cv.put(PACKAGENAME, info.getmPack());
		count = db.insert("appsort", null, cv);
		db.close();
		return count;
	}
	
	public void addAppListToSort(HashMap<String,ApplicationInfo> appMap){
		db=getWritableDatabase();
		db.beginTransaction();
		try {
			Set<Entry<String, ApplicationInfo>> data=appMap.entrySet();
			for (Entry<String, ApplicationInfo> entry : data) {
				ApplicationInfo info=entry.getValue();
				ContentValues cv=new ContentValues(); 
				cv.put(APPNAME, info.getmName());
				cv.put(MINST, info.getmInst());
				cv.put(FREQUENCY, 0);
				cv.put(PACKAGENAME, info.getmPack());
				db.insert("appsort", null, cv);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();

		}
		db.close();
	}
	
	public int deleteAppSort(String packagename){
		int count=0;
		db=getWritableDatabase();
		count = db.delete("appsort", PACKAGENAME + "= ?", new String[]{packagename});
		db.close();
		return count;
	}
	
	public void updateFrequency(String packagename){
		db=getWritableDatabase();
		String addFrequency="update appsort set frequency = frequency+1 where packagename=?;";
		db.execSQL(addFrequency,new Object[]{packagename});
	}
		
	public ArrayList<AppSort> getAllAppFromSort(String condition,String order){
		ArrayList<AppSort> appList=new ArrayList<AppSort>();
		db=getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from appsort order by  "+condition +" "+order, null);
		while(cursor.moveToNext()){
			AppSort app=new AppSort();
			app.set_id(cursor.getInt(cursor.getColumnIndex(ID)));
			app.setName(cursor.getString(cursor.getColumnIndex(APPNAME)));
			app.setmInst(cursor.getLong(cursor.getColumnIndex(MINST)));
			app.setFrequency(cursor.getInt(cursor.getColumnIndex(FREQUENCY)));
			app.setPackagename(cursor.getString(cursor.getColumnIndex(PACKAGENAME)));
			appList.add(app);
		}
		cursor.close();
		db.close();
		return appList;
	}
	
}
