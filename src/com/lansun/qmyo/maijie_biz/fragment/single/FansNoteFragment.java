package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.FansNoteAdapter;
import com.lansun.qmyo.maijie_biz.bean.FansNoteBean;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

public class FansNoteFragment extends HeaderFragment implements OnClickListener {
	
	
	private ViewGroup rootView;
	private EditText et_pelase_claim_store,et_idcard,et_phone_num,et_store_degree;
	private Button btn_save_userinfo;
	private PullToRefreshListView lv_reward_note;
	private FansNoteBean fansNoteBean;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_fans_note, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		lv_reward_note = (PullToRefreshListView) rootView.findViewById(R.id.lv_reward_note);
		ArrayList<FansNoteBean> dataList = preData();
		FansNoteAdapter fansNoteAdapter = new FansNoteAdapter(getActivity(), dataList, -1);
		lv_reward_note.setAdapter(fansNoteAdapter);
	}

	/**
	 * 模拟数据
	 * @return
	 */
	private ArrayList<FansNoteBean> preData() {
		ArrayList<FansNoteBean> mDataList = new ArrayList<FansNoteBean>();
		for(int i=1;i<100;i++){
			fansNoteBean = new FansNoteBean();
			fansNoteBean.setDate("2016.10."+i);
			fansNoteBean.setRewardCount(10+i);
			fansNoteBean.setAllRewardCount(1000+i);
			mDataList.add(fansNoteBean);
		}
		return mDataList;
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
		/*case 
			break;*/
		default:
			break;
		}		
		super.onClick(v);
	}

}
