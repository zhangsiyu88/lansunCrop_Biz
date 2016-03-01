package com.lansun.qmyo.maijie_biz.asynctask;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import com.lansun.qmyo.maijie_biz.asynctask.http.CommonHttpTask;
import com.lansun.qmyo.maijie_biz.asynctask.http.CommonParse;
import com.lansun.qmyo.maijie_biz.asynctask.http.TaskResult;
import com.lansun.qmyo.maijie_biz.utils.TArrayList;

/**
 *  基础任务类
 * @author  Yeun.Zhang
 *
 */
public class MaijieBizHttpTask extends CommonHttpTask{

	public MaijieBizHttpTask(String strUrl) {
		super(strUrl);
	}
	
	public MaijieBizHttpTask(String url, TArrayList paramsList, int type) {
		super(url, paramsList, type);
	}

	/**
	 * 实现 CommonHttpTask 中的 protected abstract  类型的_parseResponse(_JSONObject)
	 */
	@Override
	protected TaskResult _parseResponse(JSONObject json) throws JSONException, HttpException {
		//返回的是处理后，为json.result的字符串
		return CommonParse.parseCarHttpJsonResponse(json);
	}
	
}
