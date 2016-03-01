package com.lansun.qmyo.maijie_biz.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

public class UrlUtil {
      public static final boolean DEBUG = true;// 测试环境开关
//	public static final String HOST_URL = "http://www.91car.net/";
	public static final String HOST_URL = (DEBUG == true ? "http://appapi.qmyo.org/"
			: "http://appapi.qmyo.com/");

	/** 登录 */
	public static String urlLogin() {
		return HOST_URL + Url.URL_LOGIN;
	}
	/** 登出 */
	public static String urlLogout() {
		return HOST_URL + Url.URL_LOGOUT;
	}
	/** 城市列表 */
	public static String urlCityList() {
		return HOST_URL + Url.URL_CITY_LIST;
	}
	/** 今日询价列表 */
	public static String urlInquiryList() {
		return HOST_URL + Url.URL_INQUIRY_LIST;
	}

	/** 抢单 */
	public static String urlGrab() {
		return HOST_URL + Url.URL_GRAB;
	}

	/** 品牌列表 */
	public static String urlBrandList() {
		return HOST_URL + Url.URL_BRAND_LIST;
	}

	/** 经销商列表 */
	public static String urlDealerList() {
		return HOST_URL + Url.URL_DEALER_LIST;
	}

	/** 顾问信息 */
	public static String urlUserInfo() {
		return HOST_URL + Url.URL_USER_INFO;
	}

//	/** 竞价列表 */
//	public static String urlBiddingList() {
//		return HOST_URL + Url.URL_BIDDING;
//	}

	/** 竞价详情 */
	public static String urlBidDetail() {
		return HOST_URL + Url.URL_BIDDING_DETAIL;
	}

	/** 继续竞价 */
	public static String urlGoonBid() {
		return HOST_URL + Url.URL_GOON_BIDDING_;
	}

	/** 历史竞价 */
	public static String urlBidHistory() {
		return HOST_URL + Url.URL_BIDDING_HISTORY;
	}

	/** 竞价 */
	public static String urlBidding() {
		return HOST_URL + Url.URL_BIDDING_ADD;
	}

	/** 短信验证 */
	public static String urlSmsVerify() {
		return HOST_URL + Url.URL_SMS_VERIFY;
	}

	/** 注册下一步 */
	public static String urlRegisterNext() {
		return HOST_URL + Url.URL_RIGISTER;
	}
	
	/** 找回密码 */
	public static String urlFindBackPasswd() {
		return HOST_URL + Url.URL_FIND_PWD;
	}

	/** 所有经销商列表 */
	public static String urlDealerAll() {
		return HOST_URL + Url.URL_DEALER_LIST_ALL;
	}
	/** 所有汽贸公司列表 */
	public static String urlQimaoAll() {
		return HOST_URL + Url.URL_QIMAO_LIST;
	}

	/** 上传图片 */
	public static String urlUploadPic() {
		return HOST_URL + Url.URL_UPLOAD_PIC;
	}
	/** 上传名片 */
	public static String urlUploadBusinessCardPic() {
		return HOST_URL + Url.URL_UPLOAD_BUSINESS_CARD_PIC;
	}
	/** 上传身份证 */
	public static String urlUploadIdentityCardPic() {
		return HOST_URL + Url.URL_UPLOAD_IDENTITY_CARD_PIC;
	}

	/** 注册提交 */
	public static String urlRegisterSubmit() {
		return HOST_URL + Url.URL_RIGISTOR_SUBMIT;
	}

	/** 客户列表 */
	public static String urlCustomerList() {
		return HOST_URL + Url.URL_CUSTOMER;
	}

	/** 清除历史竞价 */
	public static String urlClearHistoryGrab() {
		return HOST_URL + Url.URL_CLEAR_HISTORY_GRAB;
	}
	
	/** 问题反馈*/
	public static String urlFeedBack() {
		return HOST_URL + Url.URL_FEED_BACK;
	}
	
	/** 系统消息*/
	public static String urlSysMsg() {
		return HOST_URL + Url.URL_SYS_MSG;
	}
	/** 系统消息数量*/
	public static String urlSysMsgCount() {
		return HOST_URL + Url.URL_SYS_MSG_COUNT;
	}
	/** 个人信息*/
//	public static String urlUserDetail(){
//		return HOST_URL + Url.URL_SYS_MSG_COUNT;
//	}
	/** 排行榜接口*/
    public static String urlRankList(){
    	return HOST_URL + Url.URL_RANK_LIST;
    }
    
    /** 获取升级信息*/
    public static String urlUpdateInfo() {
    	return HOST_URL + Url.URL_UPDATE;
    }
    
    /**扫描添加客户*/
    public static String urlScanAddCustomer() {
    	return HOST_URL + Url.URL_QR_ADD_CUSTOMER;
    }
    /**扫描礼券Callback*/
    public static String urlCoupon() {
    	return "http://w.91car.net/" + Url.URL_COUPON;
    }
    
	private static class Url {

		/** 登录 */
		private static String URL_LOGIN = "chequan1/consul/login";
		/** 登出接口 */
		private static String URL_LOGOUT = "chequan1/consul/loginout";
		/** 城市列表 */
		private static String URL_CITY_LIST = "";
		/** 今日询价列表 */
		private static String URL_INQUIRY_LIST = "chequan1/inquiry/today";
		/** 抢单 */
		private static String URL_GRAB = "chequan1/bidding/addfirst";
		/** 品牌列表 */
		private static String URL_BRAND_LIST = "chequan1/dealership/brandlist";
		/** 经销商列表 */
		private static String URL_DEALER_LIST = "chequan1/dealership/deallist";
		/** 汽贸列表 */
		private static String URL_QIMAO_LIST = "chequan1/dealership/getCarSell";
		/** 顾问信息 */
		private static String URL_USER_INFO = "/chequan1/consul/info";
//		/** 竞价列表 */
//		private static String URL_BIDDING = "chequan1/inquiry/consulgrab";
		/** 竞价详情 */
		private static String URL_BIDDING_DETAIL = "chequan1/bidding/details";
		/** 继续竞价 */
		private static String URL_GOON_BIDDING_ = "chequan1/bidding/goonbidding";
		/** 历史竞价 */
		private static String URL_BIDDING_HISTORY = "chequan1/inquiry/consulhisgrab";
		/** 竞价 */
		private static String URL_BIDDING_ADD = "chequan1/bidding/confirmbidding";
		/** 短信验证 */
		private static String URL_SMS_VERIFY = "chequan1/mess/send";
		/** 注册下一步 */
		private static String URL_RIGISTER = "chequan1/consul/reg";
		private static String URL_FIND_PWD = "chequan1/consul/getpwd";
		/** 所有经销商列表 */
		private static String URL_DEALER_LIST_ALL = "chequan1/dealership/alllist";
		/** 上传头像*/
		private static String URL_UPLOAD_PIC = "chequan1/file/up";
		/** 上传名片*/
		private static String URL_UPLOAD_BUSINESS_CARD_PIC = "chequan1/file/upCard";
		/** 上传身份证*/
		private static String URL_UPLOAD_IDENTITY_CARD_PIC = "chequan1/file/upId";
		/** 注册提交 */
		private static String URL_RIGISTOR_SUBMIT = "chequan1/consul/submit";
		/** 客户列表 */
		private static String URL_CUSTOMER = "chequan1/inquiry/usernum";
		/** 清除历史竞价接口 */
		private static String URL_CLEAR_HISTORY_GRAB = "chequan1/inquiry/clearhisgrab";
		/** 问题反馈*/
		private static String URL_FEED_BACK = "chequan1/mess/feed";
		/** 系统消息*/
		private static String URL_SYS_MSG = "chequan1/mess/list";
		/** 系统消息角标接口*/
		private static String URL_SYS_MSG_COUNT = "chequan1/mess/newnum";
		/**个人信息接口 */
		//private static String URL_USER_DETAIL = "UserDetail";
		/**排行榜接口 */
		private static String URL_RANK_LIST = "chequan1/mess/top";
		/**升级接口*/
		private static String URL_UPDATE = "chequan1/servicedb";
		/**二维码扫描添加客户接口*/
		private static String URL_QR_ADD_CUSTOMER = "chequan1/bidding/scan";
		/** 扫描礼券callback*/
		private static String URL_COUPON = "present/scan_buy.php";
	}
	

	//自动补全请求参数
	public static String getFullUrlRqst(String url, TArrayList paramsList) {
		if (paramsList == null)
			return url;
		// TODO 添加公共参数
		paramsList.add(new BasicNameValuePair("phoneNum", PrefsConfig.u_phonenum));
		paramsList.add(new BasicNameValuePair("cid", PrefsConfig.u_cid));
		paramsList.add(new BasicNameValuePair("userType", "2"));
		int randomNum = randomNum();
		paramsList.add(new BasicNameValuePair("randomNum", String.valueOf(randomNum)));
		String md5Str = PrefsConfig.u_phonenum + PrefsConfig.u_cid + randomNum +PrefsConfig.u_passwd;
		paramsList.add(new BasicNameValuePair("sig", md5(md5Str)));

		StringBuilder sb = new StringBuilder();
		sb.append(url).append("?").append(StrOperate.getQueryString(paramsList));

		return sb.toString();
	}
	
	private static int randomNum() {
		Random random = new Random();

		int x = random.nextInt(899999);

		return x+100000;
	}
	
	
	private static String md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			return buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	// public static String getFilePathByUrl(String url, String rootPath) {
	// String path = null;
	// if (!TextUtils.isEmpty(url)) {
	// path = rootPath + File.separator + String.valueOf(url.hashCode());
	// }
	// return path;
	// }

	public static List<FormBodyPart> getUploadCommonParams() {
		List<FormBodyPart> parts = new ArrayList<FormBodyPart>();
		try {
//			parts.add(new FormBodyPart("userType", new StringBody("2", Charset.forName("UTF-8"))));
			parts.add(new FormBodyPart("userType", new StringBody("2", Charset.forName("UTF-8"))));
			parts.add(new FormBodyPart("userId", new StringBody(PrefsConfig.u_id, Charset.forName("UTF-8"))));
			parts.add(new FormBodyPart("phoneNum", new StringBody(PrefsConfig.u_phonenum, Charset.forName("UTF-8"))));
			parts.add(new FormBodyPart("cid", new StringBody(PrefsConfig.u_cid, Charset.forName("UTF-8"))));
//			parts.add(new FormBodyPart("userType", body));
			int randomNum = randomNum();
			parts.add(new FormBodyPart("randomNum", new StringBody(String.valueOf(randomNum), Charset.forName("UTF-8"))));
			String md5Str = md5(PrefsConfig.u_phonenum + PrefsConfig.u_cid + randomNum +PrefsConfig.u_passwd);
			parts.add(new FormBodyPart("sig", new StringBody(md5Str, Charset.forName("UTF-8"))));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return parts;
	}
	
	public static List<FormBodyPart> getUploadBusinessCardParams() {
		List<FormBodyPart> parts = new ArrayList<FormBodyPart>();
		try {
//			parts.add(new FormBodyPart("userType", new StringBody("2", Charset.forName("UTF-8"))));
//			parts.add(new FormBodyPart("userType", new StringBody("2", Charset.forName("UTF-8"))));
			parts.add(new FormBodyPart("userId", new StringBody(PrefsConfig.u_id, Charset.forName("UTF-8"))));
//			parts.add(new FormBodyPart("phoneNum", new StringBody(PrefsConfig.u_phonenum, Charset.forName("UTF-8"))));
//			parts.add(new FormBodyPart("cid", new StringBody(PrefsConfig.u_cid, Charset.forName("UTF-8"))));
////			parts.add(new FormBodyPart("userType", body));
//			int randomNum = randomNum();
//			parts.add(new FormBodyPart("randomNum", new StringBody(String.valueOf(randomNum), Charset.forName("UTF-8"))));
//			String md5Str = md5(PrefsConfig.u_phonenum + PrefsConfig.u_cid + randomNum +PrefsConfig.u_passwd);
//			parts.add(new FormBodyPart("sig", new StringBody(md5Str, Charset.forName("UTF-8"))));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return parts;
	}
	public static List<FormBodyPart> getUploadIdentityCardParams() {
		List<FormBodyPart> parts = new ArrayList<FormBodyPart>();
		try {
//			parts.add(new FormBodyPart("userType", new StringBody("2", Charset.forName("UTF-8"))));
//			parts.add(new FormBodyPart("userType", new StringBody("2", Charset.forName("UTF-8"))));
			parts.add(new FormBodyPart("userId", new StringBody(PrefsConfig.u_id, Charset.forName("UTF-8"))));
//			parts.add(new FormBodyPart("phoneNum", new StringBody(PrefsConfig.u_phonenum, Charset.forName("UTF-8"))));
//			parts.add(new FormBodyPart("cid", new StringBody(PrefsConfig.u_cid, Charset.forName("UTF-8"))));
////			parts.add(new FormBodyPart("userType", body));
//			int randomNum = randomNum();
//			parts.add(new FormBodyPart("randomNum", new StringBody(String.valueOf(randomNum), Charset.forName("UTF-8"))));
//			String md5Str = md5(PrefsConfig.u_phonenum + PrefsConfig.u_cid + randomNum +PrefsConfig.u_passwd);
//			parts.add(new FormBodyPart("sig", new StringBody(md5Str, Charset.forName("UTF-8"))));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return parts;
	}

	// private static String toUtf8(String str) {
	// String temp = null;
	// try {
	// temp = new String(str.getBytes(),"utf-8");
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return temp;
	// }
}
