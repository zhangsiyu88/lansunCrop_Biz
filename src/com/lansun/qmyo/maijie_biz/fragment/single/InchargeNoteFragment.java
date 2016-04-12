package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.InchargeNoteAdapter;
import com.lansun.qmyo.maijie_biz.bean.FansNoteBean;
import com.lansun.qmyo.maijie_biz.bean.InchargeNoteBean;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

public class InchargeNoteFragment extends HeaderFragment implements OnClickListener {
	
	
	private ViewGroup rootView;
	private Button btn_save_userinfo;
	private InchargeNoteBean inchargeNoteBean;
	private PullToRefreshListView lv_incharge_note;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_incharge_note, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		lv_incharge_note = (PullToRefreshListView) rootView.findViewById(R.id.lv_incharge_note);
		ArrayList<InchargeNoteBean> dataList = preData();
		InchargeNoteAdapter fansNoteAdapter = new InchargeNoteAdapter(getActivity(), dataList, -1);
		lv_incharge_note.setAdapter(fansNoteAdapter);
	}

	/**
	 * 模拟数据
	 * @return
	 */
	private ArrayList<InchargeNoteBean> preData() {
		ArrayList<InchargeNoteBean> mDataList = new ArrayList<InchargeNoteBean>();
		for(int i=1;i<100;i++){
			inchargeNoteBean = new InchargeNoteBean();
			inchargeNoteBean.setDate("2016.10."+i);
			inchargeNoteBean.setEfficientFans(10+i);
			inchargeNoteBean.setInchargeMoney(100+i);
			inchargeNoteBean.setState(i%2);
			
			mDataList.add(inchargeNoteBean);
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
