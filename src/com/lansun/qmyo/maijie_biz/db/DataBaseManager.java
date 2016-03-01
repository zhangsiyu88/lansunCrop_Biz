package com.lansun.qmyo.maijie_biz.db;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.lansun.qmyo.maijie_biz.dirmanager.DirType;
import com.lansun.qmyo.maijie_biz.utils.AppContext;
import com.lansun.qmyo.maijie_biz.utils.FileUtils;

/**
 * 核心数据库管理类,可同时管理多个数据库
 * @author  Yeun.Zhang
 *
 */
public class DataBaseManager {

	private HashMap<String, SqliteProvider> mSqliteHashMap;
	
	private static DataBaseManager _instance = null;
	private static Context mContext;
	
	private DataBaseManager(Context context){
		registDatabase(context);
	}
	
	public static void init(Context context){
		mContext = context;
		_instance = new DataBaseManager(context);
	}
	
	public synchronized static DataBaseManager getInstance(){
		if(_instance == null)
			_instance = new DataBaseManager(mContext);
		return _instance;
	}
	
	private void registDatabase(Context context){
		if(mSqliteHashMap == null)
			mSqliteHashMap = new HashMap<String, SqliteProvider>();
		mSqliteHashMap.clear();
		
		//注册默认数据库
//		mSqliteHashMap.put(DbInfos.DB_NAME, new DefaultSqliteProvider(context));
	}
	
	public void closeAllDb(){
		if(mSqliteHashMap == null)
			return;
		
		Iterator<String> keyIterator = mSqliteHashMap.keySet().iterator();
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			SqliteProvider provider = mSqliteHashMap.get(key);
			provider.closeDb();
		}
		_instance = null;
	}
	
	public SQLiteDatabase getDataBase(String key){
		if(mSqliteHashMap == null)
			return null;
		SqliteProvider provider = mSqliteHashMap.get(key);
		if(provider == null)
			return null;
		return provider.getDataBase();
	}
	
	public SQLiteDatabase getDataBase(){
		return getDataBase(DbInfos.DB_NAME);
	}
	
	
	public boolean  copyDatabaseToSdcard(String name){
		if(TextUtils.isEmpty(name)){
    		name = DbInfos.DB_NAME;
    	}
		File dbFile = mContext.getApplicationContext().getDatabasePath(name);
		if (dbFile.exists()) {
			File dest = new File(AppContext.getDirManager().getDirPath(DirType.TEMP), name);
			FileUtils.deleteFile(dest.getPath());
			return FileUtils.copyFile(dbFile, dest);
		}
    	return false;
	}
}
