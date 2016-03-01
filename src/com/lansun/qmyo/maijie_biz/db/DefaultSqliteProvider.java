package com.lansun.qmyo.maijie_biz.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lansun.qmyo.maijie_biz.log.FrameLog;

public class DefaultSqliteProvider extends SQLiteOpenHelper implements SqliteProvider {
	private static final String TAG = "DefaultSqliteProvider";
	private final static int VERSION = 1;
	
	private SQLiteDatabase mDatabase;
	
	protected DefaultSqliteProvider(Context context) {
		super(context, DbInfos.DB_NAME, null, VERSION);
		mDatabase = getWritableDatabase();
	}
	
	@Override
	public SQLiteDatabase getDataBase(){
		return mDatabase;
	}
	
	@Override
	public void closeDb(){
		try{
			if(mDatabase != null && mDatabase.isOpen()){
				mDatabase.close();
				mDatabase = null;
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
	
	private List<String> getCreateSqlList(){
		List<String> sqls = new ArrayList<String>();
		
		StringBuilder sql_w = new StringBuilder();
		sql_w.append("CREATE TABLE ");
		sql_w.append(DbInfos.TextTableField.TB_NAME);
		sql_w.append(" ( ");
		sql_w.append(DbInfos.TextTableField._ID);
		sql_w.append(" integer primary key autoincrement, ");
		sql_w.append(DbInfos.TextTableField.TITLE);
		sql_w.append(" TEXT , ");
		sql_w.append(DbInfos.TextTableField.CONTENT);
		sql_w.append(" TEXT , ");
		sql_w.append(DbInfos.TextTableField.TIME);
		sql_w.append(" TEXT , ");
		sql_w.append(DbInfos.TextTableField.LAT);
		sql_w.append(" TEXT , ");
		sql_w.append(DbInfos.TextTableField.LNG);
		sql_w.append(" TEXT , ");
		sql_w.append(DbInfos.TextTableField.WORD_EXPAND1);
		sql_w.append(" TEXT , ");
		sql_w.append(DbInfos.TextTableField.WORD_EXPAND2);
		sql_w.append(" TEXT , ");
		sql_w.append(DbInfos.TextTableField.WORD_EXPAND3);
		sql_w.append(" TEXT )");
		
		StringBuilder sql_p = new StringBuilder();
		sql_p.append("CREATE TABLE ");
		sql_p.append(DbInfos.PhotoTableField.TB_NAME);
		sql_p.append(" ( ");
		sql_p.append(DbInfos.PhotoTableField._ID);
		sql_p.append(" integer primary key autoincrement, ");
		sql_p.append(DbInfos.PhotoTableField.TITLE);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.FILE);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.SIZE);
		sql_p.append(" Integer , ");
		sql_p.append(DbInfos.PhotoTableField.TIME);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.LAT);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.LNG);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.PHOTO_EXPAND1);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.PHOTO_EXPAND2);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.PHOTO_EXPAND3);
		sql_p.append(" TEXT )");
		
		StringBuilder sql_a = new StringBuilder();
		sql_a.append("CREATE TABLE ");
		sql_a.append(DbInfos.AudioTableField.TB_NAME);
		sql_a.append(" ( ");
		sql_a.append(DbInfos.AudioTableField._ID);
		sql_a.append(" integer primary key autoincrement, ");
		sql_a.append(DbInfos.AudioTableField.TITLE);
		sql_a.append(" TEXT , ");
		sql_a.append(DbInfos.AudioTableField.FILE);
		sql_a.append(" TEXT , ");
		sql_a.append(DbInfos.AudioTableField.SIZE);
		sql_a.append(" Integer , ");
		sql_a.append(DbInfos.AudioTableField.TIME);
		sql_a.append(" TEXT , ");
		sql_a.append(DbInfos.AudioTableField.LAT);
		sql_a.append(" TEXT , ");
		sql_a.append(DbInfos.AudioTableField.LNG);
		sql_a.append(" TEXT , ");
		sql_a.append(DbInfos.AudioTableField.AUDIO_EXPAND1);
		sql_a.append(" TEXT , ");
		sql_a.append(DbInfos.AudioTableField.AUDIO_EXPAND2);
		sql_a.append(" TEXT , ");
		sql_a.append(DbInfos.AudioTableField.AUDIO_EXPAND3);
		sql_a.append(" TEXT )");
		
		StringBuilder sql_v = new StringBuilder();
		sql_v.append("CREATE TABLE ");
		sql_v.append(DbInfos.VedioTableField.TB_NAME);
		sql_v.append(" ( ");
		sql_v.append(DbInfos.VedioTableField._ID);
		sql_v.append(" integer primary key autoincrement, ");
		sql_v.append(DbInfos.VedioTableField.TITLE);
		sql_v.append(" TEXT , ");
		sql_v.append(DbInfos.VedioTableField.FILE);
		sql_v.append(" TEXT , ");
		sql_v.append(DbInfos.VedioTableField.SIZE);
		sql_v.append(" Integer , ");
		sql_v.append(DbInfos.VedioTableField.TIME);
		sql_v.append(" TEXT , ");
		sql_v.append(DbInfos.VedioTableField.LAT);
		sql_v.append(" TEXT , ");
		sql_v.append(DbInfos.VedioTableField.LNG);
		sql_v.append(" TEXT , ");
		sql_v.append(DbInfos.VedioTableField.VEDIO_EXPAND1);
		sql_v.append(" TEXT , ");
		sql_v.append(DbInfos.VedioTableField.VEDIO_EXPAND2);
		sql_v.append(" TEXT , ");
		sql_v.append(DbInfos.VedioTableField.VEDIO_EXPAND3);
		sql_v.append(" TEXT )");
		
		StringBuilder sql_l = new StringBuilder();
		sql_l.append("CREATE TABLE ");
		sql_l.append(DbInfos.LocationTableField.TB_NAME);
		sql_l.append(" ( ");
		sql_l.append(DbInfos.LocationTableField._ID);
		sql_l.append(" integer primary key autoincrement, ");
		sql_l.append(DbInfos.LocationTableField.LAT);
		sql_l.append(" TEXT , ");
		sql_l.append(DbInfos.LocationTableField.LNG);
		sql_l.append(" TEXT , ");
		sql_l.append(DbInfos.LocationTableField.TIME);
		sql_l.append(" TEXT , ");
		sql_l.append(DbInfos.LocationTableField.VEDIO_EXPAND1);
		sql_l.append(" TEXT , ");
		sql_l.append(DbInfos.LocationTableField.VEDIO_EXPAND2);
		sql_l.append(" TEXT , ");
		sql_l.append(DbInfos.LocationTableField.VEDIO_EXPAND3);
		sql_l.append(" TEXT )");
		
		StringBuilder sql_city = new StringBuilder();
		sql_city.append("CREATE TABLE ");
		sql_city.append(DbInfos.CityTableField.TB_NAME);
		sql_city.append(" ( ");
		sql_city.append(DbInfos.CityTableField._ID);
		sql_city.append(" integer primary key autoincrement, ");
		sql_city.append(DbInfos.CityTableField.NAME);
		sql_city.append(" TEXT , ");
		sql_city.append(DbInfos.CityTableField.CODE);
		sql_city.append(" TEXT , ");
		sql_city.append(DbInfos.CityTableField.FIRSTLETTER);
		sql_city.append(" TEXT , ");
		sql_city.append(DbInfos.CityTableField.PCODE);
		sql_city.append(" TEXT , ");
		sql_city.append(DbInfos.CityTableField.VEDIO_EXPAND1);
		sql_city.append(" TEXT , ");
		sql_city.append(DbInfos.CityTableField.VEDIO_EXPAND2);
		sql_city.append(" TEXT , ");
		sql_city.append(DbInfos.CityTableField.VEDIO_EXPAND3);
		sql_city.append(" TEXT )");
		
		sqls.add(sql_w.toString());
		sqls.add(sql_p.toString());
		sqls.add(sql_a.toString());
		sqls.add(sql_v.toString());
		sqls.add(sql_l.toString());
		sqls.add(sql_city.toString());
		
		return sqls;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		List<String> sqls = getCreateSqlList();
		if(sqls != null && sqls.size() > 0){
			for(String sql : sqls){
				try{
					FrameLog.d(TAG, sql);
					db.execSQL(sql);
				}catch(Throwable e){}
			}
		}
		
	}
	
//	private void insertCityData() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("insert ")
//		
//	}

	/**
	 * 版本升级
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
