package com.lansun.qmyo.maijie_biz.asynctask.http;


/**
 * 请求监听接口 一般和TaskListener结合使用
 * @author  Yeun.Zhang
 *
 */
public interface FetchListener {

	/** 正确返回结果 */
	public static int STATUS_OK = 0;
	/** 网络错误 */
	public static int STATUS_NET_ERROR = 1;
	/** 解析错误 */
	public static int STATUS_PARSER_ERROR = 2;
	
	// 单点登录 强制下线
	public static int StATUS_OFFLINE = 100;

	/**
	 * 请求发送前 对应 TaskListener的onPreExcute
	 */
	void onPreFetch();

	/**
	 * 请求返回后 对应 TaskListener的onPostExcute
	 * 
	 * @param status
	 *            返回状态
	 * @param result
	 *            返回值
	 */
	void onPostFetch(int status, Object... result);
}
