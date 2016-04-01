package com.lansun.qmyo.maijie_biz.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lansun.qmyo.maijie_biz.log.FrameLog;

/**
 * 创建数据库与数据库的操作对象SQLiteOpenHelper
 * 
 * @author  Yeun.Zhang
 *
 */
public class DefaultSqliteProvider extends SQLiteOpenHelper implements SqliteProvider {
	private static final String TAG = "DefaultSqliteProvider";
	private final static int VERSION = 1;
	
	private SQLiteDatabase mDatabase;
	
	protected DefaultSqliteProvider(Context context) {
		super(context, DbInfos.DB_NAME, null, VERSION);    //传null使用默认游标工厂
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
	
	
	/**
	 * 一上来就是执行创建Sql列表的操作
	 * @return
	 */
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
		sql_p.append(DbInfos.PhotoTableField.PHOTO_EXPAND1);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.PHOTO_EXPAND2);
		sql_p.append(" TEXT , ");
		sql_p.append(DbInfos.PhotoTableField.PHOTO_EXPAND3);
		sql_p.append(" TEXT )");
		

		sqls.add(sql_w.toString());//
		sqls.add(sql_p.toString());//photo
		return sqls;
	}

	
	/**
	 * 数据库创建
	 */
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
