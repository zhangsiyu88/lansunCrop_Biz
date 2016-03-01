package com.lansun.qmyo.maijie_biz.ui.swipfragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class SwipeBackFragmentHelper {

	private SwipeBackFragment mFragment;
    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackFragmentHelper(SwipeBackFragment fragment) {
    	mFragment = fragment;
    }
    
    public void onFragmentCreateView(){
        //mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(mFragment.getActivity()).inflate(R.layout.frame_swipeback,null);
    	
    	//use code to replace with resource xml(R.layout.frame_swipeback) modify by wangxudong
    	mSwipeBackLayout = new SwipeBackLayout(mFragment.getActivity());
    	FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
    			ViewGroup.LayoutParams.MATCH_PARENT);
    	mSwipeBackLayout.setLayoutParams(fLayoutParams);
    	
        mSwipeBackLayout.attachToFragment(mFragment);//将新生成的SwipeBackLayout对象和mFragment的 getView对象扯上关联
    }
    
    public View findViewById(int id) {
        if (mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return null;
    }
    
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

}
