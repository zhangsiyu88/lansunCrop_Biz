package com.lansun.qmyo.maijie_biz.fragment.single;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.MainActivity;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil.TipAlertDialogCallBack;
import com.umeng.analytics.MobclickAgent;

public class ActivityPreShowFragment extends HeaderFragment implements OnClickListener {
	
	
	private ViewGroup rootView;
	private Button bt_save_submit2verify;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_pre_show, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		bt_save_submit2verify = (Button) rootView.findViewById(R.id.bt_save_submit2verify);
		
		bt_save_submit2verify.setOnClickListener(this);
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
		case R.id.bt_save_submit2verify:
			//1、弹出对话框
		DialogUtil.createCommonDialog(mContext, "", "请仔细核对发布的活动信息\n一经提交无法修改", "确认提交", "取消", new TipAlertDialogCallBack() {
			
			@Override
			public void onPositiveButtonClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Fragment f = new VerifyActivityStateFragment();
			    ((MainActivity)getActivity()).entrySubFragment(f);
			}
			
			@Override
			public void onNegativeButtonClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
			
			break;
		default:
			break;
		}		
		super.onClick(v);
	}

}
