package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.utils.TimerPickerUtils;
import com.umeng.analytics.MobclickAgent;

public class BizInfoFragment extends HeaderFragment implements OnClickListener {
	
	
	private ViewGroup rootView;
	private TextView tv_business_duration;
	private RelativeLayout rl_business_duration;
	private RelativeLayout rl_store_phone1,rl_store_phone2;
	private ArrayList<EditText> editList = new ArrayList<EditText>();

	private LinearLayout ll_store_phone1;
	private int addEditCount = 0;
	private Button btn_save_userinfo;
	private EditText et_store_phone1;
	private View line_split2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_biz_info, null);
		rootView = contentView;
		initView();
		
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		rl_business_duration = (RelativeLayout) rootView.findViewById(R.id.rl_business_duration);
		rl_store_phone2 = (RelativeLayout) rootView.findViewById(R.id.rl_store_phone2);
		rl_store_phone1 = (RelativeLayout) rootView.findViewById(R.id.rl_store_phone1);
		ll_store_phone1 = (LinearLayout) rootView.findViewById(R.id.ll_store_phone1);
		btn_save_userinfo = (Button) rootView.findViewById(R.id.btn_save_userinfo);
		et_store_phone1 = (EditText) rootView.findViewById(R.id.et_store_phone1);
		line_split2 = rootView.findViewById(R.id.line_split2);
		
		tv_business_duration = (TextView) rootView.findViewById(R.id.tv_business_duration);
		rl_business_duration.setOnClickListener(this);
		rl_store_phone2.setOnClickListener(this);
		btn_save_userinfo.setOnClickListener(this);
		editList.add(et_store_phone1);
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.rl_business_duration:
            TimerPickerUtils instance = TimerPickerUtils.getInstance(getActivity());
            instance.setAffectView(tv_business_duration);
            instance.intervalTimePicker();
			break;
			
		case R.id.rl_store_phone2:
			if(addEditCount>=3){
				lToast("电话整那么多干啥，想干啥？");
				rl_store_phone2.setVisibility(View.GONE);
				line_split2.setVisibility(View.GONE);
				break;
			}
			EditText editText = new EditText(mContext);
			editText.setBackgroundResource(R.drawable.actsdetails__corner_rectangle);
			editText.setHint("附加联系方式");
			editText.setTextSize(14);
			editText.setPadding(10, 10, 10, 10);
			editText.setGravity(Gravity.TOP);
			
			LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 100);
			layoutParams.setMargins(20, 10, 20, 0);
			ll_store_phone1.addView(editText,layoutParams);
			
			//每生成一个Edittext对象便将其送入到这个列表中,当要离开页面发送数据时
			//我们再从这个editList中一一拿出每个EditText对象,将这几个EditText中的内容拼接在一起
			editList.add(editText);
			addEditCount++;
			break;	
		case R.id.btn_save_userinfo	:
			/** 将所有EditText中的文字写入进来*/
			StringBuilder sb = new StringBuilder();
			for(EditText e:editList){
				String str = e.getText().toString();
				sb.append(str);
			}
			lToast(sb.toString());
			break;
		default:
			break;
		}
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

}
