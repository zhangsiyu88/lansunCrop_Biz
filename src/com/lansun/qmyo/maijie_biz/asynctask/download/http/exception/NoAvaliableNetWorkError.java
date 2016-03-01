package com.lansun.qmyo.maijie_biz.asynctask.download.http.exception;

public class NoAvaliableNetWorkError extends Throwable
{
  private static final long serialVersionUID = 0x69f0f4719cd095f8L;

  public NoAvaliableNetWorkError()
  {
    super("没有可用网络");
  }
}
