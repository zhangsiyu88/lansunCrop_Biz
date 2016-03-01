package com.lansun.qmyo.maijie_biz.asynctask.download.http;


/**
 * http download 通知接口
 * @author Yeun.zhang
 *
 */
public interface IHttpNotify {
	/**
	 * 开始下载的回调
	 */
	void onStart(long totalSize);
	
	/**
	 * 下载进度回调
	 */
	void onProgress(long currSize);
	
	/**
	 * 下载完成回调
	 */
	void onFinished(String path);
	
	/**
	 * 下载失败的回调
	 */
	void onFail(NetworkError error);
	
	/**
	 * 主动取消（暂停）下载的回调
	 * @param path：文件路径
	 * @param totalSize：要下载的数据总大小
	 * @param curSize:当前下载大小
	 */
	void onCancel(String path,long totalSize,long curSize);
}
