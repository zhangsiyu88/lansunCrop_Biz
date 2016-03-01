package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class Brand extends Result {
	
	private static final long serialVersionUID = 1L;
	public String brandName;
	public String brandId;
	public String imgUrl;
	public String firstLetter;
	@Override
	public boolean parseJsonObj(JSONObject brandObj) {
		try {
			if(brandObj.has("brandId")) {
				this.brandId = brandObj.getString("brandId");	
			}
			if(brandObj.has("brandName")) {
				this.brandName = brandObj.getString("brandName");
			}
			if(brandObj.has("firstLetter")) {
				this.firstLetter = brandObj.getString("firstLetter");
			}
			if(brandObj.has("imgUrl")) {
				this.imgUrl = brandObj.getString("imgUrl");
			}
			this.type = 0;
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	
	
}
