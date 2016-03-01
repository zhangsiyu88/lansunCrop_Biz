package com.lansun.qmyo.maijie_biz.activity;



import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.fragment.base.CapitalPlanFragment;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.fragment.base.HelpFragment;
import com.lansun.qmyo.maijie_biz.fragment.base.HomeFragment;
import com.lansun.qmyo.maijie_biz.fragment.base.MineFragment;
import com.lansun.qmyo.maijie_biz.log.FrameLog;
import com.lansun.qmyo.maijie_biz.pageindicator.MainViewPager;
import com.lansun.qmyo.maijie_biz.pageindicator.TabPageIndicator;
import com.lansun.qmyo.maijie_biz.pageindicator.TabsAdapter;
import com.umeng.analytics.MobclickAgent;


public class MainActivity extends FragmentActivity implements OnPageChangeListener{

    private static final String TAG = "Fragment_Name";
	private TabPageIndicator mPageIndicator;
	private MainViewPager mViewPager;
	private TabsAdapter mTabsAdapter;
	private static ViewGroup mFrameViews = null; // 二级以上Fragment
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        mPageIndicator = (TabPageIndicator) findViewById(R.id.main_indicator);
		mViewPager = (MainViewPager) findViewById(R.id.main_pager);
		mViewPager.setOffscreenPageLimit(4);

		mTabsAdapter = new TabsAdapter(this);
		mTabsAdapter.addTab("Home", getString(R.string.home), HomeFragment.class, R.drawable.shoye_selector);
		mTabsAdapter.addTab("CapitalPlan", getString(R.string.capital_plan), CapitalPlanFragment.class, R.drawable.koubeijingying_selector);
		mTabsAdapter.addTab("Mine", getString(R.string.mine), MineFragment.class, R.drawable.wode_selector);
		mTabsAdapter.addTab("Help", getString(R.string.help_center), HelpFragment.class, R.drawable.bangzhuzhongxin_selector);

		mViewPager.setAdapter(mTabsAdapter);
		mPageIndicator.setViewPager(mViewPager, 0);
		/*  底部无需下滑浮标  
		 *  mPageIndicator.setTabCursor(R.drawable.tab_cursor, 50, true);*/
		mPageIndicator.setOnPageChangeListener(this);
		mFrameViews = (ViewGroup) findViewById(R.id.main_frame);
		
//		processPushCommand(getIntent());
//		checkForUpdate();
	}
	
	public void entrySubFragment(Fragment fragment) {
		if (fragment != null && fragment instanceof HeaderFragment) {
			String tag = fragment.getClass().getSimpleName();
			FrameLog.d(TAG, "jumpTo:" + tag);

			mFrameViews.setVisibility(View.VISIBLE);
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_right, R.anim.slide_out_from_right);
			transaction.add(R.id.main_frame, fragment, tag);
			transaction.addToBackStack(tag); // 加入至栈中
			transaction.commitAllowingStateLoss();
		}
	}
	
	public void setCurrPager(int index){
		mViewPager.setCurrentItem(index, false);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
//		processPushCommand(intent);
		
		
	}
	/**
	 * 处理推送的命令
	 * @param intent
	 * @return
	 */
//	private boolean processPushCommand(final Intent intent) {
//		if (intent == null) {
//			return false;
//		}
//		
//		switch (intent.getIntExtra(PushDefine.PUSH_PARAM_TYPE, 0)) {
//		case -1:// type为-1 取消通知
//			CarNotificationManager.getInstance().cancleNotificationByID(CarNotificationManager.NOTIFICATION_ID_INQUIRY);
//			break;
//
//		default:
//			break;
//		}
//		
//		
//		return false;
//	}

	/**
	 * 检测是否需要版本更新
	 */
//	private void checkForUpdate() {
//		DataFetchManager.getInstance().fetchUpdateInfo(new FetchListener() {
//			
//			@Override
//			public void onPreFetch() {
//			}
//			
//			@Override
//			public void onPostFetch(int status, Object... result) {
//				if (status == FetchListener.STATUS_OK) {
//					try {
//						DownloadItem item = (DownloadItem) result[0];
//						int versionCode = SysUtils.getAppVersionCode(CarApp.getAppContext());
//						if(versionCode == -1)
//							return;
//						if(item.version > versionCode) {
//							UpdateDialogFragment updateDialog = new UpdateDialogFragment();
//							Bundle bundle = new Bundle();
//							bundle.putSerializable("key_update_download", item);
//							updateDialog.setArguments(bundle);
//							updateDialog.show(getSupportFragmentManager(), "UpdateDialog");
//						}
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				} else if (status == FetchListener.STATUS_NET_ERROR) {
//					String errMsg = (String) result[0];
//					int errCode = (Integer) result[1];
//					if (errCode == FetchListener.StATUS_OFFLINE) {
//						CommDialogFragment offDialog = new CommDialogFragment();
//						Bundle bundle = new Bundle();
//						bundle.putString("key_offline", errMsg);
//						offDialog.setArguments(bundle);
//						offDialog.show(getSupportFragmentManager(), "OfflineDialog");
//					} else {
////						sToast(errMsg);
//					}
//				} else {
////					show(ResultType.FAIL);
//
//				}
//			}
//		});
//	}

	@Override
	public void onPageScrollStateChanged(int arg0) { }
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) { }
	@Override
	public void onPageSelected(int arg0) { }
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);   
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);    
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			int count = fragmentManager.getBackStackEntryCount();
			if (count > 0) {
				List<Fragment> fList = fragmentManager.getFragments();
				Fragment f = null;
				for (int i = fList.size(); i > 0; i--) {
					if (fList.get(i - 1) != null) {
						f = fList.get(i - 1);
						break;
					}
				}
//				if (f instanceof HeaderFragment) {
//					boolean neetTip = ((HeaderFragment) f).onKeyDown(keyCode);
//					if (neetTip)
//						return true;
//				}
				
				popBackStack();
				return true;
			} else {
				// TODO 需要的话这里可以增加退出对话框
				// 目前主界面back键直接回到桌面
				moveTaskToBack(true);
			}
			
			break;
		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	private void popBackStack() {
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		fragmentManager.popBackStack();
	}

}
