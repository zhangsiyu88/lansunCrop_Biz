package com.lansun.qmyo.maijie_biz.db;

import android.database.sqlite.SQLiteDatabase;

public interface SqliteProvider {

	/**
	 * 获取数据库对象
	 * @return
	 */
	public SQLiteDatabase getDataBase();
	
	/**
	 * 关闭数据库
	 */
	public void closeDb();
}
