package com.lansun.qmyo.maijie_biz.fragment.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;

public class VerifyStateFragment extends HeaderFragment implements OnClickListener{

	
	
	private ViewGroup rootView;
	private Button btn_go2lsat_page;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_audit_state, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		btn_go2lsat_page = (Button) rootView.findViewById(R.id.btn_go2lsat_page);	
		btn_go2lsat_page.setOnClickListener(this);
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
		case R.id.btn_go2lsat_page:
			getActivity().getSupportFragmentManager().popBackStack();
			break;
		default:
			break;
		}
		super.onClick(v);
	}

}
