package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends Result {

	private static final long serialVersionUID = -932827817403776103L;
	
	public String u_cid;
	public String u_id;
	public String u_name;
	public String u_passwd;
	public String u_phonenum;
	public String u_sex;
	public String u_dealerId;
	public String u_dealer;
	public String u_city;
	
	public String u_attNum;
	public String u_dealerType;
	public String u_forbiddenEndTime;
	public String u_grade;
	public String u_imageUrl;
	public String u_identity_card_pic;
	public String u_business_card_pic;
	
	public String u_inquiryPushNum;
	public String u_status;
	public String u_stepNum;
	public String u_succeRushNum;
	public String u_topNum;
	
	public String u_brandName;
//	public String u_brandImgUrl;
	public String u_job;
	
	@Override
      public String toString() {
	  return "User [u_cid=" + u_cid + ", u_id=" + u_id + ", u_name=" + u_name + ", u_passwd=" + u_passwd + ", u_phonenum=" + u_phonenum
		    + ", u_sex=" + u_sex + ", u_dealerId=" + u_dealerId + ", u_dealer=" + u_dealer + ", u_city=" + u_city + ", u_attNum="
		    + u_attNum + ", u_dealerType=" + u_dealerType + ", u_forbiddenEndTime=" + u_forbiddenEndTime + ", u_grade=" + u_grade
		    + ", u_imageUrl=" + u_imageUrl + ", u_identity_card_pic=" + u_identity_card_pic + ", u_business_card_pic="
		    + u_business_card_pic + ", u_inquiryPushNum=" + u_inquiryPushNum + ", u_status=" + u_status + ", u_stepNum=" + u_stepNum
		    + ", u_succeRushNum=" + u_succeRushNum + ", u_topNum=" + u_topNum + ", u_brandName=" + u_brandName + ", u_job=" + u_job + "]";
      }

      @Override
	public boolean parseJsonObj(JSONObject jsonUser) {
		try {
            if (jsonUser.has("u_attNum")) {
                this.u_attNum = jsonUser.getString("u_attNum");
            }
            if(jsonUser.has("u_cid")) {
            	this.u_cid = jsonUser.getString("u_cid");
            }
            if(jsonUser.has("city")) {
            	this.u_city = jsonUser.getString("city");
            }
            if(jsonUser.has("dealershipId")) {
            	this.u_dealerId = jsonUser.getString("dealershipId");
            }
			if(jsonUser.has("dealershipName")) {
				this.u_dealer = jsonUser.getString("dealershipName");
			}
			if(jsonUser.has("dealershipType")) {
				this.u_dealerType = jsonUser.getString("dealershipType");
			}
			if(jsonUser.has("forbiddenEndTime")) {
				this.u_forbiddenEndTime = jsonUser.getString("forbiddenEndTime");
			}
			if(jsonUser.has("grade")) {
				this.u_grade = jsonUser.getString("grade");
			}
			if(jsonUser.has("id")) {
				this.u_id = jsonUser.getString("id");
			}
			if(jsonUser.has("imgUrl")) {
				this.u_imageUrl = jsonUser.getString("imgUrl");
			}
			if(jsonUser.has("imgCard")) {
				this.u_identity_card_pic = jsonUser.getString("imgCard");
			}
			if(jsonUser.has("imgId")) {
				this.u_business_card_pic = jsonUser.getString("imgId");
			}
			if(jsonUser.has("inquiryPushNum")) {
				this.u_inquiryPushNum = jsonUser.getString("inquiryPushNum");
			}
			if(jsonUser.has("name")) {
				this.u_name = jsonUser.getString("name");
			}
			if(jsonUser.has("passwd")) {
				this.u_passwd = jsonUser.getString("passwd");
			}
			if(jsonUser.has("phoneNum")) {
				this.u_phonenum = jsonUser.getString("phoneNum");
			}
			if(jsonUser.has("sex")) {
				this.u_sex = jsonUser.getString("sex");
			}
			if(jsonUser.has("status")) {
				this.u_status = jsonUser.getString("status");
			}
			if(jsonUser.has("stepNum")) {
				this.u_stepNum = jsonUser.getString("stepNum");
			}
			if(jsonUser.has("succeRushNum")) {
				this.u_succeRushNum = jsonUser.getString("succeRushNum");
			}
			if(jsonUser.has("topNum")) {
				this.u_topNum = jsonUser.getString("topNum");
			}
			if(jsonUser.has("brandName")) {
				this.u_brandName = jsonUser.getString("brandName");
			}
			if(jsonUser.has("job")) {
				this.u_job = jsonUser.getString("job");
			}
            
			return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
		return false;
	}

}
