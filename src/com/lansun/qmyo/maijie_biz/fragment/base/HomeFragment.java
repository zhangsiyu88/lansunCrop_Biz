package com.lansun.qmyo.maijie_biz.fragment.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.MainActivity;
import com.lansun.qmyo.maijie_biz.fragment.single.AboutFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.ReleaseActWriteInfoFragment;
import com.lansun.qmyo.maijie_biz.ui.fragment.qrcode.CaptureFragment;
import com.lansun.qmyo.maijie_biz.view.GooViewListener;

@SuppressLint("InflateParams") 
public class HomeFragment extends BaseFragment implements OnClickListener {
	private LinearLayout ll_tomystore;
	private RelativeLayout rl_myacts;
	private Button bt_sureStore;
	private Context mContext;
	private TextView red_point;
	private ViewGroup contentView;
	private ImageView im_saoyisao;
	private TextView applyActs;
	private ImageView releaseActs;
	private ImageView messageCenter;
	private TextView tv_basepager_title;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mContext = getActivity();
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		contentView = (ViewGroup) inflater.inflate(R.layout.header_fragment, null);
		initView();
		initData();
		
		return super.onCreateView(inflater, contentView, savedInstanceState);		
	}
	

	@Override
	public void initView() {
		im_saoyisao = (ImageView)   contentView.findViewById(R.id.im_saoyisao);
		im_saoyisao.setOnClickListener(this);
		applyActs   = (TextView)    contentView.findViewById(R.id.tv_apply_activity);
		applyActs.setOnClickListener(this);
		releaseActs = (ImageView)   contentView.findViewById(R.id.im_releaseacts);
		releaseActs.setOnClickListener(this);
		messageCenter = (ImageView) contentView.findViewById(R.id.iv_messagercenter);
		messageCenter.setOnClickListener(this);
		
		//进入 选择我的门店 的 界面
		ll_tomystore = (LinearLayout) contentView.findViewById(R.id.ll_tomystore);
		ll_tomystore.setOnClickListener(this);
		//进入 门店管理 的 界面
		rl_myacts = (RelativeLayout) contentView.findViewById(R.id.rl_myacts);
		rl_myacts.setOnClickListener(this);
		//进入 立即认领 的界面
		bt_sureStore = (Button) contentView.findViewById(R.id.bt_sureStore);
		bt_sureStore.setOnClickListener(this);
		red_point = (TextView) contentView.findViewById(R.id.point);
		/**
		 * 
		 * 初始化监听者，方便对圆形按钮进行GooViewListener
		 * 之随意在这一步就进行初始化，谁拿到数据谁进行初始化任务，另外从时效原则上来说也是必须的
		 */
		GooViewListener mGooListener = new GooViewListener(mContext, red_point) {
			@Override
			public void onDisappear(PointF mDragCenter) {
				super.onDisappear(mDragCenter);
//				mRemoved.add(position);
//				notifyDataSetChanged();
//				Utils.showToast(mContext,"Cheers! We have get rid of it!");
			}
			@Override
			public void onReset(boolean isOutOfRange) {
				super.onReset(isOutOfRange);
//				notifyDataSetChanged();
//				Utils.showToast(mContext,isOutOfRange ? "Are you regret?" : "Try again!");
			}
		};
		red_point.setOnTouchListener(mGooListener);
	}

	public void initData(){
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_apply_activity://申领活动
			sToast("申领活动");
//			Intent intent = new Intent();
//			intent.setClass(mActivity, TestTryandCatchActivity.class);
//			mActivity.startActivity(intent);
			break;
		case R.id.im_releaseacts://发布活动
			sToast("发布活动");
			ReleaseActWriteInfoFragment f = new ReleaseActWriteInfoFragment();
//			AboutFragment f = new AboutFragment();
			((MainActivity) getActivity()).entrySubFragment(f);
//			Intent intent1 = new Intent();
//			intent1.setClass(mActivity, ReleaseActsActivity.class);
//			mActivity.startActivity(intent1);
			break;
		case R.id.ll_tomystore://我的门店
			sToast("我的门店");
			
//			Intent intent2 = new Intent();
//			intent2.setClass(mActivity, ChooseMyStoreActivity.class);
//			mActivity.startActivity(intent2);
			break;
		case R.id.rl_myacts://我的活动
			sToast("我的活动");
//			Intent intent3 = new Intent();
//			intent3.setClass(mActivity, ActsFourStatusActivity.class);
//			mActivity.startActivity(intent3);
			break;
		case R.id.iv_messagercenter://消息中心
			sToast("消息中心");
//			Intent intent4 = new Intent();
//			intent4.setClass(mActivity, FansAndActivitiesActivity.class);
//			mActivity.startActivity(intent4);
			break;
		case R.id.im_saoyisao://扫一扫
			sToast("扫一扫");
			CaptureFragment cf = new CaptureFragment();
			((MainActivity) getActivity()).entrySubFragment(cf);
//			Intent intent = new Intent();
//			intent.setClass(mActivity, BarCodeTestActivity.class);
//			mActivity.startActivity(intent);
			break;
		case R.id.bt_sureStore://确认门店
			sToast("确认门店");
//			Intent intent = new Intent();
//			intent.setClass(mActivity, InsertInfoActivity.class);
//			mActivity.startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	
	//-------------------------------------暂-------------------------------------------------
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
    //---------------------------------------------------------------------------------------
	//获取网络的操作行为
	@Override
	protected void getNetData(boolean needLoading) {

	}
	

	

}
