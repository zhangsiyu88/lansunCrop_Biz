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
	
	public DefaultSqliteProvider(Context context) {
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

		
		//下面是建立具体的创建新门店表格的sql代码
		StringBuilder sql_store = new StringBuilder();
		sql_store.append("CREATE TABLE ");
		sql_store.append(DbInfos.NewStoreTableField.TB_NAME);
		sql_store.append(" ( ");
		sql_store.append(DbInfos.NewStoreTableField._ID);
		sql_store.append(" integer primary key autoincrement, ");
		sql_store.append(DbInfos.NewStoreTableField.STORE_NAME);
		sql_store.append(" TEXT , ");
		sql_store.append(DbInfos.NewStoreTableField.STORE_ADDRESS);
		sql_store.append(" TEXT , ");
		sql_store.append(DbInfos.NewStoreTableField.STORE_ADDRESS_PLUS);
		sql_store.append(" TEXT , ");
		sql_store.append(DbInfos.NewStoreTableField.CITY);
		sql_store.append(" TEXT , ");
		sql_store.append(DbInfos.NewStoreTableField.BIZ_AREA);
		sql_store.append(" TEXT , ");
		sql_store.append(DbInfos.NewStoreTableField.STORE_EXPAND1);
		sql_store.append(" TEXT , ");
		sql_store.append(DbInfos.NewStoreTableField.STORE_EXPAND2);
		sql_store.append(" TEXT , ");
		sql_store.append(DbInfos.NewStoreTableField.STORE_EXPAND3);
		sql_store.append(" TEXT )");
		
		
		StringBuilder sql_activity_release = new StringBuilder();
		sql_activity_release.append("CREATE TABLE ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.TB_NAME);
		sql_activity_release.append(" ( ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField._ID);
		sql_activity_release.append(" integer primary key autoincrement, ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_NAME);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_DEGREE);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_CLASSFICATION);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_TAG);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_TITLE);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_DETAIL);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_RULES);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.PICTURE_PATH);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_DURATION);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_START);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_END);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_EXPAND1);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_EXPAND2);
		sql_activity_release.append(" TEXT , ");
		sql_activity_release.append(DbInfos.ReleaseActivityTableField.ACTIVITY_EXPAND3);
		sql_activity_release.append(" TEXT )");
		
		
		
		
		
		
		
		
		
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
		
		
		sqls.add(sql_activity_release.toString());
		sqls.add(sql_store.toString());
		
		System.out.println(sqls.get(0));
		System.out.println(sqls.get(1));
//		sqls.add(sql_w.toString());//
//		sqls.add(sql_p.toString());//photo
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
