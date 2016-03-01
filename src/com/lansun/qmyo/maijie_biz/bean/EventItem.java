package com.lansun.qmyo.maijie_biz.bean;

public class EventItem extends Result {

	private static final long serialVersionUID = -6869920456657058619L;
	public static final int ID_REGSTER_SUBMIT = 0x03223;
	public static final int ID_BRAND_CHOSE = 0x03224;
	public static final int ID_GRAB_DEAL = 0x03225;
	public static final int ID_PUSH_MSG = 0x03226;
	public static final int ID_BIDING_DETAIL = 0x03227;
	public static final int ID_GOON_BIDING_SUCC = 0x03228;
	public static final int ID_CONFIRM_BIDDING = 0x03229;
	public static final int ID_AUTO_CLOSE_QR = 0x03230;
	public static final int ID_BACK_TO_QR = 0x03231;
	public static final int ID_REASH_CUSTOMER_LIST = 0x03232;
	public static final int ID_REFRESH_USER_ICON = 0x03233;
	
	public EventItem(int id) {
		eventId = id;
	}
	
	public int eventId;
	public Object object1;
	public Object object2;
	
	@Override
	public String toString() {
		return "EventItem [eventId=" + eventId + "]";
	}

}
