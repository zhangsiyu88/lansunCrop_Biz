package com.lansun.qmyo.maijie_biz.asynctask.download.http;

/**
 * 网络数据监听接口，带有主动"取消"  和    文件读写错误造成的被动"取消"接口
 * @author Yeun.zhang
 *
 */
public abstract class CancelHttpListener implements HttpProviderListener {
	protected String mFilePath ;
	private boolean mIsCancel = false;

	/*
	 * 网络请求开始接口
	 * 返回false 表示终止网络请求
	 * (non-Javadoc)
	 * @see cn.ibaby.frame.http.HttpProviderListener#onStart(long)
	 */
	public boolean onStart(long contentLen) {
		return !this.mIsCancel;
	}

	/*
	 * 重写接口，获取网络数据进度
	 * 返回false表示终止网络请求
	 * (non-Javadoc)
	 * @see cn.ibaby.frame.http.HttpProviderListener#onAdvance(byte[], int, int)
	 */
	public boolean onAdvance(byte[] buf, int start, int len) {
		return !this.mIsCancel;
	}

	public void onFail(NetworkResult result, Throwable e) {
			
		if (this.mIsCancel) {
			//手动取消不需要回调onHttpFail
			onCancel();
			return;
		}
		onHttpFail(result, e);
	}

	/*
	 * result 中包含上层数据接收异常return false引起的网络请求自动“取消”引起的失败
	 * 用户主动“取消”不会回调失败接口
	 */
	public abstract void onHttpFail(
			NetworkResult result,
			Throwable paramThrowable);
	
	/**
	 * 主动取消接口
	 */
	public abstract void onCancel();

	public void cancel(String filePath) {
		if(filePath.equals(mFilePath)) {
			this.mIsCancel = true;
		}
	}

	public boolean isCancel() {
		return this.mIsCancel;
	}
}