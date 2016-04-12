package com.lansun.qmyo.maijie_biz.utils;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;

public class BitmapUtils {
	
	public static BitmapUtils _instance; 
	
	public static BitmapUtils getInstance(){
		if(_instance==null){
			_instance = new BitmapUtils();
		}
		return _instance;
	}

	/**
	 * Bitmap对象的空间大小
	 * @param bitmap
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT) 
	public int getBitmapSize(Bitmap bitmap){
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){    //API 19
	        return bitmap.getAllocationByteCount();
	    }
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
	        return bitmap.getByteCount();
	    }
	    return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
	}
	
	/**
	 * 根据 存储路径，获取Bitmap对象
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
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
	 * 分清所有类别进行转换Bitmap
	 * 
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public Bitmap convertToBitmapPrecious(String path, int w, int h) {
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
	 * 根据Uri获取到实际的硬件位置，比下面的getPath效果更好(红米试验比getPath()方法有效)
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
}
