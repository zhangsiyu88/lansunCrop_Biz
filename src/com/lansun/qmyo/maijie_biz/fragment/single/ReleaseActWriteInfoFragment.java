package com.lansun.qmyo.maijie_biz.fragment.single;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.lansun.qmyo.maijie_biz.activity.MainActivity;
import com.lansun.qmyo.maijie_biz.adapter.DetailHeaderDeletePagerAdapter;
import com.lansun.qmyo.maijie_biz.adapter.UpLoadPhotoAdapter;
import com.lansun.qmyo.maijie_biz.bean.ActivityInfoBean;
import com.lansun.qmyo.maijie_biz.bean.ActsDegreeBean;
import com.lansun.qmyo.maijie_biz.db.DataBaseManager;
import com.lansun.qmyo.maijie_biz.db.DbInfos;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.utils.BitmapUtils;
import com.lansun.qmyo.maijie_biz.utils.TimerPickerUtils;
import com.lansun.qmyo.maijie_biz.view.CustomToast;
import com.lansun.qmyo.maijie_biz.view.ImageGalleryDeleteDialog;
import com.lansun.qmyo.maijie_biz.view.ImageGalleryDeleteDialog.OnNotifyGridViewListener;
import com.lansun.qmyo.maijie_biz.view.MyGridView;
import com.lansun.qmyo.maijie_biz.wheeldialog.wheeldate.WheelMain;
import com.lansun.qmyo.maijie_biz.wheeldialog.widget.ActionSheetDialog;
import com.lansun.qmyo.maijie_biz.wheeldialog.widget.ActionSheetDialog.OnSheetItemClickListener;
import com.lansun.qmyo.maijie_biz.wheeldialog.widget.ActionSheetDialog.SheetItemColor;
import com.ns.mutiphotochoser.GalleryActivity;
import com.ns.mutiphotochoser.constant.Constant;

@SuppressLint("SimpleDateFormat") 
public class ReleaseActWriteInfoFragment extends HeaderFragment implements OnClickListener,OnNotifyGridViewListener {

	private Uri imageUri;
	private RelativeLayout addDesc;
	private LinearLayout actsdetailsdesc;
	private EditText et_actsdetailsdesc,et_content;
	public int i = 0;
	public ArrayList<EditText> editList;
	private Button bt_nextstep;
	public StringBuffer sb = new StringBuffer();
	private TextView hejin;
	CharSequence clickItem;
	public ArrayList<ActsDegreeBean> degreeList;
	public int j = 0;
	private RadioGroup rg_days,rg_isNew;
	private TextView actsclassify,actsendtime,actsstarttime;
	private RadioButton rb_days_1,rb_days_2,rb_isNew_1,rb_isNew_2;
	
	public Boolean isSevenDays = true;
	public Boolean isNew = true;
	WheelMain wheelMain;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private int[] pureCodeTimes;
	private ViewGroup contentView;
	private MyGridView gv_images;
	private LayoutInflater inflater ;
	private Activity activity;
	protected static final int ACTION_IMAGE_CAPTURE = 2;
	protected static final int ACTION_IMAGE_PICK = 1;
	private ArrayList<String> files = new ArrayList<>();
	private UpLoadPhotoAdapter adapter;
	private DetailHeaderDeletePagerAdapter headPagerAdapter;
	private ImageGalleryDeleteDialog imageGalleryDeleteDialog;
	private LinearLayout ll_photo_upload;
	private SQLiteDatabase db;
	private EditText et_userules_desc;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		db = DataBaseManager.getInstance().getDataBase(DbInfos.DB_NAME);
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_releaseacts_writeinfo, null);
		contentView = view;
		activity = getActivity();
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
						show(ResultType.RESULT);}});}}, 500);
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
		et_userules_desc = (EditText)  contentView.findViewById(R.id.et_userules_desc);
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
		
		hejin = (TextView)contentView.findViewById(R.id.tv_heijin);
		hejin.setOnClickListener(this);
		degreeList = new ArrayList<ActsDegreeBean>();
		for(int i=0;i<10;i++){
			ActsDegreeBean actsDegreeBean = new ActsDegreeBean();
			actsDegreeBean.setStr("黑金"+(i+1));
			degreeList.add(i,actsDegreeBean);
		}
		actsclassify .setOnClickListener(this);
	
		
		adapter = new UpLoadPhotoAdapter(activity, files);
		ll_photo_upload = (LinearLayout) contentView.findViewById(R.id.ll_photo_upload);
		ll_photo_upload.setOnClickListener(this);
		gv_images = (MyGridView) contentView.findViewById(R.id.gv_images);
		gv_images.setAdapter(adapter);
		/**
		 * 给GridView设置上条目监听事件
		 */
		gv_images.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position < 8){
					if(position == adapter.getCount()-1){//点击的位置小于8（即个数小于9），且是所填图的最后一个时
						upDataHead();
						return;
					}
				}else if(position == 9){//点击对象为9张图片中的最后一张图像
					View layout = LayoutInflater.from(activity).inflate(R.layout.custom_toast, null);
		    		TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
		    		TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
		    		tv_title.setText("选择上传的照片已满");
		    		tv_content.setText("最多上传9张照片哦");
		    		Toast toast = new Toast(getActivity());
		    		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		    		toast.setDuration(0);
		    		toast.setView(layout);
		    		toast.show();
		    		
//		    		openPhotoDetails(8);
		    		return;
				}
				 openPhotoDetails(position);
			}
		});
		
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
			
			
			
			//将表格中的信息写入实体类中
			 ActivityInfoBean actInfo= arrangeToEntity();
			//将输入进入数据库表中
			writeIntoDb(actInfo);
			
			
			Fragment f = new ReleaseActsCheckedInfoFragment();
			((MainActivity)getActivity()).entrySubFragment(f);
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
					TimerPickerUtils.getInstance(getActivity()).setAffectView(actsstarttime);
					TimerPickerUtils.getInstance(getActivity()).setAffectSecondTextView(actsendtime);
					TimerPickerUtils.getInstance(getActivity()).startTimePicker();
				    break;
				case R.id.rb_days_1://  选择7天
					rb_days_2.setChecked(false);
					rb_days_1.setChecked(true);
					isSevenDays = true;
					if(pureCodeTimes != null){
						StringBuffer sb_seven = ComputeTheActsEndTime(pureCodeTimes);
						actsendtime.setText(sb_seven);
					}
					break;
				case R.id.rb_days_2:// 选择 30天
					rb_days_1.setChecked(false);
					rb_days_2.setChecked(true);
					isSevenDays = false;
					if(pureCodeTimes != null){
						StringBuffer sb_thirty = ComputeTheActsEndTime(pureCodeTimes);
						actsendtime.setText(sb_thirty);
					}
					break;
				case R.id.rb_isNew_1://  选择普通
					rb_isNew_1.setChecked(true);
					rb_isNew_2.setChecked(false);
					isNew = false;
					break;
				case R.id.rb_isNew_2:// 选择 新品
					rb_isNew_1.setChecked(false);
					rb_isNew_2.setChecked(true);
					isNew = true;
					break;
				case R.id.ll_photo_upload:
					upDataHead();
					break;
				default:
					break;
		}
	}
	
	
	private ActivityInfoBean arrangeToEntity() {
		ActivityInfoBean bean = new ActivityInfoBean();
		ArrayList<String> detailsList = new ArrayList<String>();
		ArrayList<String> pathsList = new ArrayList<String>();
		ArrayList<String> rulesList = new ArrayList<String>();
		pathsList = files;
		rulesList.add(et_userules_desc.getEditableText().toString());
		
		
		
		bean._name          = "MuYoU";
		bean._title         = et_content.getEditableText().toString();
		bean._degree        = (String) hejin.getText();
		bean._classfication = (String) actsclassify.getText();
		bean._duration      = (isSevenDays?String.valueOf(7):String.valueOf(30));
		bean._end           = (String) actsendtime.getText();
		bean._start         = (String) actsstarttime.getText();
		bean._tag           = (isNew?"新品":"普通");
		bean._picture_path  = pathsList;
		bean._rules         = rulesList;
		bean._detail        = detailsList;
		
		return bean;
	}


	private void writeIntoDb(ActivityInfoBean bean) {
		//活动名称
		//活动级别
		//活动分类
		//活动标签
		//活动标题
		//活动详情描述
		//使用规则
		//活动图片的本地path   
		//活动期限                                   7天 or 30天
		//活动开始时间
		//活动结束时间
		
		
		StringBuffer sb_details = new StringBuffer();
		for (EditText et: editList) {
			sb_details.append(et.getText().toString());
			sb_details.append("%^&");
		}
		String details = sb_details.toString();
		
		//需单独处理bean._picture_path的图片地址类型为String
		ArrayList<String> _picture_path = bean._picture_path;
	    StringBuffer sb_pic_paths = new StringBuffer(); 
		
	    for(int i = 0;i<_picture_path.size();i++){
	    	sb_pic_paths.append(_picture_path.get(i));
	    	if(i!=_picture_path.size()-1){
	    		sb_pic_paths.append("%^&");//在每个图片路径的后面加上"%^&"号作为分隔符
	    	}
		}
	     String pic_paths = sb_pic_paths.toString();
		
		//以事物的方式将内容写入本地数据库
		long start = System.currentTimeMillis();
		db.beginTransaction();
		try {
			/*for (int i = 0; i < 2000; i++) {*/
				//注意下面写入数据表中的数据格式，该加""的记得加双引号""
				/*db.execSQL("insert into "+DbInfos.ReleaseActivityTableField.TB_NAME+
						" (" +
						DbInfos.ReleaseActivityTableField.ACTIVITY_NAME+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_DEGREE+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_CLASSFICATION+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_TAG+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_TITLE+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_DETAIL+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_RULES+"," +
						DbInfos.ReleaseActivityTableField.PICTURE_PATH+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_DURATION+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_START+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_END+
						")" +
						"values ("+bean._name+i+ ","
								  +bean._degree+i+","
								  +bean._classfication+i+","
								  +bean._tag+i+","
								  +bean._title+i+","
								  
								  +details+i+","//使用修整过后的多条详情描述
								  +bean._rules.get(0)+i+","
								  +pic_paths+i+","//使用修整过后的多张图片的地址

								  +bean._duration+i+","
								  +bean._start+i+","
								  +bean._end+i+ ")");*/
						
//						"values ('呷哺呷哺"+i+"',"+"'新天地"+i+"',"+"'新世界城4楼"+i+"',"+"'上海"+i+"',"+"'人民广场"+i+ "')" );
//						  ("呷哺呷哺100"，"新天地100"，"新世界城100"，"上海100"，"人民广场100")
				
				db.execSQL("insert into "+DbInfos.ReleaseActivityTableField.TB_NAME+
						" (" +
						DbInfos.ReleaseActivityTableField.ACTIVITY_NAME+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_DEGREE+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_CLASSFICATION+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_TAG+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_TITLE+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_DETAIL+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_RULES+"," +
						DbInfos.ReleaseActivityTableField.PICTURE_PATH+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_DURATION+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_START+"," +
						DbInfos.ReleaseActivityTableField.ACTIVITY_END+
						")" +
						"values ('"+bean._name+i+ "','"
								  +bean._degree+i+"','"
								  +bean._classfication+i+"','"
								  +bean._tag+i+"','"
								  +bean._title+i+"','"
								  
								  +details+i+"','"//使用修整过后的多条详情描述
								  +bean._rules.get(0)+i+"','"
								  +pic_paths+"','"//使用修整过后的多张图片的地址

								  +bean._duration+i+"','"
								  +bean._start+i+"','"
								  +bean._end+i+ "')");
				
				
			/*}*/
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			long duration = System.currentTimeMillis() - start;
			System.out.println("插入完毕, 总共用时: " + duration);
		}
	}

	/*
	 * 根据选中的活动时间的长度，计算得出活动结束的时间
	 */
	protected void showActEndTime(){
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
			.append(min<10?"0"+min:min);
		return sb;
	}	
	
	public void upDataHead() {
		inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.photo_choose_dialog, null);
		Button carema = (Button) view.findViewById(R.id.camera);
		Button album = (Button) view.findViewById(R.id.album);
		Button give_up = (Button) view.findViewById(R.id.give_up);
		final Dialog dialog = new Dialog(activity,
				R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.mypopwindow_anim_style);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		carema.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (checkCameraHardWare(activity)) {

					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						Date date = new Date(System.currentTimeMillis());
						String filename = format.format(date);
						// 创建File对象用于存储拍照的图片 SD卡根目录
						// File outputImage = new
						// File(Environment.getExternalStorageDirectory(),test.jpg);
						// 存储至DCIM文件夹
						File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
						File outputImage = new File(path, filename + ".jpg");
						try {
							if (outputImage.exists()) {
								outputImage.delete();
							}
							outputImage.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (9 - files.size() < 0) {
							CustomToast.show(activity, R.string.tip,
									R.string.max_photos);
							return;
						}
						imageUri = Uri.fromFile(outputImage);
						Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); // 照相
						intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 指定图片输出地址
						intent.putExtra("return-data", true);
						startActivityForResult(intent, ACTION_IMAGE_CAPTURE); // 启动照相
					}
				}
			}
		});
		album.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(getActivity(), GalleryActivity.class);
				// 指定图片选择数
				int max = 9;
				
//				if(adapter==null||adapter.getCount()==0){
//					max = 9;
//				}else{
//					max = 9 - adapter.getCount();
//				}
				
				//此处判断写的好玩些
				if (max - files.size() < 0) {
					CustomToast.show(activity, R.string.tip,R.string.max_photos);
					return;
				} else {
					max = max - files.size();
				}
				intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, max);//max会随着files表的数目变化
				startActivityForResult(intent, ACTION_IMAGE_PICK);
			}
		});

		give_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	public void openPhotoDetails(int position){
		headPagerAdapter = new DetailHeaderDeletePagerAdapter(activity, files);
		imageGalleryDeleteDialog = ImageGalleryDeleteDialog.newInstance(headPagerAdapter, position,files);
		imageGalleryDeleteDialog.setOnNotifyGridViewListener(this);
		imageGalleryDeleteDialog.show(getFragmentManager(), "galleryCanDelete");
	}

	@Override
	public void notifyGridView(){
		//此adapter是GridView挂钩的Adapter数据源
		adapter.notifyDataSetChanged();
	}
	@Override
	public void notifyGridView(int i) {
		imageGalleryDeleteDialog.dismiss();
		adapter.notifyDataSetChanged();
	}
	private boolean checkCameraHardWare(Context context) {
		PackageManager packageManager = context.getPackageManager();
		if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		}
		return false;
	}
	/**
	 * 图片返回的数据
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTION_IMAGE_CAPTURE:
			if (new File(BitmapUtils.getInstance().getRealFilePath(activity, imageUri)).length() == 0) {
				return;
			}
			ll_photo_upload.setVisibility(View.GONE);
			gv_images.setVisibility(View.VISIBLE);
			files.add(imageUri.toString());
			// setListViewHeightBasedOnChildren(v.gv_new_comment_images);
			Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			intentBc.setData(imageUri);
			getActivity().sendBroadcast(intentBc);
			adapter.notifyDataSetChanged();
			break;
		case ACTION_IMAGE_PICK:
			if (data == null) {
				return;
			}
			ll_photo_upload.setVisibility(View.GONE);
			gv_images.setVisibility(View.VISIBLE);
			// String path = getPath(activity, uri);
			ArrayList<String> images = data.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
			for (String path : images) {
				files.add("file://" + path);
			}
			// setListViewHeightBasedOnChildren(v.gv_new_comment_images);
			adapter.notifyDataSetChanged();
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
