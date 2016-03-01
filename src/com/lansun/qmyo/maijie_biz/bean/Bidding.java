package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class Bidding extends Result {

      private static final long serialVersionUID = 5069711310242808144L;

      public String againInquiryTimeSec;
      public String appearanceColor;
      public String attNum;
      public String bid1;
      public String bid2;
      public String bid3;
      public String bid4;
      public String bid5;
      public String bid6;
      public String biddingNum;
      public String biddingTime;
      public String carseriesId;
      public String carseriesImgUrl;
      public String carseriesName;
      public String city;
      public String consuId;
      public String dealershipId;
      public String dealershipNum;
      public String discount;
      public String finalOffer;
      public String grabNum;
      public String id;
      public String imgUrl;
      public String inquiryInvalidTime;
      public String inquiryTime;
      public String inquiryTimeSec;
      public String intentionTime;
      public String maxDiscount;
      public String maxRecordId;
      public String minOffer;
      public String modelsId;
      public String modelsName;
      public String msrp;
      public String name;
      public String newBiddingNum;
      public String newOffer;
      public String nickname;
      public String paymentModel;
      public String phoneNum;
      public String preferentialInfo;
      public String pushTime;
      public String sex;
      public String status;
      public String userId;
      public String userNum;
      public String year;

      @Override
      public String toString() {
	  return "Inquiry [againInquiryTimeSec=" + againInquiryTimeSec + ", appearanceColor=" + appearanceColor + ", attNum=" + attNum + ", bid1="
		    + bid1 + ", bid2=" + bid2 + ", bid3=" + bid3 + ", bid4=" + bid4 + ", bid5=" + bid5 + ", bid6=" + bid6 + ", biddingNum="
		    + biddingNum + ", biddingTime=" + biddingTime + ", carseriesId=" + carseriesId + ", carseriesImgUrl=" + carseriesImgUrl
		    + ", carseriesName=" + carseriesName + ", city=" + city + ", consuId=" + consuId + ", dealershipId=" + dealershipId
		    + ", dealershipNum=" + dealershipNum + ", discount=" + discount + ", finalOffer=" + finalOffer + ", grabNum=" + grabNum
		    + ", id=" + id + ", imgUrl=" + imgUrl + ", inquiryInvalidTime=" + inquiryInvalidTime + ", inquiryTime=" + inquiryTime
		    + ", inquiryTimeSec=" + inquiryTimeSec + ", intentionTime=" + intentionTime + ", maxDiscount=" + maxDiscount
		    + ", maxRecordId=" + maxRecordId + ", minOffer=" + minOffer + ", modelsId=" + modelsId + ", modelsName=" + modelsName
		    + ", msrp=" + msrp + ", name=" + name + ", newBiddingNum=" + newBiddingNum + ", newOffer=" + newOffer + ", nickname="
		    + nickname + ", paymentModel=" + paymentModel + ", phoneNum=" + phoneNum + ", preferentialInfo=" + preferentialInfo
		    + ", pushTime=" + pushTime + ", sex=" + sex + ", status=" + status + ", userId=" + userId + ", userNum=" + userNum
		    + ", year=" + year + "]";
      }

      @Override
      public boolean parseJsonObj(JSONObject jsonObj) {
	  try {
	        if (jsonObj.has("againInquiryTimeSec")) {
		    this.againInquiryTimeSec = jsonObj.getString("againInquiryTimeSec");
	        }
	        if (jsonObj.has("appearanceColor")) {
		    this.appearanceColor = jsonObj.getString("appearanceColor");
	        }
	        if (jsonObj.has("attNum")) {
		    this.attNum = jsonObj.getString("attNum");
	        }
	        if (jsonObj.has("bid1")) {
		    this.bid1 = jsonObj.getString("bid1");
	        }
	        if (jsonObj.has("bid2")) {
		    this.bid2 = jsonObj.getString("bid2");
	        }
	        if (jsonObj.has("bid3")) {
		    this.bid3 = jsonObj.getString("bid3");
	        }
	        if (jsonObj.has("bid4")) {
		    this.bid4 = jsonObj.getString("bid4");
	        }
	        if (jsonObj.has("bid5")) {
		    this.bid5 = jsonObj.getString("bid5");
	        }
	        if (jsonObj.has("bid6")) {
		    this.bid6 = jsonObj.getString("bid6");
	        }
	        if (jsonObj.has("biddingNum")) {
		    this.biddingNum = jsonObj.getString("biddingNum");
	        }
	        if (jsonObj.has("biddingTime")) {
		    this.biddingTime = jsonObj.getString("biddingTime");
	        }
	        if (jsonObj.has("carseriesId")) {
		    this.carseriesId = jsonObj.getString("carseriesId");
	        }
	        if (jsonObj.has("carseriesImgUrl")) {
		    this.carseriesImgUrl = jsonObj.getString("carseriesImgUrl");
	        }
	        if (jsonObj.has("carseriesName")) {
		    this.carseriesName = jsonObj.getString("carseriesName");
		    ;
	        }
	        if (jsonObj.has("city")) {
		    this.city = jsonObj.getString("city");
	        }
	        if (jsonObj.has("consuId")) {
		    this.consuId = jsonObj.getString("consuId");
	        }
	        if (jsonObj.has("dealershipId")) {
		    this.dealershipId = jsonObj.getString("dealershipId");
	        }
	        if (jsonObj.has("dealershipNum")) {
		    this.dealershipNum = jsonObj.getString("dealershipNum");
	        }
	        if (jsonObj.has("discount")) {
		    this.discount = jsonObj.getString("discount");
	        }
	        if (jsonObj.has("finalOffer")) {
		    this.finalOffer = jsonObj.getString("finalOffer");
	        }
	        if (jsonObj.has("grabNum")) {
		    this.grabNum = jsonObj.getString("grabNum");
	        }
	        if (jsonObj.has("id")) {
		    this.id = jsonObj.getString("id");
	        }
	        if (jsonObj.has("imgUrl")) {
		    this.imgUrl = jsonObj.getString("imgUrl");
	        }
	        if (jsonObj.has("inquiryInvalidTime")) {
		    this.inquiryInvalidTime = jsonObj.getString("inquiryInvalidTime");
	        }
	        if (jsonObj.has("inquiryTime")) {
		    this.inquiryTime = jsonObj.getString("inquiryTime");
	        }
	        if (jsonObj.has("inquiryTimeSec")) {
		    this.inquiryTimeSec = jsonObj.getString("inquiryTimeSec");
	        }
	        if (jsonObj.has("intentionTime")) {
		    this.intentionTime = jsonObj.getString("intentionTime");
	        }
	        if (jsonObj.has("maxDiscount")) {
		    this.maxDiscount = jsonObj.getString("maxDiscount");
	        }
	        if (jsonObj.has("maxRecordId")) {
		    this.maxRecordId = jsonObj.getString("maxRecordId");
	        }
	        if (jsonObj.has("minOffer")) {
		    this.minOffer = jsonObj.getString("minOffer");
	        }
	        if (jsonObj.has("modelsId")) {
		    this.modelsId = jsonObj.getString("modelsId");
	        }
	        if (jsonObj.has("modelsName")) {
		    this.modelsName = jsonObj.getString("modelsName");
	        }
	        if (jsonObj.has("msrp")) {
		    this.msrp = jsonObj.getString("msrp");
	        }
	        if (jsonObj.has("name")) {
		    this.name = jsonObj.getString("name");
	        }
	        if (jsonObj.has("newBiddingNum")) {
		    this.newBiddingNum = jsonObj.getString("newBiddingNum");
	        }
	        if (jsonObj.has("newOffer")) {
		    this.newOffer = jsonObj.getString("newOffer");
	        }
	        if (jsonObj.has("nickname")) {
		    this.nickname = jsonObj.getString("nickname");
	        }
	        if (jsonObj.has("paymentModel")) {
		    this.paymentModel = jsonObj.getString("paymentModel");
	        }
	        if (jsonObj.has("phoneNum")) {
		    this.phoneNum = jsonObj.getString("phoneNum");
	        }
	        if (jsonObj.has("preferentialInfo")) {
		    this.preferentialInfo = jsonObj.getString("preferentialInfo");
	        }
	        if (jsonObj.has("pushTime")) {
		    this.pushTime = jsonObj.getString("pushTime");
	        }
	        if (jsonObj.has("sex")) {
		    this.sex = jsonObj.getString("sex");
	        }
	        if (jsonObj.has("status")) {
		    this.status = jsonObj.getString("status");
	        }
	        if (jsonObj.has("userId")) {
		    this.userId = jsonObj.getString("userId");
	        }
	        if (jsonObj.has("userNum")) {
		    this.userNum = jsonObj.getString("userNum");
	        }
	        if (jsonObj.has("year")) {
		    this.year = jsonObj.getString("year");
	        }
	        this.type = 2;

	        return true;
	  } catch (Exception e) {
	        e.printStackTrace();
	  }

	  return false;
      }

}
