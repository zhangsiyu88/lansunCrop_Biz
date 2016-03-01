package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class DownloadItem extends Result {

	private static final long serialVersionUID = 1678671044380995944L;
	
	public String url;
	public int totleSize;
	public long currSize;
	
	public boolean compulsory;
	public String md5;
	public String name;
	public int version;
	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if(jsonObj.has("compulsory")) {
				this.compulsory = jsonObj.getBoolean("compulsory");
			}
			if(jsonObj.has("md5")) {
				this.md5 = jsonObj.getString("md5");
			}
			if(jsonObj.has("name")) {
				this.name = jsonObj.getString("name");
			}
			if(jsonObj.has("size")) {
				this.totleSize = jsonObj.getInt("size");
			}
			if(jsonObj.has("url")) {
				this.url = jsonObj.getString("url");
			}
//			this.type = downObj.getInt("type");
			if(jsonObj.has("version")) {
				this.version = jsonObj.getInt("version");
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
