package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.FansNoteAdapter;
import com.lansun.qmyo.maijie_biz.bean.FansNoteBean;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshListView;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.umeng.analytics.MobclickAgent;

public class FansNoteFragment extends HeaderFragment implements OnClickListener,OnDateSetListener,TimePickerDialog.OnTimeSetListener {
	
	
	private ViewGroup rootView;
	private Button btn_save_userinfo;
	private PullToRefreshListView lv_reward_note;
	private FansNoteBean fansNoteBean;
	private LinearLayout ll_date;
	private TextView tv_date_year;
	private TextView tv_date_month;
	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;
	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_fans_note, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		
		final Calendar calendar = Calendar.getInstance();
		datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
	    timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
		
		lv_reward_note = (PullToRefreshListView) rootView.findViewById(R.id.lv_reward_note);
		tv_date_month = (TextView) rootView.findViewById(R.id.tv_date_month);
		ll_date = (LinearLayout) rootView.findViewById(R.id.ll_date);
		tv_date_year = (TextView) rootView.findViewById(R.id.tv_date_year);
		ll_date.setOnClickListener(this);
		ArrayList<FansNoteBean> dataList = preData();
		FansNoteAdapter fansNoteAdapter = new FansNoteAdapter(getActivity(), dataList, -1);
		lv_reward_note.setAdapter(fansNoteAdapter);
	}
	
   /* private boolean isVibrate() {
        return ((CheckBox) rootView.findViewById(R.id.checkBoxVibrate)).isChecked();
    }*/

	/**
	 * 模拟数据
	 * @return
	 */
	private ArrayList<FansNoteBean> preData() {
		ArrayList<FansNoteBean> mDataList = new ArrayList<FansNoteBean>();
		for(int i=1;i<100;i++){
			fansNoteBean = new FansNoteBean();
			fansNoteBean.setDate("2016.10."+i);
			fansNoteBean.setRewardCount(10+i);
			fansNoteBean.setAllRewardCount(1000+i);
			mDataList.add(fansNoteBean);
		}
		return mDataList;
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
		switch (v.getId()) {
		case R.id.ll_date:
			 datePickerDialog.setYearRange(1985, 2028);
             datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
			break;
		/*case R.id.ll_date:
            timePickerDialog.show(getActivity().getSupportFragmentManager(), TIMEPICKER_TAG);
			break;*/
		default:
			break;
		}		
		super.onClick(v);
	}

	@Override
	public void onTimeSet(RadialPickerLayout arg0, int hourOfDay, int minute) {
		Toast.makeText(getActivity(), "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDateSet(DatePickerDialog arg0, int year, int month, int day) {
		tv_date_year.setText(year+"年");
		tv_date_month.setText(month+1+"月");
//		Toast.makeText(getActivity(), "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
	}

}
