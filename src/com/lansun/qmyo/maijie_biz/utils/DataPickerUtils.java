package com.lansun.qmyo.maijie_biz.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.pc.ioc.view.PickerView;
import com.android.pc.ioc.view.PickerView.onSelectListener;
import com.lansun.qmyo.maijie_biz.R;

/**
 * DataPicker简单的相关操纵
 * @author  Yeun.Zhang
 *
 */
public class DataPickerUtils {

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
	private static Activity mContext;
	
	private static OnRoll2SelectListener mListener;
	

	public void setRoll2SelectListener(OnRoll2SelectListener mListener) {
		this.mListener = mListener;
	}

	/** 设置需要修改的控件  */
    public void setAffectView(TextView tv) {
		mTextView = tv;
	}
	private static DataPickerUtils _instance;
	public Activity getmContext() {
		return mContext;
	}
	public void setmContext(Activity mContext) {
		this.mContext = mContext;
	}
	public List getmDataList() {
		return mDataList;
	}
	public void setmDataList(ArrayList<String> mDataList) {
		this.mDataList = mDataList;
	}
	public int getDisappearType() {
		return disappearType;
	}
	public void setDisappearType(int disappearType) {
		this.disappearType = disappearType;
	}
	public int getShowType() {
		return showType;
	}
	public void setShowType(int showType) {
		this.showType = showType;
	}
	
	public static DataPickerUtils getInstance(Activity act, ArrayList list){
		mDataList = list;
		mContext = act;
		if(_instance==null){
			_instance = new DataPickerUtils();
		}
		return _instance;
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
