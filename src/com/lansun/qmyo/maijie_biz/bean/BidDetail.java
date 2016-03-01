package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class BidDetail extends Result {
	
	private static final long serialVersionUID = 577224290255323344L;
	
	public String additionalInfo;
	public String biddingCount;
	public String biddingTime;
	public String cid;
	public String consultantId;
	public String consultantImgUrl;
	public String dealershipId;
	public String dealershipName;
	public String dealershipType;
	public String discount;
	public String id;
	public String inquiryId;
	public String maxBiddingCount;
	public String offer;
	public String phoneNum;
	public String preferentialInfo;
	public String remark;
	public String status;
	
	
	@Override
	public String toString() {
		return "BidDetail [additionalInfo=" + additionalInfo
				+ ", biddingCount=" + biddingCount + ", biddingTime="
				+ biddingTime + ", cid=" + cid + ", consultantId="
				+ consultantId + ", consultantImgUrl=" + consultantImgUrl
				+ ", dealershipId=" + dealershipId + ", dealershipName="
				+ dealershipName + ", dealershipType=" + dealershipType
				+ ", discount=" + discount + ", id=" + id + ", inquiryId="
				+ inquiryId + ", maxBiddingCount=" + maxBiddingCount
				+ ", offer=" + offer + ", phoneNum=" + phoneNum
				+ ", preferentialInfo=" + preferentialInfo + ", remark="
				+ remark + ", status=" + status + "]";
	}


	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if (jsonObj.has("additionalInfo")) {
				this.additionalInfo = jsonObj.getString("additionalInfo");
			}
			if (jsonObj.has("biddingCount")) {
				this.biddingCount = jsonObj.getString("biddingCount");;
			}
			if (jsonObj.has("biddingTime")) {
				this.biddingTime = jsonObj.getString("biddingTime");;
			}
			if (jsonObj.has("cid")) {
				this.cid = jsonObj.getString("cid");;
			}
			if (jsonObj.has("consultantId")) {
				this.consultantId = jsonObj.getString("consultantId");;
			}
			if (jsonObj.has("consultantImgUrl")) {
				this.consultantImgUrl = jsonObj.getString("consultantImgUrl");;
			}
			if (jsonObj.has("dealershipId")) {
				this.dealershipId = jsonObj.getString("dealershipId");;
			}
			if (jsonObj.has("dealershipName")) {
				this.dealershipName = jsonObj.getString("dealershipName");;
			}
			if (jsonObj.has("dealershipType")) {
				this.dealershipType = jsonObj.getString("dealershipType");;
			}
			if (jsonObj.has("discount")) {
				this.discount = jsonObj.getString("discount");;
			}
			if (jsonObj.has("id")) {
				this.id = jsonObj.getString("id");;
			}
			if (jsonObj.has("inquiryId")) {
				this.inquiryId = jsonObj.getString("inquiryId");;
			}
			if (jsonObj.has("maxBiddingCount")) {
				this.maxBiddingCount = jsonObj.getString("maxBiddingCount");;
			}
			if (jsonObj.has("offer")) {
				this.offer = jsonObj.getString("offer");;
			}
			if (jsonObj.has("phoneNum")) {
				this.phoneNum = jsonObj.getString("phoneNum");;
			}
			if (jsonObj.has("preferentialInfo")) {
				this.preferentialInfo = jsonObj.getString("preferentialInfo");;
			}
			if (jsonObj.has("remark")) {
				this.remark = jsonObj.getString("remark");;
			}
			if(jsonObj.has("status")) {
				this.status = jsonObj.getString("status");;
			}
			this.type = 0;

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
