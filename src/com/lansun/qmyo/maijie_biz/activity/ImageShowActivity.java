package com.lansun.qmyo.maijie_biz.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.utils.ScreenInfo;
import com.lansun.qmyo.maijie_biz.view.ImageLoadingDialog;

public class ImageShowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.imageshower);
		ImageView iv_content = (ImageView) findViewById(R.id.iv_content);
		if(getIntent()!=null){
//			Bundle bundle = getIntent().getExtras();
//			Bitmap bp = (Bitmap) bundle.get("view");
			
		    String bp_path = getIntent().getStringExtra("view_path");
		    ScreenInfo screenInfo = new ScreenInfo(this);
			int width = screenInfo.getWidth();
			int densityDpi = screenInfo.getDensityDpi();
			int DpiScale = densityDpi/160;
//			bp_idcard = convertToBitmap(images.get(0), width-20*DpiScale, 150*DpiScale);
		    Bitmap bp = convertToBitmap(bp_path, width-20*DpiScale, 150*DpiScale);
		    
//		    String photoname_pick_idcard = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";	
//			FileOutputStream b_pick_idcard = null;
//			File file_pick_idcard = new File("/sdcard/MJBizImage/");
//			file_pick_idcard.mkdirs();// 创建文件夹
//			String fileName_pick_idcard = "/sdcard/MJBizImage/"+photoname_pick_idcard;
//			try {
//				b_pick_idcard = new FileOutputStream(fileName_pick_idcard);
//				bp.compress(Bitmap.CompressFormat.JPEG, 10, b_pick_idcard);// 把数据写入文件
//			} catch (FileNotFoundException e){
//				e.printStackTrace();
//			} finally {
//				try {
//					b_pick_idcard.flush();
//					b_pick_idcard.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			iv_content.setImageBitmap(bp);
		}
		
		final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		// 两秒后关闭后dialog
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		}, 1000 * 1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
	
	
	public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);//此举可以拿到需要的图片的尺寸，又不用返回Bitmap对象
        
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
//        opts.inSampleSize = (int)scale;
        
//        if(scale==0){//长宽都是小于预设的长宽值
//        	opts.inSampleSize = 1;//情况：原图片的大小比设定目标大小还要小，会出现scale为0，造成报错unknown bitmap configuration
//        	targetWidth = w;
//        	targetHeight = height*(w/width);
//        	WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
//        	return Bitmap.createScaledBitmap(weak.get(),targetWidth,targetHeight, true);
//        }else{
//        	opts.inSampleSize = (int)scale;
//        }
//        
//        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
////        return Bitmap.createScaledBitmap(weak.get(),(int)(width/scale), (int) (height/scale), true);
//        //下面按照真实的图片大小的尺寸进行等比例缩放
//        return Bitmap.createScaledBitmap(weak.get(), width, height, true);
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
}
