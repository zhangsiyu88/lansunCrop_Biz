package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.FocusNoteAdapter;
import com.lansun.qmyo.maijie_biz.bean.FansNoteBean;
import com.lansun.qmyo.maijie_biz.bean.FocusNoteBean;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

public class FocusNoteFragment extends HeaderFragment implements OnClickListener {
	
	
	private ViewGroup rootView;
	private PullToRefreshListView lv_focus_note;
	private FocusNoteBean focusNoteBean;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_focus_note, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		lv_focus_note = (PullToRefreshListView) rootView.findViewById(R.id.lv_focus_note);
		ArrayList<FocusNoteBean> dataList = preData();
		FocusNoteAdapter focusNoteAdapter = new FocusNoteAdapter(getActivity(), dataList, -1);
		lv_focus_note.setAdapter(focusNoteAdapter);
	}

	/**
	 * 模拟数据
	 * @return
	 */
	private ArrayList<FocusNoteBean> preData() {
		ArrayList<FocusNoteBean> mDataList = new ArrayList<FocusNoteBean>();
		for(int i=1;i<100;i++){
			focusNoteBean = new FocusNoteBean();
			focusNoteBean.setDate("2016.10."+i);
			focusNoteBean.setAddCounts(10+i);
			focusNoteBean.setDeleteCounts(1000+i);
			focusNoteBean.setAllFansCounts(1000+i);
			mDataList.add(focusNoteBean);
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
