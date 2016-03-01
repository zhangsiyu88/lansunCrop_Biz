package com.lansun.qmyo.maijie_biz.fragment.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.ui.swipfragment.SwipeBackFragment;
import com.lansun.qmyo.maijie_biz.ui.swipfragment.SwipeBackLayout;
import com.lansun.qmyo.maijie_biz.ui.swipfragment.SwipeBackFragment.ResultType;
import com.lansun.qmyo.maijie_biz.uisupport.other.OnBarMenuClickListener;
import com.lansun.qmyo.maijie_biz.uisupport.other.UiUtil;

/**
 * 所有二级Fragment的父类，继承自SwipeBackFragment 支持滑动销毁
 * 
 * 支持自定义title， 自定义右边的menu图片和事件处理 左边的返回事件统一默认处理
 * 
 * @author Yeun.zhang
 *
 */
@SuppressLint("InflateParams") public abstract class HeaderFragment extends SwipeBackFragment implements OnClickListener {

	private ImageView mBgSkin;
	private ImageView mMenuIcon = null;
	private TextView mTitle = null;
	public RelativeLayout mSearchBar;
	public EditText mEtSearch;
	public ImageView mSearch;
	// TitleBar右侧文字menu
	public TextView mMenuText = null;
	public Context mContext;

	// private ImageView mBgSkin = null;
	private OnBarMenuClickListener mRightMenuClickListener = null;
	private ViewGroup skinlayer;

	protected abstract String getTitle();

	protected abstract int getMenuResId();

	public abstract boolean onKeyDown(int keyCode);
//	protected abstract boolean onKeyDown(int keyCode);

	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		mContext = getActivity();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.headerfragment, null);
		mLoadingView = view.findViewById(R.id.base_loading);
		
		/*mTitle = (TextView) view.findViewById(R.id.header_title);
		mTitle.setText(getTitle());
		if(getTitle() == null) {
			mSearchBar = (RelativeLayout) view.findViewById(R.id.search_bar);
			mSearchBar.setVisibility(View.VISIBLE);
			mTitle.setVisibility(View.GONE);
			mSearch = (ImageView) view.findViewById(R.id.search);
			mEtSearch = (EditText) view.findViewById(R.id.search_bar_et);
		}

		((ImageView) view.findViewById(R.id.btn_header_back)).setOnClickListener(this);

		if (getMenuResId() != -1) {
			mMenuIcon = (ImageView) view.findViewById(R.id.btn_header_menu);
			mMenuIcon.setImageResource(getMenuResId());
			mMenuIcon.setOnClickListener(this);
		} else {
			mMenuText = (TextView) view.findViewById(R.id.tv_header_menu_text);
			mMenuText.setOnClickListener(this);
		}*/

		mContentView = (ViewGroup) view.findViewById(R.id.header_content);
		LayoutParams glp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		container.setLayoutParams(glp);
		mContentView.addView(container);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		super.onViewCreated(view, savedInstanceState);//至此，已经完成Fragment的view 和 SwipeBackLayout的挂钩牵连工作
		
		 skinlayer = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.skin, null);
		 mBgSkin = (ImageView)skinlayer.findViewById(R.id.bgskin);
		 mBgSkin.setOnClickListener(this);
		// if (getActivity() instanceof BaseActivity) { //判断是否来自BaseActivity
		// setSkin(((BaseActivity)getActivity()).getCurSkinBitmap());
		// }
		SwipeBackLayout swipView = getSwipeBackLayout();
		ViewGroup childView = (ViewGroup) swipView.getChildAt(0);

		swipView.removeView(childView);
		skinlayer.addView(childView);
		swipView.setContentView(skinlayer);
		swipView.addView(skinlayer);

		// 设置是否滑动退出
		setSwipeBackEnable(true);
		//弹出软键盘
//		UiUtil.openKeyboard(getActivity());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_header_back:
//			if (onKeyDown(KeyEvent.KEYCODE_BACK))
//				return;
			UiUtil.closeKeyboard(getActivity());
			close();
			break;
		case R.id.btn_header_menu:
		case R.id.tv_header_menu_text:
			if (mRightMenuClickListener != null) {
				mRightMenuClickListener.OnRightMenuClick();
			}
			break;
		case R.id.bgskin:
			UiUtil.closeKeyboard(getActivity());
			break;
		default:
			break;
		}
	}
	
	
	
	protected void setOnRightMenuClickListener(OnBarMenuClickListener listener) {
		mRightMenuClickListener = listener;
	}

	protected void show(ResultType type) {
		switch (type) {
		case LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			mContentView.setVisibility(View.GONE);
			break;

		case RESULT:
			mLoadingView.setVisibility(View.GONE);
			mContentView.setVisibility(View.VISIBLE);
			break;
		case EMPTY:
			mLoadingView.setVisibility(View.GONE);
			mContentView.setVisibility(View.VISIBLE);
			break;
		case FAIL:
			mLoadingView.setVisibility(View.GONE);
			mContentView.setVisibility(View.VISIBLE);
			break;
		default:
			break;

		}
	}
	

	
	
}
