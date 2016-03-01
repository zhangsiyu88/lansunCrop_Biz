package com.lansun.qmyo.maijie_biz.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.lansun.qmyo.maijie_biz.log.FrameLog;

public class PhotoUtils {
      @TargetApi(Build.VERSION_CODES.KITKAT)
      public static String getPath(final Context context, final Uri uri) {

	  final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	  // DocumentProvider
	  if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
		    final String docId = DocumentsContract.getDocumentId(uri);
		    final String[] split = docId.split(":");
		    final String type = split[0];

		    if ("primary".equalsIgnoreCase(type)) {
			return Environment.getExternalStorageDirectory() + "/" + split[1];
		    }

		    // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

		    final String id = DocumentsContract.getDocumentId(uri);
		    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

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

		    return getDataColumn(context, contentUri, selection, selectionArgs);
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

      /**
       * Get the value of the data column for this Uri. This is useful for
       * MediaStore Uris, and other file-based ContentProviders.
       * 
       * @param context
       *              The context.
       * @param uri
       *              The Uri to query.
       * @param selection
       *              (Optional) Filter used in the query.
       * @param selectionArgs
       *              (Optional) Selection arguments used in the query.
       * @return The value of the _data column, which is typically a file path.
       */
      public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

	  Cursor cursor = null;
	  final String column = "_data";
	  final String[] projection = { column };

	  try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
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

      /**
       * @param uri
       *              The Uri to check.
       * @return Whether the Uri authority is ExternalStorageProvider.
       */
      public static boolean isExternalStorageDocument(Uri uri) {
	  return "com.android.externalstorage.documents".equals(uri.getAuthority());
      }

      /**
       * @param uri
       *              The Uri to check.
       * @return Whether the Uri authority is DownloadsProvider.
       */
      public static boolean isDownloadsDocument(Uri uri) {
	  return "com.android.providers.downloads.documents".equals(uri.getAuthority());
      }

      /**
       * @param uri
       *              The Uri to check.
       * @return Whether the Uri authority is MediaProvider.
       */
      public static boolean isMediaDocument(Uri uri) {
	  return "com.android.providers.media.documents".equals(uri.getAuthority());
      }

      /**
       * @param uri
       *              The Uri to check.
       * @return Whether the Uri authority is Google Photos.
       */
      public static boolean isGooglePhotosUri(Uri uri) {
	  return "com.google.android.apps.photos.content".equals(uri.getAuthority());
      }

      /**
       * 裁剪图片方法实现
       * 
       * @param uri
       */
      public static void startPhotoZoom(Fragment fragment, Uri uri, int outWidth, int outHeight) {
	  if (uri == null) {
	        FrameLog.i("tag", "The uri is not exist.");
	  }
	  Intent intent = new Intent("com.android.camera.action.CROP");
	  intent.setDataAndType(uri, "image/*");
	  // 设置裁剪
	  intent.putExtra("crop", "true");
	  // aspectX aspectY 是宽高的比例
	  intent.putExtra("aspectX", outWidth);
	  intent.putExtra("aspectY", outHeight);
	  // outputX outputY 是裁剪图片宽高
	  intent.putExtra("outputX", outWidth);
	  intent.putExtra("outputY", outHeight);
	  intent.putExtra("return-data", true);
	  intent.putExtra("noFaceDetection", true);
	  fragment.startActivityForResult(intent, 2);
      }

      /**
       * 保存裁剪之后的图片数据
       * 
       * @param picdata
       */
      public static Bitmap setImageToView(Intent data) {
	  Bundle extras = data.getExtras();
	  if (extras != null) {
	        Bitmap photo = extras.getParcelable("data");
	        return photo;
	  }
	  return null;
      }
      /**
       * 
       * @param srcPath
       * @return
       */
      public static  Bitmap compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//TODO 太大了会造成内从溢出
		float hh = 1280f;//
		float ww = 720f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率

		newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		return bitmap;
	}
      /**
	 * 保存一张BItmap位图
	 */
	public static boolean saveBitMapToSDcard(Bitmap bitmap, String savePath) {
		boolean saveStatus = true;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(savePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			saveStatus = false;
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

		return saveStatus;

	}

}
