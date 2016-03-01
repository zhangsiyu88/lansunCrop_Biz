package com.lansun.qmyo.maijie_biz.asynctask.download.http;

/**
 * HttpProviderListener网络访问的接口
 * 
 * @author  Yeun.Zhang
 *
 */
public abstract interface HttpProviderListener
{
  public abstract boolean onStart(long contentLen);

  public abstract boolean onAdvance(byte[] paramArrayOfByte, int start, int len);

  public abstract void onFinished();

  public abstract void onFail(NetworkResult paramSimpleNetworkResult, Throwable paramThrowable);
  
}
