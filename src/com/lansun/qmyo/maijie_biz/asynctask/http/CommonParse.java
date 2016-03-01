package com.lansun.qmyo.maijie_biz.asynctask.http;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import com.lansun.qmyo.maijie_biz.log.FrameLog;

/**
 * 公共解析
 * @author  Yeun.Zhang
 *
 */
public class CommonParse {

	private static String TAG = "CommonParse";
	public static final String STATUS_KEY = "s"; // 
	public static final String DATA_KEY = "d";
	
	/** 1代表返回结果成功 */
	public static final int RQST_SUCCESS = 1;
	/** 0代表返回结果失败 */
	public static final int RQST_FAIL = 0;
	
	
//	public static TaskResult parseKlHttpResponse(HttpResponse response) {
//		if (response != null) {
//			try {
//				String res = EntityUtils.toString(response.getEntity());
//				JSONObject responseJson = new JSONObject(res);
//				return parseKlHttpJsonResponse(responseJson);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (JSONException e) {
//				e.printStackTrace();
//			} catch (HttpException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
//	
//	public static TaskResult parseDogHttpJsonResponse(JSONObject json) throws JSONException{
//		
//		TaskResult result = new TaskResult();
//		if (DOG_STATUSVALUE_SUCCESS == json.getInt(DOG_STATUS)) {
//			result.result = json.getJSONObject(DOG_RESULT);
//			result.status = TaskResultStatus.OK;
//		} else {
//			result.result = json.getString(DOG_RESULT);
//			result.status = TaskResultStatus.FAILED;
//		}
//
//		return result;
//	}
	
	public static TaskResult parseCarHttpJsonResponse(JSONObject json) throws JSONException, HttpException{
		FrameLog.d(TAG, json.toString());
		
		int returnCode = json.getInt(STATUS_KEY);
		TaskResult result;

		switch (returnCode) {
		case RQST_SUCCESS:
			result = new TaskResult();
			if (json.has(DATA_KEY)) {
//				result.result = json.getJSONObject(DATA_KEY);
				result.result = json;//原封不动的包装返回
			}
			result.status = TaskResultStatus.OK;
			return result;
		case RQST_FAIL:
			result = new TaskResult();
			if (json.has(DATA_KEY)) {
//				result.result = json.getJSONObject(DATA_KEY);
				result.result = json;
			}
			result.status = TaskResultStatus.FAILED;
			return result;
		default:
			throw new HttpException("数据请求失败");
		}
	}
}
