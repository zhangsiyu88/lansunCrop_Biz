package com.lansun.qmyo.maijie_biz.asynctask.http;

/**
* 网络请求任务的监听器
* @author lruijun
* @date   2015-06-02
*/
public interface TaskListener {

	void onPreExecute(BaseTask task);

	void onPostExecute(BaseTask task, TaskResult result);
	
	void onProgressUpdate(BaseTask task, Object param);

	void onCancelled(BaseTask task);
}
