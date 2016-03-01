package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class CarGoonBid extends Result {

	private static final long serialVersionUID = 6067384605013702533L;
	
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
		return "CarGoonBid [additionalInfo=" + additionalInfo
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
		return false;
		
	}
	
	

}
