package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.BizCooperationActivity;
import com.lansun.qmyo.maijie_biz.adapter.ClaimStoreAdapter;
import com.lansun.qmyo.maijie_biz.asynctask.http.FetchListener;
import com.lansun.qmyo.maijie_biz.entity.CloseEntity;
import com.lansun.qmyo.maijie_biz.eventbus.EventBus;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.manager.DataFetchManager;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshBase;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshBase.Mode;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshListView;
import com.lansun.qmyo.maijie_biz.utils.AnimUtils;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil.TipAlertDialogCallBack;
import com.umeng.analytics.MobclickAgent;

public class ClaimStoreFragment extends HeaderFragment implements OnClickListener,OnItemClickListener{
	
	
	private ViewGroup rootView;
	private PullToRefreshListView lv_claim_store;
	private ArrayList<HashMap<String, Object>> dataList;
	private ClaimStoreAdapter claimStoreAdapter;
	private RelativeLayout rl_city_search;
	private Button btn_create_store;
	
	private boolean scrollFlag = false;
	private int lastVisibleItemPosition = 0;
	private RelativeLayout rl_create_button;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_claimstore, null);
		rootView = contentView;
//		mTvVersion = (TextView) contentView.findViewById(R.id.tv_version);
		initView();
		initData();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	/**
	 * 进行网络访问，获取网络数据
	 */
	private void initData() {
		
		DataFetchManager.getInstance().fetchUnClaimStoreListInfo("上海","NIKE",new FetchListener() {
			
			@Override
			public void onPreFetch() {
				
			}
			
			@Override
			public void onPostFetch(int status, Object... result) {
				//拿到返回的result对象
				
		
			}
		});
		dataList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap;
		for(int i=0;i<20;i++){
			hashMap = new HashMap<String,Object>();
			hashMap.put("store_name", "商户名称"+i);
			hashMap.put("address", "地址"+i);
			hashMap.put("statue", -1);
			dataList.add(hashMap);
		}
		claimStoreAdapter = new ClaimStoreAdapter(getActivity(),dataList,-1);
		lv_claim_store.setAdapter(claimStoreAdapter);
	}

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	private void initView() {
		TextView tv_city = (TextView) rootView.findViewById(R.id.tv_city);
		rl_city_search = (RelativeLayout) rootView.findViewById(R.id.rl_city_search);
		btn_create_store = (Button) rootView.findViewById(R.id.btn_create_store);
		lv_claim_store = (PullToRefreshListView) rootView.findViewById(R.id.lv_claim_store);
		rl_create_button = (RelativeLayout) rootView.findViewById(R.id.rl_create_button);
		
		
		Drawable drawable= getResources().getDrawable(R.drawable.down_arrow);
		drawable.setBounds(0, 0, drawable.getMinimumWidth()/3, drawable.getMinimumHeight()/3);
		tv_city.setCompoundDrawables(null,null,drawable,null);
		btn_create_store.setOnClickListener(this);
		tv_city.setOnClickListener(this);
		
		lv_claim_store.setOnItemClickListener(this);
		lv_claim_store.setPullToRefreshEnabled(false);
		lv_claim_store.setMode(Mode.PULL_UP_TO_REFRESH);
		lv_claim_store.setOnRefreshListener(new OnRefreshListener2() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				HashMap<String, Object> hashMap;
				for(int i=20;i<40;i++){
					hashMap = new HashMap<String,Object>();
					hashMap.put("store_name", "商户名称"+i);
					hashMap.put("address", "地址"+i);
					hashMap.put("statue", 1);
					dataList.add(hashMap);
					claimStoreAdapter.notifyDataSetChanged();
				}
			}
		});
//		ViewTreeObserver vto = lv_claim_store.getViewTreeObserver();
//		vto.addOnScrollChangedListener(new OnScrollChangedListener() {
//			
//			@Override
//			public void onScrollChanged() {
//				//0.当ListView的出于向下滑动时，上面搜索栏目和下面的创建门店按钮皆不可见
//				
//				
//			}
//		});
		
		
		lv_claim_store.setOnScrollListener(new OnScrollListener() {
			 
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                switch (scrollState) {
                // 当不滚动时
                case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                   scrollFlag = false;
                   if( ((ListView)(lv_claim_store.mRefreshableView)).getLastVisiblePosition()
                		   == ((ListView)(lv_claim_store.mRefreshableView)).getCount()-1){
//                	   rl_city_search.setVisibility(View.VISIBLE);
                	   rl_create_button.setVisibility(View.VISIBLE);
                   }
                   if(((ListView)(lv_claim_store.mRefreshableView)).getFirstVisiblePosition() == 0){
//                	   rl_city_search.setVisibility(View.VISIBLE);
                	   rl_create_button.setVisibility(View.VISIBLE);
                   }
                    break;
                case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                    scrollFlag = true;
                    break;
                case OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                    scrollFlag = false;
                    break;
                }
            }
 
            /**
             * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
             * visibleItemCount：当前能看见的列表项个数（小半个也算） totalItemCount：列表项共数
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                // 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
                if (scrollFlag) {//&& ScreenUtil.getScreenViewBottomHeight(lv_claim_store) >= ScreenUtil.getScreenHeight(getActivity())
                    
					if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
//                    	rl_city_search.setVisibility(View.VISIBLE);
						if((rl_create_button.getVisibility()==View.GONE)){
//                    		AnimUtils.startTopOutAnim(getActivity(),rl_city_search);
                    		AnimUtils.startBottomInAnim(getActivity(),rl_create_button);
                    	}
						
                    	rl_create_button.setVisibility(View.VISIBLE);
                    } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
                    	//消失的方式升级为 滑动出去，同样展示的方式也应该是这样滑动
                    	if(!(rl_create_button.getVisibility()==View.GONE)){
//                    		AnimUtils.startTopOutAnim(getActivity(),rl_city_search);
                    		AnimUtils.startBottomOutAnim(getActivity(),rl_create_button);
                    	}
                    } else {
                        return;
                    }
                    lastVisibleItemPosition = firstVisibleItem;
                }
            }
        });
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
		Fragment f;
		switch(v.getId()){
		case R.id.tv_city:
			break;
		
		
		case R.id.btn_create_store:
			f = new CreateNewStoreFragment();
		    ((BizCooperationActivity)getActivity()).entrySubFragment(f);			
		    break;
		}
		super.onClick(v);
	}

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//1、弹出对话框，询问是否认领（对应的Item信息）
		DialogUtil.createStoreClaimDialog(mContext, "Meliferart(新天地店)\n上海市黄浦区人民广场巴拉巴拉~~", new TipAlertDialogCallBack() {
			
			@Override
			public void onPositiveButtonClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//0.当前页面的销毁，数据写入本地数据库中
				int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
				if(count!=0){
					for(int i=0;i<=count;i++){
						getActivity().getSupportFragmentManager().popBackStack();
					}
				}
				//1.通知商户合作页面的数据回显
				
				EventBus.getDefault().post("来自列表选中");
			}
			@Override
			public void onNegativeButtonClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}
}
