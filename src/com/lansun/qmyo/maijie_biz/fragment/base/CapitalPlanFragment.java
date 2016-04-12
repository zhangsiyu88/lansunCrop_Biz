package com.lansun.qmyo.maijie_biz.fragment.base;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.MainActivity;
import com.lansun.qmyo.maijie_biz.fragment.single.FansNoteFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.FocusNoteFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.InchargeNoteFragment;

public class CapitalPlanFragment extends BaseFragment implements OnPageChangeListener{
	
	private ViewGroup      rootView;
	private ArrayList<View> viewList;
	private ImageView iv_2left;
	private ImageView iv_2right;
	private ViewPager vp_store;
	private LinearLayout ll_fans_note;
	private LinearLayout ll_incharge_note;
	private LinearLayout ll_foucus_note;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.koubei_fragment, null);
	    rootView = contentView;
	    initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);		
	}
	
	
	@Override
	protected String getTitle() {
		return null;
	}

	@Override
	protected int getRMenuIvResId() {
		return -1;
	}

	@Override
	protected int getRMenuTvResId() {
		return -1;
	}

	@Override
	protected int getLMenuTvResId() {
		return -1;
	}

	@Override
	protected int getLMenuIvResId() {
		return -1;
	}

	@Override
	protected void getNetData(boolean needLoading) {

	}
	@Override
	public void initView() {
		vp_store = (ViewPager) rootView.findViewById(R.id.vp_store);
		iv_2left = (ImageView) rootView.findViewById(R.id.iv_2left);
		iv_2right = (ImageView) rootView.findViewById(R.id.iv_2right);
		ll_fans_note = (LinearLayout) rootView.findViewById(R.id.ll_fans_note);
		ll_incharge_note = (LinearLayout) rootView.findViewById(R.id.ll_incharge_note);
		ll_foucus_note = (LinearLayout) rootView.findViewById(R.id.ll_foucus_note);
		viewList = inflateFunc();
		RewardVPAdapter rewardVPAdapter = new RewardVPAdapter(viewList);
		vp_store.setAdapter(rewardVPAdapter);
		vp_store.setOnPageChangeListener(this);
		
		iv_2left.setOnClickListener(this);
		iv_2right.setOnClickListener(this);
		ll_fans_note.setOnClickListener(this);
		ll_incharge_note.setOnClickListener(this);
		ll_foucus_note.setOnClickListener(this);
	}
	
	private ArrayList<View> inflateFunc() {
		viewList = new ArrayList<View>();
		View page_first  = LayoutInflater.from(getActivity()).inflate(R.layout.page_first, null);
		View page_second = LayoutInflater.from(getActivity()).inflate(R.layout.page_second, null);
		viewList.add(page_first);
		viewList.add(page_second);
		return  viewList;
	}

	class RewardVPAdapter extends PagerAdapter{

		private ArrayList<View> mViewList;
		public RewardVPAdapter(ArrayList<View> viewList) {
			mViewList = viewList;
		}

		@Override
		public int getCount() {
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
		    container.addView(mViewList.get(position));
			return mViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	}

	@Override
	public void onClick(View v) {
		Fragment f;
		switch ( v.getId()) {
		case R.id.iv_2left:
			vp_store.setCurrentItem(0,true);
			System.out.println("点击向左操作");
			break;
		case R.id.iv_2right:
			vp_store.setCurrentItem(1);
			System.out.println("点击向右操作");
			break;
		case R.id.ll_fans_note:
			f = new FansNoteFragment();
			((MainActivity)getActivity()).entrySubFragment(f);
			break;
		case R.id.ll_incharge_note:
			f = new InchargeNoteFragment();
			((MainActivity)getActivity()).entrySubFragment(f);
			break;
		case R.id.ll_foucus_note:
			f = new FocusNoteFragment();
			((MainActivity)getActivity()).entrySubFragment(f);
			break;

		default:
			break;
		}
		super.onClick(v);
	}
	
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	@Override
	public void onPageSelected(int position) {
		if (position == 1) {
			iv_2left.setVisibility(View.VISIBLE);
			iv_2right.setVisibility(View.GONE);
		} else {
			iv_2right.setVisibility(View.VISIBLE);
			iv_2left.setVisibility(View.GONE);
		}
	}

}
