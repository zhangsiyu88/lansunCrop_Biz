package com.lansun.qmyo.maijie_biz.asynctask.download.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.lansun.qmyo.maijie_biz.log.FrameLog;
import com.lansun.qmyo.maijie_biz.utils.FileUtils;

/**
 * 通用网络数据下载监听器,支持取消请求，断点续传
 * @author Yeun.zhang
 *
 */
public  class DownloadHttpListener extends CancelHttpListener{
	private static final String TAG = "DownloadHttpListener";
	
	//临时文件后缀名
	public  final String TEMP_EXT = ".temp";
	public  final String CACHE_EXT = ".apk";
	int mLastProgress = 0;
	
	//观察者回调
	private IHttpNotify mHttpNotify;
	
	
	//FileOutputStream mFos = null;
	RandomAccessFile mAccessFile = null;
	private long mContentLen = -1L;
	private long mPos = 0L;

	//暂未实现
	private boolean mIsSupportMulThread = false; //是否支持多线程
	private int MAX_THREAD_NUM = 3;
	private int mThreadNum = 1;
	
	public DownloadHttpListener(String filePath,IHttpNotify httpNotify) {
		super();
		this.mFilePath = filePath;
		mHttpNotify = httpNotify;
	}
	public void setIHttpNotify(IHttpNotify httpNotify){
		mHttpNotify = httpNotify;
	}
	public void setFilePath(String path){
		this.mFilePath = path;
	}
	
	public void setFileSize(long contentLen) {
		mContentLen = contentLen;
	}
	
	//当前文件指针位置，即已经下载的大小
	public void setCurBreakPos(long pos){
		this.mPos = pos;
	}
	
	public long getCurBreakPos(){
		return this.mPos;
	}
	
	@Override
	public void onFinished() {
		closeStream();
		// 下载成功,修改临时文件为当前文件     
		File file = new File(mFilePath + TEMP_EXT);                               
		boolean succ = false;
        if (file.exists()) {
            File target = new File(mFilePath + CACHE_EXT);
            succ = file.renameTo(target);
        }
        if (mHttpNotify != null) {
    		mHttpNotify.onFinished(succ? (mFilePath+CACHE_EXT):(mFilePath+TEMP_EXT));
		}
	}
	
	/**
	 * 主动取消的回调
	 * 用于保存当前已经下载的数据进度
	 */
	@Override
	public void onCancel() {
		closeStream();
		if (mHttpNotify != null) {       	
    		mHttpNotify.onCancel(mFilePath, mContentLen, mPos);
		}
	}
	
	@Override
	public void onHttpFail(NetworkResult arg0, Throwable arg1) {
		closeStream();
		File file = new File(mFilePath + TEMP_EXT);
		file.delete();
		
		if (arg0 != null) {
			if (arg0.mNetworkError == NetworkError.CANCEL) {
				//此处为http模块收到非主动cancel的异常引起的false，已经发送过一次了，不需要再重复发送
//				if (mHttpNotify != null) {       	
//		    		mHttpNotify.onFail(NetworkError.CANCEL);
//				}
			}else {
				if (mHttpNotify != null) {       	
		    		mHttpNotify.onFail(arg0.mNetworkError);
				}
			}
		}
	}
	
	@Override
	public boolean onStart(long contentLen) {	
		
		String tmpPath = mFilePath + TEMP_EXT;
		
		FrameLog.d(TAG, tmpPath);
		
		File cacheFile = new File(tmpPath);
		File target = new File(mFilePath + CACHE_EXT);
		
		if(cacheFile.exists()) {
			cacheFile.delete();
		}
		if(target.exists()) {
			target.delete();
		}
		
		//检测文件是否存在 
//		if (!cacheFile.exists()) {
            try {
            	long length = contentLen<= 0 ? mContentLen : contentLen;
                FileUtils.createEmptyFile(cacheFile.getAbsolutePath(), length); 
                mPos = 0L; 
            } catch (Exception e) {
            	if (mHttpNotify != null) {       	
    	    		mHttpNotify.onFail(NetworkError.FAIL_IO_ERROR);
    			}
                return false;
            }
//        }
		
		try {
				 mAccessFile = new RandomAccessFile(cacheFile,"rw");
				 mAccessFile.seek(mPos);
				 FrameLog.i("下载", "开始大小");

        } catch (FileNotFoundException e) {
        	if (mHttpNotify != null) {       	
	    		mHttpNotify.onFail(NetworkError.FILE_NOT_FOUND);
			}
            return false;
        }catch (IOException e) {
        	if (mHttpNotify != null) {       	
	    		mHttpNotify.onFail(NetworkError.FAIL_IO_ERROR);
			}
            return false;
		}
//        mContentLen = contentLen+mPos;//包含断点续传
        
        if (mHttpNotify != null) {       	
    		mHttpNotify.onStart(mContentLen);
		}
		
		//返回父类的处理
		return super.onStart(mContentLen);
	}

	@Override
	public boolean onAdvance(byte[] buffer, int start, int len) {
		
		try {
				mAccessFile.write(buffer, start, len);
			
		} catch (IOException e) {
			if (mHttpNotify != null) {       	
	    		mHttpNotify.onFail(NetworkError.FAIL_IO_ERROR);
			}
			return false;
		}

		this.mPos += len;
		int progress = (int)(mContentLen > 0 ? mPos * 100/ mContentLen: 0);
		
		//FrameLog.i("下载", "onAdvance 已下载大小："+mPos+"--"+mContentLen);
		if (mLastProgress == progress) {
			return super.onAdvance(buffer, start, len);
		}
		
		mLastProgress = progress;	
		if (mHttpNotify != null) {       	
    		mHttpNotify.onProgress(mPos);
		}
		return super.onAdvance(buffer, start, len);
	}
	
	private void closeStream(){
		if (mAccessFile != null) {
			try {
				mAccessFile.close();
				mAccessFile = null;
			} catch (IOException e) {
				mAccessFile = null;
			}
		}
	}
}
