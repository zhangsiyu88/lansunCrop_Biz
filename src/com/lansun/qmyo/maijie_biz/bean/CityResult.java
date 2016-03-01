package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class CityResult extends Result {
	
	private static final long serialVersionUID = 6289082464698717440L;
	
	public String mCityCode;
	public String mCityName;
	public String mProvinceCode;
	public String mFirstLetter;
	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		return false;
		
	}
}
