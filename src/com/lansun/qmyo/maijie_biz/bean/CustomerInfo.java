package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;


public class CustomerInfo extends Result {
	
	private static final long serialVersionUID = 346646082715881953L;
	public String name;
	public String phoneNum;
	public String minOffer;
	public String carseriesName;
	public String year;
	public String modelsName;
	public String appearanceColor;
	public String intentionTime;
	public String inquiryTime;
	public String userId;
	public String carseriesId;
	public String modelsId;
	@Override
	public String toString() {
		return "CustomerInfo [name=" + name + ", phoneNum=" + phoneNum
				+ ", minOffer=" + minOffer + ", carseriesName=" + carseriesName
				+ ", year=" + year + ", modelsName=" + modelsName
				+ ", appearanceColor=" + appearanceColor + ", intentionTime="
				+ intentionTime + ", inquiryTime=" + inquiryTime + ", userId="
				+ userId + ", carseriesId=" + carseriesId + ", modelsId="
				+ modelsId + "]";
	}

	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if (jsonObj.has("name")) {
				this.name = jsonObj.getString("name");
			}
			if (jsonObj.has("phoneNum")) {
				this.phoneNum = jsonObj.getString("phoneNum");
			}
			if (jsonObj.has("minOffer")) {
				this.minOffer = jsonObj.getString("minOffer");
			}
			if (jsonObj.has("carseriesName")) {
				this.carseriesName = jsonObj.getString("carseriesName");
			}
			if (jsonObj.has("year")) {
				this.year = jsonObj.getString("year");
			}
			if (jsonObj.has("modelsName")) {
				this.modelsName = jsonObj.getString("modelsName");
			}
			if (jsonObj.has("appearanceColor")) {
				this.appearanceColor = jsonObj.getString("appearanceColor");
			}
			if (jsonObj.has("intentionTime")) {
				this.intentionTime = jsonObj.getString("intentionTime");
			}
			if (jsonObj.has("inquiryTime")) {
				this.inquiryTime = jsonObj.getString("inquiryTime");
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
}
