package com.lansun.qmyo.maijie_biz.asynctask.http;
/**
 * Task执行的结果
 * 
 * @author Yeun.zhang
 * 
 */
public enum TaskResultStatus {
	OK,
	FAILED, 
	NET_ERROR,
	HTTP_ERROR,
	JSON_ERROR,
	IO_ERROR,
}