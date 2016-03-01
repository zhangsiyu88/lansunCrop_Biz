package com.lansun.qmyo.maijie_biz.bean;

import java.util.List;

import org.json.JSONObject;

public class CustomerGroup extends Result {

	public static final long serialVersionUID = -6162609984731589487L;
	public String carseriesName;
	public String userNum;
	public boolean expand;

	public List<CustomerInfo> customerList;

	
	@Override
	public String toString() {
		return "CustomerGroup [carseriesName=" + carseriesName + ", userNum="
				+ userNum + ", customerList=" + customerList + "]";
	}


	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if(jsonObj.has("carseriesName")) {
				this.carseriesName = jsonObj.getString("carseriesName");
			}
			if(jsonObj.has("userNum")) {
				this.userNum = jsonObj.getString("userNum");
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
