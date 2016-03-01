package com.lansun.qmyo.maijie_biz.asynctask;

import org.apache.http.message.BasicNameValuePair;

import com.lansun.qmyo.maijie_biz.asynctask.http.CommonHttpTask;
import com.lansun.qmyo.maijie_biz.bean.Bidding;
import com.lansun.qmyo.maijie_biz.bean.CustomerInfo;
import com.lansun.qmyo.maijie_biz.bean.Inquiry;
import com.lansun.qmyo.maijie_biz.bean.User;
import com.lansun.qmyo.maijie_biz.log.FrameLog;
import com.lansun.qmyo.maijie_biz.utils.PrefsConfig;
import com.lansun.qmyo.maijie_biz.utils.TArrayList;
import com.lansun.qmyo.maijie_biz.utils.UrlUtil;

/**
 * 网络请求接口请添加在该类
 * 所有网络请求的唯一入口   =_=
 * @author Yeun.Zhang
 */

public class MaijieBizRqstApi {
	public static final String TAG = "MaijieBizApi";

	private static MaijieBizRqstApi _instance;

	private MaijieBizRqstApi() {
	}

	/**
	 * 常见的单例模式（带同步锁的单例）
	 * 
	 * @return
	 */
	public static synchronized MaijieBizRqstApi getInstance() {
		if (_instance == null) {
			_instance = new MaijieBizRqstApi();
		}
		return _instance;
	}
	
	
	/**
	 * 登录
	 * @param phoneNum
	 * @param pwd
	 * @return
	 */
	public MaijieBizHttpTask doLogin(String phoneNum, String pwd) {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("phoneNum", phoneNum));
		paramsList.add(new BasicNameValuePair("passwd", pwd));
		paramsList.add(new BasicNameValuePair("cid", PrefsConfig.u_cid));

		return new MaijieBizHttpTask(UrlUtil.urlLogin(), paramsList, CommonHttpTask.GET);
	}

	/**
	 * 注册获取短信验证码
	 * @param phoneNum
	 * @return
	 */
	public MaijieBizHttpTask getPhoneVerify(String phoneNum) {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("phoneNum", phoneNum));
		
		return new MaijieBizHttpTask(UrlUtil.urlSmsVerify(), paramsList, CommonHttpTask.GET);
	}
	
	/**
	 * 注册下一步
	 */
	public MaijieBizHttpTask doRegisterNextStep(String phone, String verify, String passwd) {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("phoneNum", phone));
		paramsList.add(new BasicNameValuePair("messCode", verify));
		paramsList.add(new BasicNameValuePair("passwd", passwd));
		paramsList.add(new BasicNameValuePair("cid", PrefsConfig.u_cid));
		
		return new MaijieBizHttpTask(UrlUtil.urlRegisterNext(), paramsList, CommonHttpTask.GET);
	}
	
	public MaijieBizHttpTask doFindBackPasswd(String phone, String verify, String passwd) {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("phoneNum", phone));
		paramsList.add(new BasicNameValuePair("messCode", verify));
		paramsList.add(new BasicNameValuePair("passwd", passwd));
//		paramsList.add(new BasicNameValuePair("cid", PrefsConfig.u_cid));
		
		return new MaijieBizHttpTask(UrlUtil.urlFindBackPasswd(), paramsList, CommonHttpTask.GET);
	}
	
	
	/**
	 * 提交注册信息
	 * @param user
	 * @return
	 */
	public MaijieBizHttpTask doRegisterSubmit(User user) {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("consulId", PrefsConfig.u_id));
		paramsList.add(new BasicNameValuePair("name", user.u_name));
		paramsList.add(new BasicNameValuePair("sex", user.u_sex));
		paramsList.add(new BasicNameValuePair("city", user.u_city));
		paramsList.add(new BasicNameValuePair("dealershipId", user.u_dealerId));
		paramsList.add(new BasicNameValuePair("job", user.u_job));
		paramsList.add(new BasicNameValuePair("brandName", user.u_brandName));
		
		return new MaijieBizHttpTask(UrlUtil.urlRegisterSubmit(), paramsList, CommonHttpTask.GET);
	}

	
	/**
	 * 提交问题反馈
	 * @param msg
	 * @return
	 */
	public MaijieBizHttpTask doSubmitFeedBack(String msg) {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("content", msg));
		paramsList.add(new BasicNameValuePair("userId", PrefsConfig.u_id));
		return new MaijieBizHttpTask(UrlUtil.urlFeedBack(), paramsList, CommonHttpTask.GET);	
	}
	
	/**
	 * 获取系统消息
	 * @return
	 */
	public MaijieBizHttpTask getSysMsg() {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("userType", "2"));
		paramsList.add(new BasicNameValuePair("userId", PrefsConfig.u_id));
		return new MaijieBizHttpTask(UrlUtil.urlSysMsg(), paramsList, CommonHttpTask.GET);
	}
	
	/**
	 * 获取系统消息数量
	 * @return
	 */
	public MaijieBizHttpTask getSysMsgCount() {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("userType", "2"));
		paramsList.add(new BasicNameValuePair("userId", PrefsConfig.u_id));
		return new MaijieBizHttpTask(UrlUtil.urlSysMsgCount(), paramsList, CommonHttpTask.GET);
	}





	

	
	public MaijieBizHttpTask getUpdateInfo() {
		TArrayList paramsList = new TArrayList();
		paramsList.add(new BasicNameValuePair("type", "2"));
		return new MaijieBizHttpTask(UrlUtil.urlUpdateInfo(), paramsList, CommonHttpTask.GET);
	}

}
