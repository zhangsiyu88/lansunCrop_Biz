package com.lansun.qmyo.maijie_biz.asynctask;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.lansun.qmyo.maijie_biz.asynctask.http.CommonHttpTask;
import com.lansun.qmyo.maijie_biz.asynctask.http.CommonParse;
import com.lansun.qmyo.maijie_biz.asynctask.http.TaskResult;
import com.lansun.qmyo.maijie_biz.asynctask.http.TaskResultStatus;
import com.lansun.qmyo.maijie_biz.log.FrameLog;
import com.lansun.qmyo.maijie_biz.utils.TArrayList;

/**
 * 大数据的网络请求
 * 由于父类BaseHttpTask使用volley框架，只能发送比较小的数据量，所以这里用HttpUrlConnection配合AsyncTask方式实现发送大数据量的请求
 * 
 * !!!不支持取消任务!!!
 * @author  Yeun.Zhang
 */
public class LargeDataHttpTask extends CommonHttpTask {

	protected final static String TAG = "LargeDataHttpTask";
	
	public LargeDataHttpTask(String url, TArrayList paramsList, int type) {
		super(url, paramsList, type);
	}

	@Override
	protected TaskResult _parseResponse(JSONObject json) throws JSONException, HttpException {
		return CommonParse.parseCarHttpJsonResponse(json);
	}

	@Override
	public void execute() {
		new LargeDataTask().execute();
	}
	
	private class LargeDataTask extends AsyncTask<Void, Integer, JSONObject>{
		
		@Override
		protected JSONObject doInBackground(Void... params) {
			
			return syncGetLargeData(mUrl);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isRunning = true;
			FrameLog.d(TAG, "step preExecute");
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			TaskResult tr = new TaskResult();
			if( result == null ){
				tr.status = TaskResultStatus.HTTP_ERROR;
			}else{
				try {
					tr = _parseResponse(result);
				} catch (JSONException e) {
					e.printStackTrace();
					tr.status = TaskResultStatus.JSON_ERROR;
				} catch (HttpException e) {
					e.printStackTrace();
					tr.status = TaskResultStatus.HTTP_ERROR;
				}
			}
			LargeDataHttpTask.this.onPostExecute(tr);
			isRunning = false;
		}
	}
}
