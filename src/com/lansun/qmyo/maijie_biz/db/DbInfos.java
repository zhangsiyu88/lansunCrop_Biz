package com.lansun.qmyo.maijie_biz.db;

public class DbInfos {

	public static final String TAG = "DbInfos";
	
	/**
	 * 定义数据库的名称DB_NAME
	 */
	public static final String DB_NAME = "GeoTravelDB.db";
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
		
		/** 经度*/
		public static final String LNG = "lng";
		
		/** 纬度*/
		public static final String LAT = "lat";
		
		/** 预留字段*/
		public static final String WORD_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String WORD_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String WORD_EXPAND3 = "expend3";
		
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
		
		/** 经度*/
		public static final String LNG = "lng";
		
		/** 纬度*/
		public static final String LAT = "lat";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String PHOTO_EXPAND3 = "expend3";
	}
	public static class AudioTableField {
		/** 表名*/
		public static final String TB_NAME = "Audio_Record";
		
		/** ID自增长，不需要插入*/
		public static final String _ID = "_id";
		
		/** 标题*/
		public static final String TITLE = "title";
		
		/** 存储本地路径*/
		public static final String FILE = "file";
		
		/** 大小*/
		public static final String SIZE = "size";
		
		/** 编辑时间*/
		public static final String TIME = "time";
		
		/** 经度*/
		public static final String LNG = "lng";
		
		/** 纬度*/
		public static final String LAT = "lat";
		
		/** 预留字段*/
		public static final String AUDIO_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String AUDIO_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String AUDIO_EXPAND3 = "expend3";
	}
	public static class VedioTableField {
		/** 表名*/
		public static final String TB_NAME = "Vedio_Record";
		
		/** ID自增长，不需要插入*/
		public static final String _ID = "_id";
		
		/** 标题*/
		public static final String TITLE = "title";
		
		/** 存储本地路径*/
		public static final String FILE = "file";
		
		/** 大小*/
		public static final String SIZE = "size";
		
		/** 编辑时间*/
		public static final String TIME = "time";
		
		/** 经度*/
		public static final String LNG = "lng";
		
		/** 纬度*/
		public static final String LAT = "lat";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND3 = "expend3";
	}
	
	public static class LocationTableField {
		/** 表名*/
		public static final String TB_NAME = "Location_Record";
		
		/** ID自增长，不需要插入*/
		public static final String _ID = "_id";
		
		/** 经度*/
		public static final String LNG = "lng";
		
		/** 纬度*/
		public static final String LAT = "lat";
		
		/** 编辑时间*/
		public static final String TIME = "time";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND3 = "expend3";
	}
	
	
	
	
	public static class CityTableField {
		/** 表名*/
		public static final String TB_NAME = "City_Code";
		
		/** ID自增长，不需要插入*/
		public static final String _ID = "_id";
		
		/** 城市名称*/
		public static final String NAME = "name";
		
		/** 城市编码*/
		public static final String CODE = "code";
		
		/** 省编码*/
		public static final String PCODE = "pcode";
		
		/** 首字母*/
		public static final String FIRSTLETTER = "firstletter";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String VEDIO_EXPAND3 = "expend3";
	}
}
