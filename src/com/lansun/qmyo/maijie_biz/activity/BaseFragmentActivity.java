package com.lansun.qmyo.maijie_biz.activity;



import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;

public class BaseFragmentActivity extends FragmentActivity {

	
	public FrameLayout  frameView;
	public int frame_id;
	static String TAG ="BaseFragmentActivity";
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  FragmentManager fm = getSupportFragmentManager();
	  int index = requestCode >> 16;
	  if (index != 0) {
	   index--;
	   if (fm.getFragments() == null || index < 0
	     || index >= fm.getFragments().size()) {
	    Log.w(TAG, "Activity result fragment index out of range: 0x"
	      + Integer.toHexString(requestCode));
	    return;
	   }
	   Fragment frag = fm.getFragments().get(index);
	   if (frag == null) {
	    Log.w(TAG, "Activity result no fragment exists for index: 0x"
	      + Integer.toHexString(requestCode));
	   } else {
	    handleResult(frag, requestCode, resultCode, data);
	   }
	   return;
	  }

	 }

	 /**
	  * 递归调用，对所有子Fragement生效
	  * 
	  * @param frag
	  * @param requestCode
	  * @param resultCode
	  * @param data
	  */
	 private void handleResult(Fragment frag, int requestCode, int resultCode,
	   Intent data) {
	  frag.onActivityResult(requestCode & 0xffff, resultCode, data);
	  List<Fragment> frags = frag.getChildFragmentManager().getFragments();
	  if (frags != null) {
	   for (Fragment f : frags) {
	    if (f != null)
	     handleResult(f, requestCode, resultCode, data);
	   }
	 }
	}
		
	 
	 
	public void entrySubFragment(Fragment fragment) {

		if (fragment != null && fragment instanceof HeaderFragment) {
			String tag = fragment.getClass().getSimpleName();

			frameView.setVisibility(View.VISIBLE);
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			//transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_right, R.anim.slide_out_from_right);
			transaction.add(frame_id, fragment, tag);
			transaction.addToBackStack(tag); // 添加回退栈
			transaction.commitAllowingStateLoss();
		}
	}
}
