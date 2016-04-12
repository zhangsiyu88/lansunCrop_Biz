package com.lansun.qmyo.maijie_biz.fragment.single;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.eventbus.EventBus;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.umeng.analytics.MobclickAgent;

public class BizUserInfoFragment extends HeaderFragment implements OnClickListener {
	
	
	private ViewGroup rootView;
	private EditText et_pelase_claim_store,et_idcard,et_phone_num,et_store_degree;
	private Button btn_save_userinfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_biz_userinfo, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		et_pelase_claim_store = (EditText) rootView.findViewById(R.id.et_pelase_claim_store);
		et_idcard = (EditText) rootView.findViewById(R.id.et_idcard);
		et_phone_num = (EditText) rootView.findViewById(R.id.et_phone_num);
		et_store_degree = (EditText) rootView.findViewById(R.id.et_store_degree);
		btn_save_userinfo = (Button) rootView.findViewById(R.id.btn_save_userinfo);
		btn_save_userinfo.setOnClickListener(this);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
	}

	@Override
	protected String getTitle() {
		return getString(R.string.about_us);
	}

	@Override
	protected int getMenuResId() {
		return -1;
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("");
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd(""); 
	}

	@Override
	public boolean onKeyDown(int keyCode) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			close();
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save_userinfo:
			//1.分别增加上正则表达的判断式
			//2.获取符合条件的输入内容
			//3.在前一步基础上上传获得的数据
			et_pelase_claim_store.getText().toString();		
			et_idcard.getText().toString();		
			et_phone_num.getText().toString();		
			et_store_degree.getText().toString();	
			lToast("保存成功");
			EventBus.getDefault().post("认领人信息保存成功！");	
			getActivity().getSupportFragmentManager().popBackStack();
			break;
		default:
			break;
		}		
		super.onClick(v);
	}

}
