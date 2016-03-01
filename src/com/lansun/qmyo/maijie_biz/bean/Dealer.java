package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class Dealer extends Result {

	private static final long serialVersionUID = 1L;
	
	public String addr;
	public String name;
	public String type;
	public String id;
	
	
	
	@Override
	public String toString() {
		return "Dealer [addr=" + addr + ", name=" + name + ", type=" + type
				+ ", id=" + id + "]";
	}



	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if(jsonObj.has("addr")) {
				this.addr = jsonObj.getString("addr");
			}
			if(jsonObj.has("name")) {
				this.name = jsonObj.getString("name");
			}
			if(jsonObj.has("type")) {
				this.type = jsonObj.getString("type");
			}
			if(jsonObj.has("id")) {
				this.id = jsonObj.getString("id");
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
}
