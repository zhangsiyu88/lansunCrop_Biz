package com.lansun.qmyo.maijie_biz.asynctask.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import com.lansun.qmyo.maijie_biz.app.MaijieApp;
import com.lansun.qmyo.maijie_biz.log.FrameLog;
import com.lansun.qmyo.maijie_biz.utils.TArrayList;
import com.lansun.qmyo.maijie_biz.utils.UrlUtil;
import com.lansun.qmyo.maijie_biz.volley.DefaultRetryPolicy;
import com.lansun.qmyo.maijie_biz.volley.Request;
import com.lansun.qmyo.maijie_biz.volley.RequestQueue;
import com.lansun.qmyo.maijie_biz.volley.Response.ErrorListener;
import com.lansun.qmyo.maijie_biz.volley.Response.Listener;
import com.lansun.qmyo.maijie_biz.volley.VolleyError;
import com.lansun.qmyo.maijie_biz.volleyvolley.toolbox.RequestFuture;
import com.lansun.qmyo.maijie_biz.volleyvolley.toolbox.Volley;


/**
 * 执行网络请求的任务
 * @author  Yeun.Zhang
 *
 */
public abstract class CommonHttpTask extends BaseTask {
private static final String TAG = "CommonHttpTask";
	
	
	/** post请求 */
	public static final int POST = Request.Method.POST;
	/** get请求 */
	public static final int GET = Request.Method.GET;
	
	/** 请求的Url */
	protected String mUrl;
	/** 请求参数 */
	protected Map<String, String> httpParams;

	/** 当前的请求类型 */
	private int currType = GET;
	
	/** json请求 */
	private JsonTask mJsonRequest;
	/** 运行状态 */
	protected boolean isRunning = false;
	
	
	
	/**
	 * 新建网络请求任务
	 * 
	 * @param strUrl
	 *            请求网址
	 */
	public CommonHttpTask(String strUrl) {
		this(strUrl, null, GET);
	}

	/**
	 * 新建网络请求任务
	 * 
	 * @param baseUrl
	 *            请求网址
	 * @param value
	 *            参数
	 */
	public CommonHttpTask(String url, TArrayList paramsList, int type) {
		mUrl = UrlUtil.getFullUrlRqst(url, paramsList);
		FrameLog.d(TAG, mUrl);
		
		this.currType = type;
	}
	
	/** 同步执行网络请求并返回结果 */
	public static JSONObject syncExecute(String url) {

		RequestQueue queue = Volley.newRequestQueue(MaijieApp.getAppContext());
		RequestFuture<JSONObject> future = RequestFuture.newFuture();
		JsonTask request = new JsonTask(url, null, future, future);
		queue.add(request);

		try {
			return future.get();
		} catch (InterruptedException e) {
		} catch (ExecutionException e) {
		}
		return null;
	}
	
	/** 同步执行网络请求，用UrlConnection实现，支持更大的数据 */
	public static JSONObject syncGetLargeData(String url) {
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setConnectTimeout(30 * 1000);
			c.setReadTimeout(30 * 1000);
			c.setRequestProperty("Charset", "UTF-8");
			c.setRequestProperty("Content-Type", "application/x-javascript");
			c.setRequestProperty("Accept-Encoding", "gzip,deflate");
			c.connect();
			int status = c.getResponseCode();
			if( status == HttpURLConnection.HTTP_OK ){
				BufferedReader br;
				if( "gzip".equals(c.getContentEncoding()) ){
					br = new BufferedReader(new InputStreamReader(new GZIPInputStream(c.getInputStream())));
				}else{
					br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				}
				
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				return new JSONObject(sb.toString());
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 模板模式
	 *  
	 * 真正执行访问功能的是JsonTask */
	public void execute() {

		mJsonRequest = new JsonTask(currType, mUrl, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				TaskResult tr = new TaskResult();
				try {
					tr = _parseResponse(response);//在Volley访问成功后，拿到的Response上执行统一的处理修整，并以TaskResult的类型返回
				} catch (JSONException e) {
					e.printStackTrace();
					tr.status = TaskResultStatus.JSON_ERROR;
				} catch (HttpException e) {
					e.printStackTrace();
					tr.status = TaskResultStatus.HTTP_ERROR;
				}
				isRunning = false;
				onPostExecute(tr);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				isRunning = false;

				TaskResult tr = new TaskResult();
				tr.status = TaskResultStatus.HTTP_ERROR;
				onPostExecute(tr);
				FrameLog.e(TAG, "onErrorResponse msg=" + error.toString());
			}
		});

		mJsonRequest.setRetryPolicy(new DefaultRetryPolicy(10*1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		MainRequestQueue.getInstance(MaijieApp.getAppContext()).addToQueue(mJsonRequest);

		isRunning = true;
		onPreExecute();

	}
	
	
	/** 取消任务 */
	public void cancel() {
		if (mJsonRequest != null) {
			mJsonRequest.cancel();
		}
	}

	/** 是否在运行 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * 用来初步解析返回结果Response
	 * 
	 * @param res
	 *            返回结果
	 * @return
	 */
	protected abstract TaskResult _parseResponse(JSONObject json) throws JSONException, HttpException;
}
