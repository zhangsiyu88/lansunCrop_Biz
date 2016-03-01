package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class CarGift extends Result {

	private static final long serialVersionUID = -6327327370896250657L;
	
	public String addr;
	public String brandId;
	public String brandName;
	public String city;
	public String consulId;
	public String firstLetter;
	public String grade;
	public String id;
	public String imgUrl;
	public String name;
	public String phone;
	public String preferentialInfo;
	public String status;
	public String type;
	
	
	@Override
	public String toString() {
		return "CarGift [addr=" + addr + ", brandId=" + brandId
				+ ", brandName=" + brandName + ", city=" + city + ", consulId="
				+ consulId + ", firstLetter=" + firstLetter + ", grade="
				+ grade + ", id=" + id + ", imgUrl=" + imgUrl + ", name="
				+ name + ", phone=" + phone + ", preferentialInfo="
				+ preferentialInfo + ", status=" + status + ", type=" + type
				+ "]";
	}


	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if (jsonObj.has("addr")) {
				this.addr = jsonObj.getString("addr");
			}
			if (jsonObj.has("brandId")) {
				this.brandId = jsonObj.getString("brandId");
			}
			if (jsonObj.has("brandName")) {
				this.brandName = jsonObj.getString("brandName");
			}
			if (jsonObj.has("city")) {
				this.city = jsonObj.getString("city");
			}
			if (jsonObj.has("consulId")) {
				this.consulId = jsonObj.getString("consulId");
			}
			if (jsonObj.has("firstLetter")) {
				this.firstLetter = jsonObj.getString("firstLetter");
			}
			if (jsonObj.has("grade")) {
				this.grade = jsonObj.getString("grade");
			}
			if (jsonObj.has("id")) {
				this.id = jsonObj.getString("id");
			}
			if (jsonObj.has("imgUrl")) {
				this.imgUrl = jsonObj.getString("imgUrl");
			}
			if (jsonObj.has("name")) {
				this.name = jsonObj.getString("name");
			}
			if (jsonObj.has("phone")) {
				this.phone = jsonObj.getString("phone");
			}
			if (jsonObj.has("preferentialInfo")) {
				this.preferentialInfo = jsonObj.getString("preferentialInfo");
			}
			if (jsonObj.has("status")) {
				this.status = jsonObj.getString("status");
			}
			if (jsonObj.has("type")) {
				this.type = jsonObj.getString("type");
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
