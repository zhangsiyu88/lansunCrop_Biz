package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

/**
 * 竞价历史
 * @since 2015.06.20
 * @version 1.0
 */
public class BiddingHistory extends Result {
	private static final long serialVersionUID = -706021054460046443L;
	/**
	 * 历史竞价车图片
	 */
	public String imgUrl;
	/**
	 * 车系
	 */
	public String carseriesName;
	/**
	 * 名字
	 */
	public String name;
	/**
	 * 指导价
	 */
	public String msrp;
	/**
	 * 汽车制造款生产时间
	 */
	public String year;
	/**
	 * 车型
	 */
	public String modelsName;
	/**
	 * 顾问id
	 */
	public String consuId;
	/**
	 * 车最低报价
	 */
	public String minOffer;
	/**
	 * 是否被关注(1为关注)
	 */
	public String attNum;
	/**
	 * id
	 */
	public String id;
	
	@Override
	public String toString() {
		return "BiddingHistory [imgUrl=" + imgUrl + ", carseriesName="
				+ carseriesName + ", name=" + name + ", msrp=" + msrp
				+ ", year=" + year + ", modelsName=" + modelsName
				+ ", consuId=" + consuId + ", minOffer=" + minOffer
				+ ", attNum=" + attNum + "]";
	}

	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		try {
			if(jsonObj.has("attNum")) {
				this.attNum = jsonObj.getString("attNum");
			}
			if(jsonObj.has("carseriesName")) {
				this.carseriesName = jsonObj.getString("carseriesName");
			}
			if(jsonObj.has("consuId")) {
				this.consuId = jsonObj.getString("consuId");
			}
			if(jsonObj.has("imgUrl")) {
				this.imgUrl = jsonObj.getString("imgUrl");
			}
			if(jsonObj.has("minOffer")) {
				this.minOffer = jsonObj.getString("minOffer");
			}
			if(jsonObj.has("modelsName")) {
				this.modelsName = jsonObj.getString("modelsName");
			}
			if(jsonObj.has("msrp")) {
				this.msrp = jsonObj.getString("msrp");
			}
			if(jsonObj.has("name")) {
				this.name = jsonObj.getString("name");
			}
			if(jsonObj.has("year")) {
				this.year = jsonObj.getString("year");
			}
			if(jsonObj.has("id")) {
				this.id = jsonObj.getString("id");
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
