package com.lansun.qmyo.maijie_biz.asynctask.download;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.lansun.qmyo.maijie_biz.asynctask.download.http.DownloadHttpListener;
import com.lansun.qmyo.maijie_biz.asynctask.download.http.IHttpNotify;
import com.lansun.qmyo.maijie_biz.asynctask.download.threadpool.ThreadPoolAppTask;
import com.lansun.qmyo.maijie_biz.asynctask.download.threadpool.ThreadPoolManager;
import com.lansun.qmyo.maijie_biz.bean.DownloadItem;
import com.lansun.qmyo.maijie_biz.dirmanager.DirType;
import com.lansun.qmyo.maijie_biz.uisupport.other.UiUtil;
import com.lansun.qmyo.maijie_biz.utils.AppContext;
import com.lansun.qmyo.maijie_biz.utils.FileUtils;


public class AppDownloader {
	
	private ArrayList<DownloadHttpListener> mDownloadListenerList;
	private static AppDownloader _instance = null;
	private Map<String, Integer> mRunningTask;
	private ThreadPoolManager mThreadPoolManager;
	
	private AppDownloader(){
		mDownloadListenerList = new ArrayList<DownloadHttpListener>();
		mRunningTask = Collections.synchronizedMap(new HashMap<String, Integer>());
		mThreadPoolManager = new ThreadPoolManager(3);
	}
	public static AppDownloader getInstance(){
		if(_instance == null)
			_instance = new AppDownloader();
		return _instance;
	}
	
	public void addNewDownloadTask(DownloadItem item, IHttpNotify listener) {
		
		//CallbackListener: implements IHttpNotify 
		IHttpNotify callback = listener == null ? new CallbackListener(item, true) : listener;
		
		if(!mRunningTask.containsKey(item.url)) {
			
			DownloadHttpListener downListener = new DownloadHttpListener(getFilePathByUrl(item.url), callback);
			downListener.setFileSize(item.totleSize);
			
			ThreadPoolAppTask task = new ThreadPoolAppTask(item.url, downListener);
			mThreadPoolManager.execute(task);
			
			mRunningTask.put(item.url, item.url.hashCode());
			mDownloadListenerList.add(downListener);
		} else {
			UiUtil.sToast("任务正在下载中");
		}
	}
	
	private String getFilePathByUrl(String url) {
		return FileUtils.getFilePathByUrl(url, AppContext.getDirManager().getDirPath(DirType.DOWNLOAD));
	}
	
	public Map<String, Integer> getRunningTask() {
		return mRunningTask;
	}
	
	public void cancelDownloadTask(String url) {
		String path = FileUtils.getFilePathByUrl(url, AppContext.getDirManager().getDirPath(DirType.DOWNLOAD));
		if(mDownloadListenerList != null && mDownloadListenerList.size() > 0) {
			for(DownloadHttpListener listener : mDownloadListenerList) {
				listener.cancel(path);
			}
		}
	}
	
	public void release() {
		mRunningTask.clear();
		mThreadPoolManager.shutDown();
	}
}
