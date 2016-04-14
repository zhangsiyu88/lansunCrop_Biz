package com.lansun.qmyo.maijie_biz.activity;


import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.view.listener.OnClick;
import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.utils.Util;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;


public class PrePayOrderActivity extends Activity implements IWXAPIEventHandler {

	public String appId,
	partnerId,
	prepayId,
	nonceStr,
	timeStamp,
	packageValue,
	sign,
	extData;
	
	public static int  _pay_way = -1;
	public static final int  AliPay = 0 ;
	public static final int  WeiXinPay = 1 ;
	
	
	
	@InjectAll
    Views v;
	private IWXAPI api;
	class Views {
		
		@InjectBinder(listeners = { OnClick.class }, method = "click")
		private ImageView im_backfrontarrow, iv_ali_pay,iv_weixin_pay;
		@InjectBinder(listeners = { OnClick.class }, method = "click")
		private Button bt_to_pay;
		@InjectBinder(listeners = { OnClick.class }, method = "click")
		private RadioButton rb_yes,rb_no;
		
		private ImageView iv_coupon_1,iv_coupon_2;
	}
	
	
	
	
	//Umeng统计需求的代码-----------------------------
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
	//Umeng统计--------------------------------------
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		api = WXAPIFactory.createWXAPI(this, "wx75ffbaca73416dcb");//   wxb4ba3c02aa476ea1
		appId = "wx75ffbaca73416dcb";
		setContentView(R.layout.activity_prepayorder);
		
	}
	
	/*
	 * 样式需求：
	 * 
	 * 1.点击去付款，调用对应的微信或者支付宝进行支付
	 * 
	 * 2.选中Aliapy或者WeiXinPay时，对应的按钮的 背景边框  虚表示为 选中状态
	 * 
	 * 3.代金券按钮点击是否使用时，底部的优惠券对应展示还是消失
	 * 
	 */
	
	
	@InjectInit
	private void init() {
		v.rb_yes.setChecked(true);
		v.bt_to_pay.setPressed(false);
	}

	private void click(View view) {
		switch (view.getId()) {
			case R.id.bt_to_pay:     
				v.bt_to_pay.setEnabled(false);
			/*
			 * 1.1 toReqServer();  //访问后台服务器，拿到下单的详细信息
			 */
				switch (_pay_way) {
				case AliPay:
					//toPayByAli(appId,partnerId,prepayId,nonceStr,timeStamp,packageValue,sign,extData);//去付款_by WeinXin
					Toast.makeText(this, "暂未开通支付宝支付，请使用微信支付", Toast.LENGTH_SHORT).show();
					break;
				case WeiXinPay:
					toPayByWeixin(appId,partnerId,prepayId,nonceStr,timeStamp,packageValue,sign,extData);//去付款_by WeinXin
					break;
				}
			
			    v.bt_to_pay.setEnabled(true);
			break;
		    case R.id.rg_check_use_coupon:         //是否使用优惠券
			
		    break;
			case R.id.rb_yes:   //使用优惠券
				v.iv_coupon_1.setVisibility(View.VISIBLE);
				v.iv_coupon_2.setVisibility(View.VISIBLE);
			break;
			case R.id.rb_no:   //不使用优惠券
				v.iv_coupon_1.setVisibility(View.GONE);
				v.iv_coupon_2.setVisibility(View.GONE);
			break;
			case R.id.im_backfrontarrow:   //不使用优惠券
				finish();
			break;
			case R.id.iv_ali_pay:   
				v.iv_ali_pay.setPressed(true);
				v.iv_ali_pay.setSelected(true);
				v.iv_weixin_pay.setSelected(false);
				v.iv_weixin_pay.setPressed(false);
				v.bt_to_pay.setPressed(true);
				_pay_way = AliPay;
			break;
			case R.id.iv_weixin_pay:  
				v.iv_weixin_pay.setPressed(true);
				v.iv_weixin_pay.setSelected(true);
				v.iv_ali_pay.setPressed(false);
				v.iv_ali_pay.setSelected(false);
				v.bt_to_pay.setPressed(true);
				_pay_way = WeiXinPay;
				break;
		
		
	  } 
	}
	
	//微信的两个回调函数
	@Override
	public void onReq(BaseReq req) {
		
	}
	
	//微信支付完成后的回调函数
	@Override
	public void onResp(BaseResp resp){
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			builder.show();
		}
	}
	
	
	/**
	 * 调起微信支付
	 * @param appId
	 * @param partnerId
	 * @param prepayId
	 * @param nonceStr
	 * @param timeStamp
	 * @param packageValue
	 * @param sign
	 * @param extData
	 */
	public void toPayByWeixin(  String appId,
						String partnerId,
						String prepayId,
						String nonceStr,
						String timeStamp,
						String packageValue,
						String sign,
						String extData){
		
		String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
		Toast.makeText(this, "获取订单中...", Toast.LENGTH_SHORT).show();
        try{
			byte[] buf = Util.httpGet(url);
			if (buf != null && buf.length > 0) {
				String content = new String(buf);
				Log.e("get server pay params:",content);
	        	JSONObject json = new JSONObject(content); 
				if(null != json && !json.has("retcode") ){
					PayReq req = new PayReq();
					//req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
					req.appId			= json.getString(appId);
					req.partnerId		= json.getString(partnerId);
					req.prepayId		= json.getString(prepayId);
					req.nonceStr		= json.getString(nonceStr);
					req.timeStamp		= json.getString(timeStamp);
					req.packageValue	= json.getString(packageValue);
					req.sign			= json.getString(sign);
					req.extData			= "app data"; // optional
					
					Toast.makeText(this, "正常调起微信支付", Toast.LENGTH_SHORT).show();
					// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
					api.sendReq(req);
				}else{
		        	Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
		        	Toast.makeText(this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
				}
			}else{
	        	Log.d("PAY_GET", "服务器请求错误");
	        	Toast.makeText(this, "服务器请求错误", Toast.LENGTH_SHORT).show();
	        }
        }catch(Exception e){
        	Log.e("PAY_GET", "异常："+e.getMessage());
        	Toast.makeText(this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
	}
}
