package com.lansun.qmyo.maijie_biz.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.pc.ioc.view.PickerView;
import com.android.pc.ioc.view.PickerView.onSelectListener;
import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.utils.CustomDialog.Builder;
import com.lansun.qmyo.maijie_biz.wheeldialog.wheeldate.WheelMain;

/**
 * TimePicker简单的相关操纵
 * @author  Yeun.Zhang
 *
 */
public class TimerPickerUtils {
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private WheelMain wheelMain;
	private static Activity mContext;
	
	
	

	private static int LAYOUT_RESOURCE = R.layout.default_picker_dialog;
	private static int WINDOW_STYLE = R.style.transparentFrameWindowStyle;
	private static int WINDOW_ANIMATION_STYLE = R.style.PopupWindowAnimation;
	private static int LayoutResourceId = -1;
	private static int WindowStyleId =-1;
	private static int WindowAnimationStyleId =-1;
	
	
	@SuppressWarnings("rawtypes")
	public static List<String> mDataList;
	private int disappearType;
	private int showType;
	private TextView mTextView;
	
	// Time changed flag
	private boolean timeChanged = false;

	// Time scrolled flag
	private boolean timeScrolled = false;
	
	private static OnRoll2SelectListener mListener;
	

	public void setRoll2SelectListener(OnRoll2SelectListener mListener) {
		this.mListener = mListener;
	}

	/** 设置需要修改的控件  */
    public void setAffectView(TextView tv) {
		mTextView = tv;
	}
	private static TimerPickerUtils _instance;
	
	
	public static TimerPickerUtils getInstance(Activity act){
		mContext = act;
		if(_instance==null){
			_instance = new TimerPickerUtils();
		}
		return _instance;
	}
	
	/**
	 * 阶段时间设置
	 */
	public void intervalTimePicker(){
		View rootView = LayoutInflater.from(mContext).inflate(R.layout.time_layout, null);
		final WheelView hours = (WheelView) rootView.findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(mContext, 0, 23));

		final WheelView mins = (WheelView) rootView.findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(mContext, 0, 59, "%02d"));
		mins.setCyclic(true);
		
		final WheelView to_hours = (WheelView) rootView.findViewById(R.id.to_hour);
		to_hours.setViewAdapter(new NumericWheelAdapter(mContext, 0, 23));

		final WheelView to_mins = (WheelView) rootView.findViewById(R.id.to_mins);
		to_mins.setViewAdapter(new NumericWheelAdapter(mContext, 0, 59, "%02d"));
		to_mins.setCyclic(true);
		
		final TimePicker picker = (TimePicker) rootView.findViewById(R.id.time);
		picker.setIs24HourView(true);

		// set current time
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);

		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);
		
		to_hours.setCurrentItem(curHours);
		to_mins.setCurrentItem(curMinutes);

		picker.setCurrentHour(curHours);
		picker.setCurrentMinute(curMinutes);

		// add listeners
//		addChangingListener(mins, "min");
//		addChangingListener(hours, "hour");
//		
//		addChangingListener(to_mins, "min");
//		addChangingListener(to_hours, "hour");

		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					picker.setCurrentHour(hours.getCurrentItem());
					picker.setCurrentMinute(mins.getCurrentItem());
					timeChanged = false;
				}
			}
		};
		
		OnWheelChangedListener wheelListener1 = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					picker.setCurrentHour(to_hours.getCurrentItem());
					picker.setCurrentMinute(to_mins.getCurrentItem());
					timeChanged = false;
				}
			}
		};
		
		
		
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);
		
		to_hours.addChangingListener(wheelListener1);
		to_mins.addChangingListener(wheelListener1);

		
		
		OnWheelClickedListener click = new OnWheelClickedListener() {
			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				wheel.setCurrentItem(itemIndex, true);
			}
		};
		
		OnWheelClickedListener click1 = new OnWheelClickedListener() {
			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				wheel.setCurrentItem(itemIndex, true);
			}
		};
		
		
		
		hours.addClickingListener(click);
		mins.addClickingListener(click);
		
		to_hours.addClickingListener(click1);
		to_mins.addClickingListener(click1);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			@Override
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				picker.setCurrentHour(hours.getCurrentItem());
				picker.setCurrentMinute(mins.getCurrentItem());
				timeChanged = false;
			}
		};
		
		OnWheelScrollListener scrollListener1 = new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			@Override
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				picker.setCurrentHour(to_hours.getCurrentItem());
				picker.setCurrentMinute(to_mins.getCurrentItem());
				timeChanged = false;
			}
		};

		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
		
		to_hours.addScrollingListener(scrollListener1);
		to_mins.addScrollingListener(scrollListener1);

		picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker  view, int hourOfDay, int minute) {
				if (!timeChanged) {
					hours.setCurrentItem(hourOfDay, true);
					mins.setCurrentItem(minute, true);
					
					to_hours.setCurrentItem(hourOfDay, true);
					to_mins.setCurrentItem(minute, true);
				}
			}
		});
		
		Builder builder = new CustomDialog.Builder((Context)mContext);
		builder.setTitle("请选择营业时间").setContentView(rootView);
		builder.setPositiveButton("确定",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					mTextView.setText(
				(hours.getCurrentItem()<10?"0"+hours.getCurrentItem():hours.getCurrentItem())
				+":"
				+(mins.getCurrentItem()<10?"0"+mins.getCurrentItem():mins.getCurrentItem())
				+"--"
				+(to_hours.getCurrentItem()<10?"0"+to_hours.getCurrentItem():to_hours.getCurrentItem())
				+":"
				+(to_mins.getCurrentItem()<10?"0"+to_mins.getCurrentItem():to_mins.getCurrentItem()));
					dialog.dismiss();
				}
			}).setNegativeButton("取消",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).create().show();	
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	/**
	 * 单一事件设置
	 */
	public void popTimePicker(){
		LayoutInflater inflater = LayoutInflater.from(mContext);
		final View timepickerview = inflater.inflate(R.layout.timepicker,null);
		ScreenInfo screenInfo = new ScreenInfo(mContext);
		
		wheelMain = new WheelMain(timepickerview,false);
		wheelMain.screenheight = screenInfo.getHeight();
		
		Calendar calendar = Calendar.getInstance();
//		String time = calendar.get(Calendar.YEAR) + "年"
//				+ (calendar.get(Calendar.MONTH) + 1) + "月"
//				+ calendar.get(Calendar.DAY_OF_MONTH) + "日";
//		
//		if (JudgeDate.isDate(time, "yyyy年MM月dd日")) {
//			try {
//				calendar.setTime(dateFormat.parse(time));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				System.out.println("just goto report bug !");
//			}
//		}
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
							mTextView.setText(wheelMain.getTime());
						}
					}).setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
						}
					}).show();	
	}
	
	
	
	
	
	
	
	
	
	/**
	 * DataPicker的简单弹出和消失操作
	 */
	@SuppressWarnings("unchecked")
	public void popDataPicker(){
		LayoutResourceId = (LayoutResourceId!=-1?LayoutResourceId:LAYOUT_RESOURCE);
		WindowStyleId = (WindowStyleId!=-1?WindowStyleId:WINDOW_STYLE);
		WindowAnimationStyleId = (WindowAnimationStyleId!=-1?WindowAnimationStyleId:WINDOW_ANIMATION_STYLE);
		
		View view = LayoutInflater.from(mContext).inflate(LayoutResourceId, null);
		PickerView pv_setmovment_pv = (PickerView) view.findViewById(R.id.pv_setmovment_pv);
		final TextView tv_set_up = (TextView) view.findViewById(R.id.tv_set_up);
		
		final Dialog dialog = new Dialog(mContext,WindowStyleId);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		
		window.setWindowAnimations(WINDOW_ANIMATION_STYLE);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = 500;
		
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
        //PickerView pv = new PickerView((Activity) mContext);
		//pv.init(0x33000000);
		pv_setmovment_pv.setData(mDataList);//给PickerView设置上需要展示的数据
		dialog.show();
		
		
		//未滑动时，点击提交，需将当前选中位置的数据进行提交
		tv_set_up.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//网络访问
				String selectText = (String) mDataList.get(mDataList.size()/2);
				mListener.roll2select(selectText);
				mTextView.setText(selectText);
				dialog.dismiss();
			}
		});
		
		//pickerView设置上选中的监听器
		pv_setmovment_pv.setOnSelectListener(new onSelectListener() {
			@Override
			public void onSelect(final String text) {
				//滚动中间放入了点击监听
				tv_set_up.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						//网络访问
						mListener.roll2select(text);
						mTextView.setText(text);
						dialog.dismiss();
					}
				});
           }
		});
	}
	
	
	public interface OnRoll2SelectListener{
		public void roll2select(String text);
	}


	
}
