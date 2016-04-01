package com.lansun.qmyo.maijie_biz.db;

public class DbInfos {

	public static final String TAG = "DbInfos";
	
	/**
	 * 定义数据库的名称DB_NAME
	 */
	public static final String DB_NAME = "MaiJieBizInfos.db";
	/**
	 * 数据库版本号version
	 */
	public static final int VERSION = 1;
	
	public static class TextTableField {
		/** 表名*/
		public static final String TB_NAME = "Word_Record";
		
		/** ID自增长，不需要插入*/
		public static final String _ID = "_id";
		/** 文字记录标题*/
		public static final String TITLE = "title";
		
		/** 内容*/
		public static final String CONTENT = "content";
		
		/** 编辑时间*/
		public static final String TIME = "time";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND3 = "expend3";
		
	}
	public static class PhotoTableField {
		/** 表名*/
		public static final String TB_NAME = "Photo_Record";
		
		/** ID自增长，不需要插入*/
		public static final String _ID = "_id";
		
		/** 标题*/
		public static final String TITLE = "title";
		
		/** 图片文件*/
		public static final String FILE = "file";
		
		/** 大小*/
		public static final String SIZE = "size";
		
		/** 编辑时间*/
		public static final String TIME = "time";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND3 = "expend3";
	}

	
	

}
