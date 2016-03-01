package com.lansun.qmyo.maijie_biz.asynctask.download.http.exception;

public class StatusCodeError extends Throwable
{
  private static final long serialVersionUID = 0x3ed86da5644ff873L;
  private int mStatusCode = -1;

  public StatusCodeError(int statusCode) {
    super("状态码错误，Http返回的状态码为：" + statusCode);
    this.mStatusCode = statusCode;
  }

  public int getStatusCode() {
    return this.mStatusCode;
  }
}
