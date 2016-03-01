package com.lansun.qmyo.maijie_biz.asynctask.http;

/**
 * 网络请求基类
 * @author Yeun.zhang
 */
public abstract class BaseTask {

	protected TaskListener mListener = null;

	// 便于区分哪一个task
	private int arg;

	private Object mData;

	public void setArg(int arg) {
		this.arg = arg;
	}

	public int getArg() {
		return arg;
	}

	public Object getData() {
		return mData;
	}

	public void setData(Object data) {
		mData = data;
	}

	public void setListener(TaskListener taskListener) {
		mListener = taskListener;
	}

	public TaskListener getListener() {
		return mListener;
	}

	protected void onCancelled() {
		if (mListener != null) {
			mListener.onCancelled(this);
		}
	}

	protected void onPostExecute(TaskResult result) {
		if (mListener != null) {
			mListener.onPostExecute(this, result);
		}
	}

	protected void onPreExecute() {
		if (mListener != null) {
			mListener.onPreExecute(this);
		}
	}

	protected void onProgressUpdate(Object... values) {
		if (mListener != null) {
			if (values != null && values.length > 0) {
				mListener.onProgressUpdate(this, values[0]);
			}
		}
	}
}
