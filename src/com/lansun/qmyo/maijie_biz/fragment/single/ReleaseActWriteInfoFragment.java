package com.lansun.qmyo.maijie_biz.fragment.single;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.bean.ActsDegreeBean;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.wheeldialog.wheeldate.JudgeDate;
import com.lansun.qmyo.maijie_biz.wheeldialog.wheeldate.ScreenInfo;
import com.lansun.qmyo.maijie_biz.wheeldialog.wheeldate.WheelMain;
import com.lansun.qmyo.maijie_biz.wheeldialog.widget.ActionSheetDialog;
import com.lansun.qmyo.maijie_biz.wheeldialog.widget.ActionSheetDialog.OnSheetItemClickListener;
import com.lansun.qmyo.maijie_biz.wheeldialog.widget.ActionSheetDialog.SheetItemColor;

@SuppressLint("SimpleDateFormat") 
public class ReleaseActWriteInfoFragment extends HeaderFragment implements OnClickListener {

	private RelativeLayout addDesc;
	private LinearLayout actsdetailsdesc;
	private EditText et_actsdetailsdesc;
	private EditText et_content;
	public int i = 0;
	public ArrayList<EditText> editList;
	private Button bt_nextstep;
	public StringBuffer sb = new StringBuffer();
	private TextView hejin;
	CharSequence clickItem;
	public ArrayList<ActsDegreeBean> degreeList;
	public int j = 0;
	private RadioGroup rg_days;
	private RadioGroup rg_isNew;
	private TextView actsclassify;
	private TextView actsendtime;
	private TextView actsstarttime;
	private RadioButton rb_days_1;
	private RadioButton rb_days_2;
	private RadioButton rb_isNew_1;
	private RadioButton rb_isNew_2;
	public Boolean isSevenDays = true;
	public Boolean isNew = true;
	WheelMain wheelMain;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private int[] pureCodeTimes;
	private ViewGroup contentView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_releaseacts_writeinfo, null);
		contentView = view;
		initView();
		return super.onCreateView(inflater, view, savedInstanceState);
	}
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		et_content.requestFocus();
		
		show(ResultType.LOADING);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						show(ResultType.RESULT);}});}}, 2000);
	}
	
	
	
	
	
	
	
	
	@Override
	protected String getTitle() {
		return null;
	}

	@Override
	protected int getMenuResId() {
		return -1;
	}

	@Override
	public boolean onKeyDown(int keyCode) {
		return false;
	}

	public void initView() {

		addDesc = (RelativeLayout) contentView.findViewById(R.id.rl_clicktoadd_actsdesc);
		actsdetailsdesc = (LinearLayout)  contentView.findViewById(R.id.ll_addactsdetails_desc);
		et_actsdetailsdesc = (EditText)  contentView.findViewById(R.id.et_actsdetailsdesc);
		et_content = (EditText)  contentView.findViewById(R.id.et_content);
		
		bt_nextstep = (Button)  contentView.findViewById(R.id.bt_nextstep);
		
		editList = new ArrayList<EditText>();
		editList.add(et_actsdetailsdesc);//--第一个EditText不是自动生成的，需要手动先添加进去
		
		addDesc.setOnClickListener(this);
		bt_nextstep.setOnClickListener(this);
		
		
		rg_days = (RadioGroup)  contentView.findViewById(R.id.rg_days);
		rb_days_1 = (RadioButton)  contentView.findViewById(R.id.rb_days_1);
		rb_days_1.setOnClickListener(this);
		rb_days_2 = (RadioButton)  contentView.findViewById(R.id.rb_days_2);
		rb_days_2.setOnClickListener(this);
		rb_isNew_1 = (RadioButton)  contentView.findViewById(R.id.rb_isNew_1);
		rb_isNew_2 = (RadioButton)  contentView.findViewById(R.id.rb_isNew_2);
		
		rg_isNew = (RadioGroup) contentView.findViewById(R.id.rg_isNew);
		actsclassify = (TextView) contentView.findViewById(R.id.tv_actsclassify_write);
		actsendtime = (TextView) contentView.findViewById(R.id.tv_actsendtime);
		actsstarttime = (TextView) contentView.findViewById(R.id.tv_actsstarttime);
		Calendar calendar = Calendar.getInstance();
		actsstarttime.setText(calendar.get(Calendar.YEAR) + "年"
				+ (calendar.get(Calendar.MONTH) + 1) + "月"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "日");
		actsstarttime.setOnClickListener(this);
		
		hejin = (TextView)  contentView.findViewById(R.id.tv_heijin);
		hejin.setOnClickListener(this);
		degreeList = new ArrayList<ActsDegreeBean>();
		for(int i=0;i<10;i++){
			ActsDegreeBean actsDegreeBean = new ActsDegreeBean();
			actsDegreeBean.setStr("黑金"+(i+1));
			degreeList.add(i,actsDegreeBean);
		}
		actsclassify .setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
		case R.id.rl_clicktoadd_actsdesc:
			
			EditText editText = new EditText(mContext);
			editText.setBackgroundResource(R.drawable.actsdetails__corner_rectangle);
			editText.setHint("同上");
			editText.setTextSize(12);
			editText.setPadding(10, 10, 10, 10);
			editText.setGravity(Gravity.TOP);
			
			LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 200);
			layoutParams.setMargins(20, 10, 20, 0);
			actsdetailsdesc.addView(editText,layoutParams);
			
			//每生成一个Edittext对象便将其送入到这个列表中，当要离开页面发送数据时，我们再从这个editList中一一拿出每个EditText对象
			//将这几个EditText中的内容拼接在一起
			editList.add(editText);
			break;
	
			
		case R.id.bt_nextstep:
			
			for (EditText et: editList) {
				//Toast.makeText(ReleaseActsActivity.this, et.getText().toString(), Toast.LENGTH_LONG).show();
				sb.append(et.getText().toString());
			}
			//试验成功！
			lToast(sb.toString());
			break;
			
			case R.id.tv_heijin:
				//只要再一次点击，j这个全局变量必须重新归零
				 j=0;
				
				//先将SheetItem的数据存到链表中，作为数据容器
				//当我再Dialog中进行addSheetItem时，依次取出对应的List中的数据放进去
				//这样根据点击Item的位置就可以拿到list中对应位置的
				ActionSheetDialog actionSheetDialog = new ActionSheetDialog(mContext);
				
				ActionSheetDialog builder = actionSheetDialog.builder();
				
				builder.setTitle("请选择活动等级")
				.setCancelable(false)
				.setCanceledOnTouchOutside(false);
				
				//构建每次的要展开的Dialog
				for(j = 0;j<8;j++){
				builder.addSheetItem(((ActsDegreeBean) degreeList.get(j)).getStr(), SheetItemColor.Blue,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								hejin.setText(((ActsDegreeBean) degreeList.get(which-1)).getStr());
							}
						});
				}
				//构造完成后进行展示
				builder.show();
				break;
				
				case R.id.tv_actsclassify_write:
				//二级联动的WheelView   大类、小类
					
				break;
				
				case R.id.tv_actsstarttime:
				//五级级联动的WheelView界面  年、月、日、时、分
					LayoutInflater inflater = LayoutInflater.from(mContext);
					final View timepickerview = inflater.inflate(R.layout.timepicker,null);
					ScreenInfo screenInfo = new ScreenInfo((Activity) mContext);
					
					wheelMain = new WheelMain(timepickerview);
					wheelMain.screenheight = screenInfo.getHeight();
					
					String time = actsstarttime.getText().toString();
					
					Calendar calendar = Calendar.getInstance();
					if (JudgeDate.isDate(time, "yyyy年MM月dd日")) {
						try {
							calendar.setTime(dateFormat.parse(time));
						} catch (ParseException e) {
							e.printStackTrace();
							System.out.println("just goto report bug !");
						}
					}
					int year = calendar.get(Calendar.YEAR);
					int month = calendar.get(Calendar.MONTH);
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					int hour = calendar.get(Calendar.HOUR_OF_DAY);
					int min = calendar.get(Calendar.MINUTE);
					
					wheelMain.initDateTimePicker(year, month, day, hour, min);
					
					new AlertDialog.Builder(mContext)
							.setTitle("选择活动时间")
							.setView(timepickerview)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											//txttime.setText(wheelMain.getTime());
											actsstarttime.setText(wheelMain.getTime());
											
											//此处还应将其结束时间计算出来,回显到actsendtime的TextView上
											showTheActEndTime();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,
												int which) {
										}
									}).show();	
					
						
				break;
				
				case R.id.rb_days_1:
					//  选择7天
					rb_days_2.setChecked(false);
					rb_days_1.setChecked(true);
					isSevenDays = true;
					if(pureCodeTimes != null){
						StringBuffer sb_seven = ComputeTheActsEndTime(pureCodeTimes);
						actsendtime.setText(sb_seven);
					}
					
				
					
					//获取到当前结束时间上的值，将其
					break;
				case R.id.rb_days_2:
					// 选择 30天
					rb_days_1.setChecked(false);
					rb_days_2.setChecked(true);
					isSevenDays = false;
					if(pureCodeTimes != null){
						StringBuffer sb_thirty = ComputeTheActsEndTime(pureCodeTimes);
						actsendtime.setText(sb_thirty);
					}
					break;
				case R.id.rb_isNew_1:
					//  选择普通
					rb_isNew_1.setChecked(true);
					rb_isNew_2.setChecked(false);
					isNew = false;
					break;
				case R.id.rb_isNew_2:
					// 选择 新品
					rb_isNew_1.setChecked(false);
					rb_isNew_2.setChecked(true);
					isNew = true;
					break;
			
		default:
			break;
		}
	}
	/*
	 * 根据选中的活动时间的长度，计算得出活动结束的时间
	 */
	protected void showTheActEndTime() {
		
		   //在WheelMain中定义了一个对外开发时间值的方法getPureCodeTime
		   //将这个pureCodeTimes数组设计成全局变量，即当用户一旦点击过时间选择器之后，那么他选中的时间（纯数字格式）就会被保存起来，供后面点击使用
			pureCodeTimes = wheelMain.getPureCodeTime();
			StringBuffer sb = ComputeTheActsEndTime(pureCodeTimes);
			actsendtime.setText(sb);
		}


	public StringBuffer ComputeTheActsEndTime(int[] codeTime) {
		int year = pureCodeTimes[0];
		int month = pureCodeTimes[1];
		int day = pureCodeTimes[2];
		int hour = pureCodeTimes[3];
		int min = pureCodeTimes[4];
		
		if(isSevenDays ){//即活动时间为7天
			day = day+7;
		}else {
			day = day+30;
			} 
		
		if(month == 1|month == 3|month == 5|month ==7|month ==8|month ==10|month == 12){//大月份
			if(day>31){
				++month;
				if(month >12){
					++year;
					month=month-12;
					
				}
				day = day-31;
			}
		}else if(month == 4|month == 6|month == 9|month ==11){
			if(day>30){
				month = month+1;
				day = day-30;
			}
		}else if (month == 2){
			if(year%4==0||year%400==0){
				if(day>28){
					++month;
					day = day-29;
			    }
			}else{
		    	++month;
				day = day-28;
		    }
			
		 }
		
		StringBuffer sb = new StringBuffer();
		sb.append(year).append("年")
			.append(month).append("月")
			.append(day).append("日 ")
			.append(hour).append(":")
			.append(min);
		return sb;
	}	
}
