package com.lansun.qmyo.maijie_biz.activity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.eventbus.EventBus;
import com.lansun.qmyo.maijie_biz.utils.ScreenInfo;
import com.lansun.qmyo.maijie_biz.view.CustomToast;
import com.ns.mutiphotochoser.GalleryActivity;
import com.ns.mutiphotochoser.constant.Constant;

public class UploadVerifyMaterialActivity extends Activity implements OnClickListener{
	
	
	private Uri uri;
	
	static final int ACTION_IMAGE_CAPTURE = 2;
	static final int ACTION_IMAGE_PICK = 1;
	static final int ACTION_IMAGE_CAPTURE_SINGLE_IDCARD = 3;
	static final int ACTION_IMAGE_CAPTURE_SINGLE_STORE = 4;
	
	static final int ACTION_IMAGE_CAPTURE_IDCARD = 5;
	static final int ACTION_IMAGE_PICK_IDCARD = 6;
	static final int ACTION_IMAGE_CAPTURE_STORE = 7;
	static final int ACTION_IMAGE_PICK_STORE = 8;
	
	private static final int IS_STORE = 10;
	private static final int IS_IDCARD = 20;
	private ArrayList<String> files = new ArrayList<>();
	ArrayList<String> singleIdCardPhotoPath = new ArrayList<String>();
	ArrayList<String> singleStorePhotoPath = new ArrayList<String>();
	private ViewGroup rootView;
	
	private TextView tv_manandstore,tv_manandid;
	
	private ImageView iv_storephoto,iv_manandidcard,iv_take_idcard_photo,iv_take_store_photo;
	
	private Uri imageUri;
	private Bitmap bp_idcard, bp_store;
	private String bp_idcard_path,bp_store_path;
	private RelativeLayout.LayoutParams lp_idcard,lp_store;
	private Intent intentSystem;
	private ArrayList<String> images;
	
	public Context mContext;
	private ScreenInfo screenInfo;
	private int height_bp;
	private int width_bp;

	private Button btn_save_userinfo;
	public Context getActivity(){
		return this;
	}
	public void lToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_upload_verify_material);
		iv_take_idcard_photo = (ImageView) findViewById(R.id.iv_take_idcard_photo);
		iv_take_store_photo = (ImageView) findViewById(R.id.iv_take_store_photo);
		iv_storephoto = (ImageView) findViewById(R.id.iv_storephoto);
		iv_manandidcard = (ImageView) findViewById(R.id.iv_manandidcard);
		tv_manandid = (TextView) findViewById(R.id.tv_manandid);
		tv_manandstore = (TextView)findViewById(R.id.tv_manandstore);
		btn_save_userinfo = (Button)findViewById(R.id.btn_save_userinfo);
		
		btn_save_userinfo.setOnClickListener(this);
		initView();
		mContext = this;
	}
	
	private void initView() {
		iv_take_store_photo.setOnClickListener(this);
		iv_take_idcard_photo.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_take_store_photo:
			showPickPhoto(IS_STORE);
			break;
		case R.id.iv_take_idcard_photo:
			showPickPhoto(IS_IDCARD);
			break;
		case R.id.iv_manandidcard:
			showPhotoDetailsOnClick(bp_idcard_path);
			break;
		case R.id.iv_storephoto:
			showPhotoDetailsOnClick(bp_store_path);
			break;
		case R.id.btn_save_userinfo:
			String str = "图片已经存储";
			EventBus.getDefault().post(str);
			finish();
			break;
		default:
			break;
		}
	}

	
	
	
	/** 弹出相机和相册的选择框 */
	public void showPickPhoto(final int FLAG_SELECT) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.photo_choose_dialog, null);
		Button carema = (Button) view.findViewById(R.id.camera);
		Button album = (Button) view.findViewById(R.id.album);
		Button give_up = (Button) view.findViewById(R.id.give_up);
		final Dialog dialog = new Dialog(mContext,R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.mypopwindow_anim_style);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		dialog.setCanceledOnTouchOutside(true);
		
		carema.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (checkCameraHardWare(mContext)) {

					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
						Date date = new Date(System.currentTimeMillis());
						String filename = format.format(date);
						File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//						File path = Environment.getExternalStorageDirectory();
						File outputImage = new File(path, filename + ".jpg");
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
		                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);  
		                uri = Uri.fromFile(outputImage);  
		                // 获取拍照后未压缩的原图片，并保存在uri路径中  
		                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);   
//			            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 180);  
						if(FLAG_SELECT == IS_IDCARD){
							startActivityForResult(intent, ACTION_IMAGE_CAPTURE_IDCARD); // 启动照相
						}else if(FLAG_SELECT == IS_STORE){
							startActivityForResult(intent, ACTION_IMAGE_CAPTURE_STORE); // 启动照相
						}
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
				int max = 1;
//				if(adapter==null||adapter.getCount()==0){
//					max = 9;
//				}else{
//					max = 9 - adapter.getCount();
//				}
				if (max - files.size() < 0) {
					CustomToast.show(mContext, R.string.tip,R.string.max_photos);
					return;
				} else {
					max = max - files.size();
				}
				intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, max);
				if(FLAG_SELECT == IS_IDCARD){
					startActivityForResult(intent, ACTION_IMAGE_PICK_IDCARD);
				}else if(FLAG_SELECT == IS_STORE){
					startActivityForResult(intent, ACTION_IMAGE_PICK_STORE);
				}
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
	
	
	/** 图片返回的数据*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if(resultCode == Activity.RESULT_OK){    //resultCode 返回结果码
    		screenInfo = new ScreenInfo(this);
			int width = screenInfo.getWidth();
			int densityDpi = screenInfo.getDensityDpi();
			lToast("densityDpi: "+densityDpi);
			int DpiScale = densityDpi/160;
    		
    		switch (requestCode) {	             //requestCode 根据请求码分配
		    case ACTION_IMAGE_CAPTURE_IDCARD:
			bp_idcard = convertToBitmap(getRealFilePath(mContext, uri), width-20*DpiScale, 150*DpiScale);
			
    		iv_manandidcard.setImageBitmap(bp_idcard);// 将图片显示在ImageView里
    		iv_manandidcard.setOnClickListener(this);
    		
			//将浮在表面的图片和文字设置为gone
			tv_manandid.setVisibility(View.GONE);
			
			lp_idcard = (android.widget.RelativeLayout.LayoutParams) iv_take_idcard_photo.getLayoutParams();
			lp_idcard.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp_idcard.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lp_idcard.setMargins(0, 0, 10, 10);
			iv_take_idcard_photo.setLayoutParams(lp_idcard);
			
			bp_idcard_path = getRealFilePath(mContext, uri);
			    break;
			
		    case ACTION_IMAGE_CAPTURE_STORE:
				bp_store = convertToBitmap(getRealFilePath(mContext, uri), width-20*DpiScale, 150*DpiScale);
	    		iv_storephoto.setImageBitmap(bp_store);// 将图片显示在ImageView里
	    		iv_storephoto.setOnClickListener(this);
				//将浮在表面文字设置为gone
				tv_manandstore.setVisibility(View.GONE);
				//将门店照片拍照的按钮移至右下角
				lp_store = (android.widget.RelativeLayout.LayoutParams) iv_take_store_photo.getLayoutParams();
				lp_store.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp_store.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp_store.setMargins(0, 0, 10, 10);
				iv_take_store_photo.setLayoutParams(lp_store);
				bp_store_path = getRealFilePath(mContext, uri);
				break;
			case ACTION_IMAGE_PICK_IDCARD:
				lToast("返回到前一页");
				if (data == null) {
					return;
				}
				images = data.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);

				iv_manandidcard.setVisibility(View.VISIBLE);//-------------------------!!!
				bp_idcard_path = images.get(0);
				bp_idcard = convertToBitmap(images.get(0), width-20*DpiScale, 150*DpiScale);
	    		iv_manandidcard.setImageBitmap(bp_idcard);// 将图片显示在ImageView里
	    		iv_manandidcard.setOnClickListener(this);
	    		
				//将浮在表面的图片和文字设置为gone
				tv_manandid.setVisibility(View.GONE);
				
				lp_idcard = (android.widget.RelativeLayout.LayoutParams) iv_take_idcard_photo.getLayoutParams();
				lp_idcard.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp_idcard.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp_idcard.setMargins(0, 0, 10, 10);
				iv_take_idcard_photo.setLayoutParams(lp_idcard);

				break;
				
			case ACTION_IMAGE_PICK_STORE:
				lToast("返回到前一页");
				if (data == null) {
					return;
				}
				images = data.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
				/*for (String path : images) {
					files.add("file://" + path);
				}*/
				bp_store_path = images.get(0);
				ScreenInfo screenInfo = new ScreenInfo(this);
				int width1 = screenInfo.getWidth();
				int densityDpi1 = screenInfo.getDensityDpi();
				lToast("densityDpi: "+densityDpi1);
				int DpiScale1 = densityDpi1/160;
				bp_store = convertToBitmap(images.get(0), width1-20*DpiScale1, 150*DpiScale1);
	    		iv_storephoto.setImageBitmap(bp_store);// 将图片显示在ImageView里
	    		iv_storephoto.setOnClickListener(this);
				//将浮在表面文字设置为gone
				tv_manandstore.setVisibility(View.GONE);
				//将门店照片拍照的按钮移至右下角
				lp_store = (android.widget.RelativeLayout.LayoutParams) iv_take_store_photo.getLayoutParams();
				lp_store.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp_store.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp_store.setMargins(0, 0, 10, 10);
				iv_take_store_photo.setLayoutParams(lp_store);
				break;
		    	default:
		    		break;
	    		}	
    	}else{
    		//当resultcode不为OK时，很明显要回到InsertInfoActivity中去
//    		startActivity(new Intent(this,InsertInfoActivity.class));
    	}
		   super.onActivityResult(requestCode, resultCode, data);
    }
    
    
	/**
	 * 选中的图片进行点击操作时，展示确切的
	 * @param view
	 */
	private void showPhotoDetailsOnClick(String bp_path) {
		Intent intent = new Intent(getActivity(),ImageShowActivity.class);
//		Bundle bundle = new Bundle();
//		bundle.putParcelable("view", bundle);
//		intent.putExtras(bundle);
		intent.putExtra("view_path", bp_path);
		getActivity().startActivity(intent);
	}

	/** 检测相机的硬件属性 **/
	private boolean checkCameraHardWare(Context context) {
		PackageManager packageManager = context.getPackageManager();
		if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		}
		return false;
	}
	
	
	
	

	
	public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
        	//下面的scaleWidth和scaleHeight中可能存在某个值为 0 
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        System.out.println("scale的值：   "+scale);
       
        float targetHeight = 0;
        float targetWidth  = 0;
        
        if(scale==0){//长宽都是小于预设的长宽值
        	opts.inSampleSize = 1;//情况：原图片的大小比设定目标大小还要小，会出现scale为0，造成报错unknown bitmap configuration
        	targetWidth = w;
        	targetHeight = height*(w/width);
        	WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        	return Bitmap.createScaledBitmap(weak.get(),(int)targetWidth,(int)targetHeight, true);
        }else{////宽和高中至少存在某一个值大于等于目标的宽和高,即scaleHeight和scaleWidth中存在某个值大于1
//        	opts.inSampleSize = (int)scale;
        	opts.inSampleSize = 1;//为保证图片质量，取样值依旧为1
        	
        	if(scaleWidth>scaleHeight){//假设两个比例值都是大于等于1的，且以scaleWidth作为公用标尺scale
        		targetWidth = (((float)width)/scale);//scale其实就是width/targetWidth
        		targetHeight = (((float)height)/scale);
        		/*if(scaleHeight>1){
        			targetHeight = (((float)height)/scale);
        		}else{
        			targetHeight = (((float)height)/scale);
        		}*/
        	}else if(scaleWidth<scaleHeight){//最起码scaleHeight是大于1的
        		if(scaleWidth<1){//scaleHeight为大于等于1的值，但scaleWidth却是小于1，舍掉尾部则为0
        			targetWidth = w;
        			targetHeight = ((float)height*(float)(targetWidth/width));//实际上放大了宽度
        		}else {//表明原图的宽和高都大于目标宽高    1<scaleWidth<scaleHeight
        			targetWidth = w;
        			targetHeight = ((float)height/(float)(width/targetWidth));//宽度和高度皆要缩小
        		}
        	}
        	WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        	return Bitmap.createScaledBitmap(weak.get(),(int)targetWidth,(int)targetHeight, true);
        }
    }
	
	/**
	 * 根据Uri获取到实际的硬件位置
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealFilePath( final Context context, final Uri uri ) {
	    if ( null == uri ) return null;
	    final String scheme = uri.getScheme();
	    String data = null;
	    if ( scheme == null )
	        data = uri.getPath();
	    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
	        data = uri.getPath();
	    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
	        Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
	        if ( null != cursor ) {
	            if ( cursor.moveToFirst() ) {
	                int index = cursor.getColumnIndex( ImageColumns.DATA );
	                if ( index > -1 ) {
	                    data = cursor.getString( index );
	                }
	            }
	            cursor.close();
	        }
	    }
	    return data;
	}


}
