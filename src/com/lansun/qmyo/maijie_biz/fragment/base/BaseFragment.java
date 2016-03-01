package com.lansun.qmyo.maijie_biz.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.uisupport.other.OnBarMenuClickListener;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshBase;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshScrollView;

public abstract class BaseFragment extends Fragment implements OnClickListener {
	
	private ViewGroup mContentView = null;
//	private RelativeLayout mContentView = null;
	private View mLoadingView = null;
	private View mFailView = null;
	private TextView mTvMsg;
	private ImageView mIvFail;//
	public RelativeLayout mTitleLayout;//title_layout
//	private RelativeLayout mLoadingLayout;
	public PullToRefreshScrollView mPullRefreshScr;
	
	private ImageView mHeaderBack = null;//title栏回退按钮
	public RelativeLayout mSearchBar = null;
	public EditText mEtSearch = null;
	public ImageView mSearch = null;
	public TextView  mTitle = null;
	
	// TitleBar左侧文字TextView
	public TextView mLMenuTv = null;
	// TitleBar右侧图片ImageView
	public ImageView mRMenuIv = null;
	// TitleBar右侧文字TextView
	public TextView mRMenuTv = null;
	
	public Bundle mBundle;
	
	private OnBarMenuClickListener mBarMenuClickListener = null;
	
	
	//-----------------------------------------下方全为 抽象定义的方法 ---------------------------------------------------------
	/**返回null的话显示SearchBar，否则显示标题，请注意*/
	protected abstract String getTitle();
	/**返回TitleBar右侧ImageView 资源id 返回-1则隐藏*/
	protected abstract int getRMenuIvResId();
	/**返回TitleBar右侧TextView 资源id  返回-1则隐藏*/
	protected abstract int getRMenuTvResId();
	/**返回TitleBar左侧TextView 资源id  返回-1则隐藏*/
	protected abstract int getLMenuTvResId();
	/**返回TitleBar左侧ImageView 资源id 返回-1则隐藏*/
	protected abstract int getLMenuIvResId();
	protected abstract void getNetData(boolean needLoading);
	//-----------------------------------------上部全为 抽象定义的方法 ---------------------------------------------------------
	
	
	public enum ResultType {
		LOADING,
		RESULT,
		EMPTY,
		FAIL;
	}
	
	protected static final String KEY_OFFLINE = "key_offline";
	public static final String KEY_GRAB_DEAL = "key_grab_deal";
	public static final String KEY_GRAB_DEAL_STATUS = "key_grab_deal_status";
	public static final String KEY_BID_DETAIL = "key_bid_detail";
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBundle = getArguments();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.base_fragment, null);
		
		mLoadingView = view.findViewById(R.id.base_loading);
		mFailView = view.findViewById(R.id.base_fail); 
		
		
		mIvFail = (ImageView)mFailView.findViewById(R.id.iv_fail_pic);
		mTvMsg = (TextView)mFailView.findViewById(R.id.tv_msg);
		mTitleLayout = (RelativeLayout) mFailView.findViewById(R.id.title_layout);
		
		mPullRefreshScr = (PullToRefreshScrollView) mFailView.findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScr.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				getNetData(true);
			}
		});
		
		mLMenuTv = (TextView) view.findViewById(R.id.tv_header_left_menu);
		mRMenuIv = (ImageView)view.findViewById(R.id.btn_header_menu);
		mRMenuTv = (TextView) view.findViewById(R.id.tv_header_menu_text);
		mHeaderBack = (ImageView) view.findViewById(R.id.btn_header_back);
		mHeaderBack.setVisibility(View.GONE);
		
		// Title设置
		mTitle = (TextView)view.findViewById(R.id.header_title);
		mTitle.setText(getTitle());
		if(getTitle() == null) {
			mSearchBar = (RelativeLayout) view.findViewById(R.id.search_bar);
			mSearchBar.setVisibility(View.VISIBLE);
			mTitle.setVisibility(View.GONE);
			mSearch = (ImageView) view.findViewById(R.id.search);
			mEtSearch = (EditText) view.findViewById(R.id.search_bar_et);
		}
		
		// 左侧Menu设置
		if(getLMenuTvResId() != -1) {
			mLMenuTv.setText(getString(getLMenuTvResId()));
			mLMenuTv.setVisibility(View.VISIBLE);
			mLMenuTv.setOnClickListener(this);
		} else {
			mLMenuTv.setVisibility(View.INVISIBLE);
		}
		//左侧图形按钮
		if(getLMenuIvResId() != -1) {
			mHeaderBack.setImageResource(getLMenuIvResId());
			mHeaderBack.setVisibility(View.VISIBLE);
			mLMenuTv.setVisibility(View.GONE);
			mHeaderBack.setOnClickListener(this);
			
		} else {
			mHeaderBack.setVisibility(View.GONE);
		}
		
		// 右侧Menu设置
		if(getRMenuIvResId() != -1) {
			mRMenuIv.setImageResource(getRMenuIvResId());
			mRMenuIv.setVisibility(View.VISIBLE);
			mRMenuTv.setVisibility(View.GONE);
			mRMenuIv.setOnClickListener(this);
			
		} else {
			mRMenuIv.setVisibility(View.GONE);
		}
		
		if(getRMenuTvResId() != -1) {
			mRMenuTv.setText(getString(getRMenuTvResId()));
			mRMenuTv.setVisibility(View.VISIBLE);
			mRMenuIv.setVisibility(View.GONE);
			mRMenuTv.setOnClickListener(this);
		} else {
			mRMenuTv.setVisibility(View.GONE);
		}
		
		mContentView = (ViewGroup)view.findViewById(R.id.base_content);
		LayoutParams glp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		container.setLayoutParams(glp);
		
		/** 不要纠结在此处，只要保证传入的container没有父控件即可*/
		mContentView.addView(container);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		MobclickAgent.onPageStart("BaseFragment"); 
	}
	
	@Override
	public void onPause() {
		super.onPause();
//		MobclickAgent.onPageEnd("BaseFragment");
	}
	
	public void show(ResultType hotWordsType) {
		switch (hotWordsType) {
		case LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			mFailView.setVisibility(View.GONE);
			mContentView.setVisibility(View.GONE);
			break;
		case EMPTY:
			mLoadingView.setVisibility(View.GONE);
			mContentView.setVisibility(View.GONE);
			mFailView.setVisibility(View.VISIBLE);
			mTvMsg.setText(getString(R.string.empty_msg));
			mTvMsg.setVisibility(View.GONE);
			break;
		case FAIL:
			mLoadingView.setVisibility(View.GONE);
			mContentView.setVisibility(View.GONE);
			mFailView.setVisibility(View.VISIBLE);
			mTvMsg.setText(getString(R.string.fail));
			mTvMsg.setVisibility(View.VISIBLE);
			mIvFail.setVisibility(View.GONE);
			break;
			
		case RESULT:
			mLoadingView.setVisibility(View.GONE);
			mFailView.setVisibility(View.GONE);
			mContentView.setVisibility(View.VISIBLE);
			break;
			
		default:
			
			break;
		
		}		
	}
	
	public void sToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	public void lToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_header_menu:
		case R.id.tv_header_menu_text:
			if (mBarMenuClickListener != null) {
				mBarMenuClickListener.OnRightMenuClick();
			}
			break;
		case R.id.tv_header_left_menu:
			if(mBarMenuClickListener != null) {
				mBarMenuClickListener.OnLeftMenuClick();
			}
			break;
		case R.id.btn_header_back:
			if(mBarMenuClickListener != null) {
				mBarMenuClickListener.OnLeftMenuClick();
			}
			break;
		default:
			break;
		}
	}
	
	protected void setOnBarMenuClickListener(OnBarMenuClickListener onMenuClickListener) {
		mBarMenuClickListener = onMenuClickListener;
	}
	
	
	
	/** 获取界面中对应id的对象
	 *  
	 *  注： 暂不使用ViewUtils或InjectView等相关的注解方式 进行 View的 定位
	 *  */
	public abstract void initView();
	
}
