package com.lansun.qmyo.maijie_biz.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.DetailHeaderPagerAdapter;
import com.lansun.qmyo.maijie_biz.entity.CloseEntity;
import com.lansun.qmyo.maijie_biz.entity.DeleteEntity;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil.TipAlertDialogCallBack;




public class ImageGalleryDialog2 extends DialogFragment {
	private ViewPager dialog_gallery;
	private boolean hasTop;
	private TextView store_detail2_count;
	private TextView store_detail2_num;
	private int currentPosition;
	public static int mFlag;
	
	/*无效做法
	 * public  Changegridview changegridview ;
	public static UpLoadPhotoAdapter mAdapter;*/
	
	@InjectAll
	private Views v;

	class Views {
		@InjectBinder(listeners = { OnClick.class }, method = "click")
		private ImageView iv_activity_back, tv_dialog_delete;
	}

	public ImageGalleryDialog2() {
		super();
	}

	public static ImageGalleryDialog2 newInstance(
		DetailHeaderPagerAdapter adapter, int position) {
		ImageGalleryDialog2 fragment = new ImageGalleryDialog2();
		Bundle args = new Bundle();
		args.putSerializable("adapter", adapter);
		args.putInt("currentPosition", position);
		fragment.setArguments(args);
		fragment.setCancelable(true);
		return fragment;
	}
	

	public static ImageGalleryDialog2 newInstance(
			DetailHeaderPagerAdapter adapter, int position, int flag) {
		
			ImageGalleryDialog2 fragment = new ImageGalleryDialog2();
			Bundle args = new Bundle();
			args.putSerializable("adapter", adapter);
			args.putInt("currentPosition", position);
			fragment.setArguments(args);
			fragment.setCancelable(true);
			mFlag = flag;
			return fragment;
		
		
		
	}
	
	
	/*这种做法是无效的，这里的mAdapter已经不是原先的那个adapter了，所以mAdapter的notifyDataSetChanged是无效果的
	 * public static ImageGalleryDialog2 newInstance(
			DetailHeaderPagerAdapter headAdapter2, int position,
			UpLoadPhotoAdapter adapter) {
		ImageGalleryDialog2 fragment2 = new ImageGalleryDialog2();
		fragment2 = newInstance(headAdapter2,position);
		mAdapter = adapter;
		return fragment2;
	}*/

	/*
	 * 进来就拿到屏幕的布局大小
	 * @see android.support.v4.app.DialogFragment#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		getDialog().getWindow().setLayout(dm.widthPixels,getDialog().getWindow().getAttributes().height);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);//设置DialogFragment的风格
		
		this.headAdapter = (DetailHeaderPagerAdapter) getArguments().getSerializable("adapter");
		this.currentPosition = getArguments().getInt("currentPosition");
		
		EventBus.getDefault().register(this);//注册了一个消息接收者
		
	}
	
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	//拿来专门处理CloseEntity的Event信息
	public void onEventMainThread(CloseEntity event) {
		boolean close = event.isClose();
		if (close) {
			this.dismiss();
		}
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setCanceledOnTouchOutside(true);
		View view = inflater.inflate(R.layout.dialog_gallery2, container);
		
		//dialog_gallery是ViewPager
		dialog_gallery = (ViewPager) view.findViewById(R.id.dialog_gallery);
		
		store_detail2_count = (TextView) view.findViewById(R.id.store_detail2_count);
		store_detail2_num = (TextView) view.findViewById(R.id.store_detail2_num);

		//
		dialog_gallery.setOnPageChangeListener(listener);
		
		
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				screenWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
		dialog_gallery.setLayoutParams(params);
		
		
		//注意：首先给dialog_gallery设置好Adapter适配器拥有了数据源后，再考虑后面 定位图片的事
		dialog_gallery.setAdapter(headAdapter);
		
		dialog_gallery.setCurrentItem(currentPosition);
		store_detail2_num.setText((currentPosition + 1) + "");
		store_detail2_count.setText(headAdapter.getCount() + "");
		
		//将当前的View放在对应的Fragment中
		Handler_Inject.injectFragment(this, view);
		return view;
	}

	/*
	 * 监听页面变化，当页面变化时，我们将第几页设置为ViewPager的序号+1
	 */
	OnPageChangeListener listener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			store_detail2_num.setText(arg0 + 1 + "");
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int position) {
		}

	};
	
	//static 修饰的类
	private static DetailHeaderPagerAdapter headAdapter;
	
	
	private void click(View view) {
		switch (view.getId()) {
		case R.id.iv_activity_back:
			dismiss();
			break;
		case R.id.tv_dialog_delete:
			//弹出对话框，询问是否删除当前图片
			DialogUtil.createTipAlertDialog(getActivity(), R.string.delete,
					new TipAlertDialogCallBack() {

						@Override
						public void onPositiveButtonClick(
								DialogInterface dialog, int which) {
							dialog.dismiss();
							
							//标示对应的删除实体，保证InsertInfoActivtiy在接收删除信号后，讷讷个对应的做出删除对应的List集合的操作
							DeleteEntity event = new DeleteEntity();
							event.setFlag(mFlag) ;
							
								
							
							
							//从ViewPager上拿到当前页的顺序数
							int currentItem = dialog_gallery.getCurrentItem();
							Log.e("Tag_ImageDialog", "我删除了第"+currentItem+"张照片");
							event.setPosition(currentItem);
							
							//说好的RemoveView呢？删除显示图片呢？
							dialog_gallery.removeView(dialog_gallery.getChildAt(currentItem));
							
							
							if (currentItem <= dialog_gallery.getChildCount()) {
								dialog_gallery.setCurrentItem(currentItem);
							} else {
								dialog_gallery.setCurrentItem(currentItem - 1);
							}
							headAdapter.notifyDataSetChanged();//重设了headAdapter的getCount()的值 = photo.size()
							
							//将DeleteEntity放到event中，最后在主线程中去处理这个事情
							EventBus.getDefault().post(event);
							
							//当dialog_gallery中的某一个View 被删除后，
							//并且将要删除的viewpager 中的顺序数对应的files的对应的值删除后，在这边再一次setAdapter一下
							//疑问：这边setAdapter进去的适配器是否是被修改过后的headAdapter
							//在这里重新设置Adapter的值
							dialog_gallery.setAdapter(headAdapter);
							
							//图片的总数重新设置一下 = headAdapter.getCount(),headAdapter上一步已经notify过了
							store_detail2_count.setText(headAdapter.getCount()+ "");
							
							//当确定删除时，需要将GridView也进行一下notifyDataChanged
							//设计一个回调函数：
							/*无效做法
							 * mAdapter.notifyDataSetChanged();*/
							
							
						}
						@Override
						public void onNegativeButtonClick(
								DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			break;
		}
	}




	
}
