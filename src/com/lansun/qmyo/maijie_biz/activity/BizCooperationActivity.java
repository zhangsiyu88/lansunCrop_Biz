package com.lansun.qmyo.maijie_biz.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.eventbus.EventBus;
import com.lansun.qmyo.maijie_biz.fragment.single.BizInfoFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.BizUserInfoFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.ClaimStoreFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.VerifyMaterialFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.VerifyStateFragment;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil.TipAlertDialogCallBack;
import com.lansun.qmyo.maijie_biz.view.ImageLoadingDialog;

/**
 * 注册完成后的跳转页
 * 
 * @author  Yeun.Zhang
 *
 */
public class BizCooperationActivity extends BaseFragmentActivity implements OnClickListener{



	private TextView tv_pelase_claim_store;
	private TextView tv_go2homepage;
	private TextView tv_upload_verify_meaterial;
	private Button btn_submit_verify;
	private TextView tv_verify_result;
	private TextView tv_biz_user_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bizcoorperation);
		initView();
		//将当前的Activity塞入事件总线中，获取到
		EventBus.getDefault().register(this);
	}

	private void initView() {
		frame_id  = R.id.biz_frame;
		frameView = (FrameLayout) findViewById(frame_id);
		RelativeLayout rl_1 = (RelativeLayout) findViewById(R.id.rl_1);
		RelativeLayout rl_2 = (RelativeLayout) findViewById(R.id.rl_2);
		RelativeLayout rl_3 = (RelativeLayout) findViewById(R.id.rl_3);
		RelativeLayout rl_4 = (RelativeLayout) findViewById(R.id.rl_4);
		tv_go2homepage = (TextView) findViewById(R.id.tv_go2homepage);
		tv_verify_result = (TextView) findViewById(R.id.tv_verify_result);
		tv_pelase_claim_store = (TextView) findViewById(R.id.tv_pelase_claim_store);
		tv_biz_user_info = (TextView) findViewById(R.id.tv_biz_user_info);
		tv_upload_verify_meaterial = (TextView) findViewById(R.id.tv_upload_verify_meaterial);
		btn_submit_verify = (Button) findViewById(R.id.btn_submit_verify);
		
		
		rl_1.setOnClickListener(this);
		rl_2.setOnClickListener(this);
		rl_3.setOnClickListener(this);
		rl_4.setOnClickListener(this);
		tv_go2homepage.setOnClickListener(this);
		btn_submit_verify.setOnClickListener(this);
	}
	
	

	@Override
	public void onClick(View v) {
		Fragment f;
		switch(v.getId()){
		case R.id.rl_1:
			 f = new ClaimStoreFragment();
			 entrySubFragment(f);
			break;
		case R.id.rl_2:
			f = new BizInfoFragment();
			entrySubFragment(f);
			break;
		case R.id.rl_3:
			f = new BizUserInfoFragment();
			entrySubFragment(f);
			break;
		case R.id.rl_4:
			/*f = new UploadVerifyMaterialFragment();
			entrySubFragment(f);*/
			startActivity(new Intent(this,UploadVerifyMaterialActivity.class));
			break;
		case R.id.rl_5:
			f = new VerifyMaterialFragment();
			entrySubFragment(f);
			break;
		case R.id.tv_go2homepage:
			finish();
			startActivity(new Intent(this,MainActivity.class));
			break;
		case R.id.btn_submit_verify:
			DialogUtil.createCommonDialog(this, "", "请仔细核对所填认证资料\n提交成功后无法修改", "确认提交", "取消", new TipAlertDialogCallBack() {
				
				@Override
				public void onPositiveButtonClick(DialogInterface dialog, int which) {
					final ImageLoadingDialog dialog_loading = new ImageLoadingDialog(BizCooperationActivity.this);
					dialog_loading.setCanceledOnTouchOutside(false);
					dialog_loading.show();
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							dialog_loading.dismiss();
							btn_submit_verify.setEnabled(false);
							tv_verify_result.setText("审核资料已提交");
							Fragment f = new VerifyStateFragment();
							entrySubFragment(f);
						}
					}, 1000 * 1);
					dialog.dismiss();
				}
				@Override
				public void onNegativeButtonClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			
			
			
			
			break;
		}
	}
	
	public void onEventMainThread(Object item) {  
		
		Toast.makeText(this, "拿到EventBus的内容", Toast.LENGTH_LONG).show();
		if(item instanceof String && (((String)item).equals("来自列表选中"))||((String)item).equals("来自创建门店页面")){
			tv_pelase_claim_store.setText((String)item);
		}
		if(item instanceof String&&((String)item).equals("图片已经存储")){
			tv_upload_verify_meaterial.setText((String)item);
		}
		if(item instanceof String&&((String)item).equals("认领人信息保存成功！")){
			tv_biz_user_info.setText((String)item);
		}
	}
	
}
