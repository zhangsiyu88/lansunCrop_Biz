package com.lansun.qmyo.maijie_biz.db;

/**
 * 
 * @author  Yeun.Zhang
 *
 */
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
	
	public static class NewStoreTableField {
		/** 表名*/
		public static final String TB_NAME = "Store_Create";
		
		/** ID自增长，不需要插入*/
		public static final String _ID = "_id";
		
		/** 名称*/
		public static final String STORE_NAME = "_name";
		
		/** 地址*/
		public static final String STORE_ADDRESS = "_address";
		
		/** 地址备注*/
		public static final String STORE_ADDRESS_PLUS = "_address_plus";
		
		/** 编辑所属城市*/
		public static final String CITY = "_city";
		
		/** 编辑商圈位置*/
		public static final String BIZ_AREA = "_biz_area";
		
		/** 预留字段*/
		public static final String STORE_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String STORE_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String STORE_EXPAND3 = "expend3";
	}
	
	public static class ReleaseActivityTableField {
		/** 表名*/
		public static final String TB_NAME = "Activity_Release";
		
		/** ID自增长，不需要插入*/
		public static final String _ID = "_id";
		
		/** 名称*/
		public static final String ACTIVITY_NAME = "_name";
		
		/** 级别*/
		public static final String ACTIVITY_DEGREE = "_degree";
		
		/** 分类*/
		public static final String ACTIVITY_CLASSFICATION = "_classfication";
		
		/** 标签*/
		public static final String ACTIVITY_TAG = "_tag";
		
		/** 标题*/
		public static final String ACTIVITY_TITLE = "_title";
		
		/** 详情描述 */
		public static final String ACTIVITY_DETAIL = "_detail";
		
		/** 使用规则 */
		public static final String ACTIVITY_RULES = "_rules";
		
		/** 活动图片的path */
		public static final String PICTURE_PATH = "_picture_path";
		
		/** 活动期限*/
		public static final String  ACTIVITY_DURATION = "_duration";
		
		/** 活动开始时间*/
		public static final String  ACTIVITY_START = "_start";
		
		/** 活动结束时间*/
		public static final String  ACTIVITY_END = "_end";
		
		/** 预留字段*/
		public static final String ACTIVITY_EXPAND1 = "expend1";
		
		/** 预留字段*/
		public static final String ACTIVITY_EXPAND2 = "expend2";
		
		/** 预留字段*/
		public static final String ACTIVITY_EXPAND3 = "expend3";
	}
	
	
	
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
