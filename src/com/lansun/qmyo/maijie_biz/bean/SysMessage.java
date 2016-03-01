package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class SysMessage extends Result {

	private static final long serialVersionUID = -941796537771772699L;
	
	public String content;// 消息内容
	public String receiverId;
	public String sendTime;// 消息时间
	public String senderId;//
	public String status;// 消息的状态
	public String userId;
	public String userType;
	
	
	
	@Override
	public String toString() {
		return "SysMessage [content=" + content + ", receiverId=" + receiverId
				+ ", sendTime=" + sendTime + ", senderId=" + senderId
				+ ", status=" + status + ", userId=" + userId + ", userType="
				+ userType + "]";
	}



	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if(jsonObj.has("content")) {
				this.content = jsonObj.getString("content");
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
