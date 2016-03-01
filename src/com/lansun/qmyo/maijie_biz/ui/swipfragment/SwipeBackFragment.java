
package com.lansun.qmyo.maijie_biz.ui.swipfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class SwipeBackFragment extends Fragment implements SwipeBackFragmentBase {
    private SwipeBackFragmentHelper mHelper;
    protected ViewGroup mContentView = null;
    protected View mLoadingView = null;
    
    public static final String KEY_OFFLINE = "key_offline";
    public static final String KEY_GOON_BID_ID = "key_goon_bid_id";
    public static final String KEY_OVERMONEY_TITLE = "key_overmoney_title";
    public static final String KEY_OVERMONEY_CONTENT = "key_overmoney_content";
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mHelper = new SwipeBackFragmentHelper(this);
        mHelper.onFragmentCreateView();
    }

//    public View findViewById(int id) {
//        View v = getView().findViewById(id);
//        if (v == null && mHelper != null)
//            return mHelper.findViewById(id);
//        return v;
//    }

    
    @Override
	public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }
    
    @Override
    public void setSwipeBackEnable(boolean enable) {
    	getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        getSwipeBackLayout().scrollToFinishActivity();
    }
    
    @Override
    public void close(){
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.popBackStack();
    }
    
    public void sToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	public void lToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
	
	public enum ResultType {
		LOADING, RESULT, FAIL, EMPTY;
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
