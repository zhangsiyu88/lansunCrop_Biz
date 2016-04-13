package com.lansun.qmyo.maijie_biz.fragment.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;

public class VerifyActivityStateFragment extends HeaderFragment implements OnClickListener{

	
	
	private ViewGroup rootView;
	private Button btn_goback2homepage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_audit_state4activity, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		btn_goback2homepage = (Button) rootView.findViewById(R.id.btn_goback2homepage);	
		btn_goback2homepage.setOnClickListener(this);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	
	
	@Override
	protected String getTitle() {
		return null;
	}

	@Override
	protected int getMenuResId() {
		return 0;
	}

	@Override
	public boolean onKeyDown(int keyCode) {
		return false;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_goback2homepage:
			int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
			for(int i=0;i<count;i++){
				getActivity().getSupportFragmentManager().popBackStack();
			}
		default:
			break;
		}
		super.onClick(v);
	}

}
