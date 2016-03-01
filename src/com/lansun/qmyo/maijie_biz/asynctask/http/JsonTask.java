package com.lansun.qmyo.maijie_biz.asynctask.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

import com.lansun.qmyo.maijie_biz.log.FrameLog;
import com.lansun.qmyo.maijie_biz.volley.AuthFailureError;
import com.lansun.qmyo.maijie_biz.volley.NetworkResponse;
import com.lansun.qmyo.maijie_biz.volley.ParseError;
import com.lansun.qmyo.maijie_biz.volley.Response;
import com.lansun.qmyo.maijie_biz.volley.Response.ErrorListener;
import com.lansun.qmyo.maijie_biz.volley.Response.Listener;
import com.lansun.qmyo.maijie_biz.volleyvolley.toolbox.HttpHeaderParser;
import com.lansun.qmyo.maijie_biz.volleyvolley.toolbox.JsonObjectRequest;

/**
 * json任务请求,继承自{@link JsonObjectRequest}，支持gzip压缩后的请求
 * @type tool
 * @author Yeun.zhang
 */
public class JsonTask extends JsonObjectRequest {
	private static final String TAG = "JsonTask";

	public JsonTask(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
	}
	
	public JsonTask(String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
		super(url, jsonRequest, listener, errorListener);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String base64Str = getRealString(response.data);
			FrameLog.d(TAG, base64Str);
			
			byte[] responseByte = Base64.decode(base64Str, Base64.DEFAULT);
			
			String jsonString = new String(responseByte);
			return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Charset", "UTF-8");
		headers.put("Content-Type", "application/x-javascript");
		headers.put("Accept-Encoding", "gzip,deflate");
		return headers;
	}

	private int getShort(byte[] data) {
		return (int) ((data[0] << 8) | data[1] & 0xFF);
	}

	private String getRealString(byte[] data) {
		byte[] h = new byte[2];
		h[0] = data[0];
		h[1] = data[1];
		int head = getShort(h);
		boolean t = head == 0x1f8b;
		InputStream in;
		StringBuilder sb = new StringBuilder();
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			if (t) {
				in = new GZIPInputStream(bis);
			} else {
				in = bis;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line=null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
