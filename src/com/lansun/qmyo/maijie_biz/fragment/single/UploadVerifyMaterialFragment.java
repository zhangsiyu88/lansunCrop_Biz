package com.lansun.qmyo.maijie_biz.fragment.single;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
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

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.ImageShowActivity;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.utils.BitmapUtils;
import com.lansun.qmyo.maijie_biz.view.CustomToast;
import com.ns.mutiphotochoser.GalleryActivity;
import com.ns.mutiphotochoser.constant.Constant;
import com.umeng.analytics.MobclickAgent;

public class UploadVerifyMaterialFragment extends HeaderFragment implements OnClickListener{
	
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_upload_verify_material, null);
		rootView  = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		iv_take_idcard_photo = (ImageView) rootView.findViewById(R.id.iv_take_idcard_photo);
		iv_take_store_photo = (ImageView) rootView.findViewById(R.id.iv_take_store_photo);
		iv_storephoto = (ImageView) rootView.findViewById(R.id.iv_storephoto);
		iv_manandidcard = (ImageView) rootView.findViewById(R.id.iv_manandidcard);
		tv_manandid = (TextView) rootView.findViewById(R.id.tv_manandid);
		tv_manandstore = (TextView) rootView.findViewById(R.id.tv_manandstore);
		iv_take_store_photo.setOnClickListener(this);
		iv_take_idcard_photo.setOnClickListener(this);
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
		default:
			break;
		}
		super.onClick(v);
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
							CustomToast.show(mContext, R.string.tip,R.string.max_photos);
							return;
						}
						imageUri = Uri.fromFile(outputImage);
						Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); // 照相
						intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 指定!图片!输出!地址
						intent.putExtra("return-data", true);
						
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
    @SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if(resultCode == Activity.RESULT_OK){    //resultCode 返回结果码
    		
    		switch (requestCode) {	             //requestCode 根据请求码分配
		    case ACTION_IMAGE_CAPTURE_IDCARD:
	    	/*String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				Log.i("TestFile","SD card is not avaiable/writeable right now.");
				return;
			}*/
			String photoname_idcard = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";	
			Bundle bundle_idcard = data.getExtras();
			Bitmap bitmap_idcard = (Bitmap) bundle_idcard.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
			FileOutputStream b_idcard = null;
			File file_idcard = new File("/sdcard/MJBizImage/");
			file_idcard.mkdirs();// 创建文件夹
			String fileName_idcard = "/sdcard/MJBizImage/"+photoname_idcard;
			try {
				b_idcard = new FileOutputStream(fileName_idcard);
				bitmap_idcard.compress(Bitmap.CompressFormat.JPEG, 100, b_idcard);// 把数据写入文件
			} catch (FileNotFoundException e){
				e.printStackTrace();
			} finally {
				try {
					b_idcard.flush();
					b_idcard.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//bitmap_store = bitmap;
    		/*
    		 * 因为在DialogFragment中	iv_manandidcard被 人为的gone掉了，要想再把值放上去，需要将其设置回来为可见属性
    		 * 后面再向里面放值
    		 */
    		iv_manandidcard.setVisibility(View.VISIBLE);//-------------------------!!!
    		iv_manandidcard.setImageBitmap(bitmap_idcard);// 将图片显示在ImageView里
    		
			//将浮在表面的图片和文字设置为gone
			tv_manandid.setVisibility(View.GONE);
			iv_manandidcard.setOnClickListener(this);
			
			tv_manandid.setVisibility(View.GONE);
			lp_idcard = (android.widget.RelativeLayout.LayoutParams) iv_take_idcard_photo.getLayoutParams();
			lp_idcard.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp_idcard.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lp_idcard.setMargins(0, 0, 10, 10);
			iv_take_idcard_photo.setLayoutParams(lp_idcard);

			Uri imageUri_idcard = Uri.parse(fileName_idcard);
			bp_idcard_path = getPath(mContext, imageUri_idcard);
			    break;
			
		    case ACTION_IMAGE_CAPTURE_STORE:
		    	/*String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile","SD card is not avaiable/writeable right now.");
					return;
				}*/
				String photoname_store = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";	
				Bundle bundle_store = data.getExtras();
				Bitmap bitmap_store = (Bitmap) bundle_store.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b_store = null;
				File file_store = new File("/sdcard/MJBizImage/");
				file_store.mkdirs();// 创建文件夹
				String fileName_store = "/sdcard/MJBizImage/"+photoname_store;
				try {
					b_store = new FileOutputStream(fileName_store);
					bitmap_store.compress(Bitmap.CompressFormat.JPEG, 100, b_store);// 把数据写入文件
				} catch (FileNotFoundException e){
					e.printStackTrace();
				} finally {
					try {
						b_store.flush();
						b_store.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//bitmap_store = bitmap;
	    		/*
	    		 * 因为在DialogFragment中	iv_manandidcard被 人为的gone掉了，要想再把值放上去，需要将其设置回来为可见属性
	    		 * 后面再向里面放值
	    		 */
				iv_storephoto.setVisibility(View.VISIBLE);//-------------------------!!!
				tv_manandstore.setVisibility(View.GONE);
				iv_storephoto.setImageBitmap(bitmap_store);// 将图片显示在ImageView里
	    		
	    		iv_storephoto.setOnClickListener(this);
				//将浮在表面文字设置为gone

				//将门店照片拍照的按钮移至右下角
				lp_store = (android.widget.RelativeLayout.LayoutParams) iv_take_store_photo.getLayoutParams();
				lp_store.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp_store.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp_store.setMargins(0, 0, 10, 10);
				iv_take_store_photo.setLayoutParams(lp_store);

				Uri imageUri_store = Uri.parse(fileName_store);
				bp_store_path= getPath(mContext, imageUri_store);

				break;
			case ACTION_IMAGE_PICK_IDCARD:
				lToast("返回到前一页");
				if (data == null) {
					return;
				}
				images = data.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
				/*for (String path : images) {
					files.add("file://" + path);
				}*/
				iv_manandidcard.setVisibility(View.VISIBLE);//-------------------------!!!
				bp_idcard_path = images.get(0);
				bp_idcard = convertToBitmap(images.get(0), 400, 140);
				
				
				int size = BitmapUtils.getInstance().getBitmapSize(bp_idcard);
				System.out.println("所选图片的大小：  "+size);
				
	    		iv_manandidcard.setImageBitmap(bp_idcard);// 将图片显示在ImageView里
	    		iv_manandidcard.setOnClickListener(this);
	    		
				//将浮在表面的图片和文字设置为gone
				tv_manandid.setVisibility(View.GONE);
				
				lp_idcard = (android.widget.RelativeLayout.LayoutParams) iv_take_idcard_photo.getLayoutParams();
				lp_idcard.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp_idcard.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp_idcard.setMargins(0, 0, 10, 10);
				iv_take_idcard_photo.setLayoutParams(lp_idcard);
				//iv_take_idcard_photo.setVisibility(View.GONE);

//				sp_editor.putString("storePhoto", fileName);
//				sp_editor.commit();
				
				//设置高度
//				setListViewHeightBasedOnChildren(gv_upload_storephotos);
//				adapter.notifyDataSetChanged();
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
				iv_manandidcard.setVisibility(View.VISIBLE);//-------------------------!!!
				bp_store_path = images.get(0);
				bp_store = convertToBitmap(images.get(0), 400, 140);
				
				int size1 = BitmapUtils.getInstance().getBitmapSize(bp_store);
				System.out.println("所选图片的大小：  "+size1);
				
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
				
				
	    	case ACTION_IMAGE_CAPTURE_SINGLE_IDCARD:
    			/*String sdStatus = Environment.getExternalStorageState();
	    			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
	    				Log.i("TestFile","SD card is not avaiable/writeable right now.");
	    				return;
	    			}*/
    			String photoname = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";	
    			Bundle bundle = data.getExtras();
    			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
    			FileOutputStream b = null;
    			File file = new File("/sdcard/myImage/");
    			file.mkdirs();// 创建文件夹
    			String fileName = "/sdcard/myImage/"+photoname;
    			try {
    				b = new FileOutputStream(fileName);
    				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
    			} catch (FileNotFoundException e){
    				e.printStackTrace();
    			} finally {
    				try {
    					b.flush();
    					b.close();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    			}
				//bitmap_store = bitmap;
	    		/*
	    		 * 因为在DialogFragment中	iv_manandidcard被 人为的gone掉了，要想再把值放上去，需要将其设置回来为可见属性
	    		 * 后面再向里面放值
	    		 */
	    		iv_manandidcard.setVisibility(View.VISIBLE);//-------------------------!!!
	    		iv_manandidcard.setImageBitmap(bitmap);// 将图片显示在ImageView里
	    		
				//将浮在表面的图片和文字设置为gone
				tv_manandid.setVisibility(View.GONE);
				iv_take_idcard_photo.setVisibility(View.GONE);
				
			
//				sp_editor.putString("storePhoto", fileName);
//				sp_editor.commit();
				
    			Uri imageUri1 = Uri.parse(fileName);
    			String path = getPath(mContext, imageUri1);
				singleIdCardPhotoPath.add("file://"+fileName);
			      break;
			case ACTION_IMAGE_CAPTURE_SINGLE_STORE:
    			String sdStatus1 = Environment.getExternalStorageState();
    			if (!sdStatus1.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
    				Log.i("TestFile","SD card is not avaiable/writeable right now.");
    				return;
    			}
    			String photoname1 = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";	
    			Bundle bundle1 = data.getExtras();
    			Bitmap bitmap1 = (Bitmap) bundle1.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
    			FileOutputStream b1 = null;
    		   //为什么不能直接保存在系统相册位置呢
    			File file1 = new File("/sdcard/myImage/");
    			file1.mkdirs();// 创建文件夹
    			String fileName1 = "/sdcard/myImage/"+photoname1;
	    			try {
	    				b1 = new FileOutputStream(fileName1);
	    				bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, b1);// 把数据写入文件
	    			} catch (FileNotFoundException e) {
	    				e.printStackTrace();
	    			} finally {
	    				try {
	    					b1.flush();
	    					b1.close();
	    				} catch (IOException e) {
	    					e.printStackTrace();
	    				}
	    			}
				iv_storephoto.setVisibility(View.VISIBLE);
				iv_storephoto.setImageBitmap(bitmap1);// 将图片显示在ImageView里
				iv_storephoto.setOnClickListener(this);
				
				
				tv_manandstore.setVisibility(View.GONE);
				iv_take_store_photo.setVisibility(View.GONE);
				iv_storephoto.setOnClickListener(this);
				
//    				sp_editor1.putString("idcardPhoto", fileName1);
//    				sp_editor1.commit();
				
				Uri imageUri2 = Uri.parse(fileName1);
    			String path2 = getPath(mContext, imageUri2);
				singleStorePhotoPath.add("file://"+fileName1);
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
	
	
	
	
	/**
	 * 由Uri拿文件对象
	 * @param context
	 * @param uri
	 * @return
	 */
	@SuppressLint("NewApi")
	public String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	private boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	private boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	private boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	private boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}
	private String getDataColumn(Context context, Uri uri, String selection,
			String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };
		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
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
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
//      return Bitmap.createScaledBitmap(weak.get(), w, h, true);
        //下面按照真实的图片大小的尺寸进行等比例缩放
        return Bitmap.createScaledBitmap(weak.get(), width, height, true);
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
