package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

/**
 * 排行榜
 * @author anqi
 */
public class Rank extends Result {

	private static final long serialVersionUID = -4467811572903854957L;
	
	
	public String brandImgUrl;
	public String consulImgUrl;
	public String consulName;
	public String content;
	public String dealName;
	public String id;
	public String receiverId;
	public String sendTime;
	public String senderId;
	public String status;
	public int succeRushNum;
	public String userId;
	public String userType;
	
	
	@Override
	public String toString() {
		return "Rank [brandImgUrl=" + brandImgUrl + ", consulImgUrl="
				+ consulImgUrl + ", consulName=" + consulName + ", content="
				+ content + ", dealName=" + dealName + ", id=" + id
				+ ", receiverId=" + receiverId + ", sendTime=" + sendTime
				+ ", senderId=" + senderId + ", status=" + status
				+ ", succeRushNum=" + succeRushNum + ", userId=" + userId
				+ ", userType=" + userType + "]";
	}


	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if(jsonObj.has("brandImgUrl")) {
				this.brandImgUrl = jsonObj.getString("brandImgUrl");
			}
			if(jsonObj.has("consulImgUrl")) {
				this.consulImgUrl = jsonObj.getString("consulImgUrl");
			}
			if(jsonObj.has("consulName")) {
				this.consulName = jsonObj.getString("consulName");
			}
			if(jsonObj.has("content")) {
				this.content = jsonObj.getString("content");
			}
			if(jsonObj.has("dealName")) {
				this.dealName = jsonObj.getString("dealName");
			}
			if(jsonObj.has("id")) {
				this.id = jsonObj.getString("id");
			}
			if(jsonObj.has("receiverId")) {
				this.receiverId = jsonObj.getString("receiverId");
			}
			if(jsonObj.has("senderId")) {
				this.senderId = jsonObj.getString("senderId");
			}
			if(jsonObj.has("sendTime")) {
				this.sendTime = jsonObj.getString("sendTime");
			}
			if(jsonObj.has("status")) {
				this.status = jsonObj.getString("status");
			}
			if(jsonObj.has("succeRushNum")) {
				try {
					int num = Integer.parseInt(jsonObj.getString("succeRushNum"));
					this.succeRushNum = num;
				} catch (Exception e) {
				}
			}
			if(jsonObj.has("userId")) {
				this.userId = jsonObj.getString("userId");
			}
			if(jsonObj.has("userType")) {
				this.userType = jsonObj.getString("userType");
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
