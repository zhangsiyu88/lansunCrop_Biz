package com.lansun.qmyo.maijie_biz.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil.TipAlertDialogCallBack;


public class DialogUtil {

	/**
	 * 弹出没有网络连接提示对话框
	 * 
	 * @param context
	 */
	// public static void showNoNetWork(final Context context) {
	// AlertDialog.Builder builder = new Builder(context);
	// builder.setTitle(R.string.app_title).setIcon(R.drawable.ic_launcher)
	// .setMessage(R.string.net_faild)
	// .setPositiveButton(android.R.string.ok, new OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// if (android.os.Build.VERSION.SDK_INT > 10) {
	// context.startActivity(new Intent(
	// android.provider.Settings.ACTION_SETTINGS));
	// } else {
	// context.startActivity(new Intent(
	// android.provider.Settings.ACTION_WIRELESS_SETTINGS));
	// }
	// }
	//
	// }).setNegativeButton(android.R.string.cancel, null).show();
	// }

	public static void createTipAlertDialog(Context ctx, String content,
			final TipAlertDialogCallBack callBack) {
		CustomDialog.Builder dialog = new CustomDialog.Builder(ctx);
		dialog.setTitle(R.string.tip);
		dialog.setMessage(content);
		dialog.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callBack.onNegativeButtonClick(dialog, which);
					}
				});
		dialog.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callBack.onPositiveButtonClick(dialog, which);
					}
				});
		dialog.create().show();
	}

	public static void createTipAlertDialog(Context ctx, int contentId,
			final TipAlertDialogCallBack callBack) {
		CustomDialog.Builder dialog = new CustomDialog.Builder(ctx);
		dialog.setTitle(R.string.tip);
		dialog.setMessage(contentId);
		dialog.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callBack.onNegativeButtonClick(dialog, which);
					}
				});
		dialog.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callBack.onPositiveButtonClick(dialog, which);
					}
				});
		dialog.create().show();
	}

	public interface TipAlertDialogCallBack {
		void onPositiveButtonClick(DialogInterface dialog, int which);

		void onNegativeButtonClick(DialogInterface dialog, int which);
	}

	/**
	 * 门店创建的框架搭建
	 * @param ctx
	 * @param content
	 * @param callBack
	 */
	public static void createStoreClaimDialog(Context ctx, String content,
			final TipAlertDialogCallBack callBack) {
		CustomDialog.Builder dialog = new CustomDialog.Builder(ctx);
		dialog.setTitle(R.string.tip_claim_store);
		dialog.setMessage(content);
		dialog.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callBack.onNegativeButtonClick(dialog, which);
					}
				});
		dialog.setPositiveButton(R.string.ok_create,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callBack.onPositiveButtonClick(dialog, which);
					}
				});
		dialog.create().show();
	}
	
	
	
	/**
	 * 普通Dialog的搭建
	 * @param ctx
	 * @param tip
	 * @param content
	 * @param callBack
	 */
	public static void createCommonDialog(Context ctx, String tip ,String content,String _ok,String _cancel,
			final TipAlertDialogCallBack callBack) {
		CustomDialog.Builder dialog = new CustomDialog.Builder(ctx);
		dialog.setTitle(tip);
		dialog.setMessage(content);
		dialog.setNegativeButton(_cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callBack.onNegativeButtonClick(dialog, which);
					}
				});
		dialog.setPositiveButton(_ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callBack.onPositiveButtonClick(dialog, which);
					}
				});
		dialog.create().show();
	}

//	public static void createUserLoginDialog(final Context ctx, int contentId) {
//		createTipAlertDialog(ctx, contentId, new TipAlertDialogCallBack() {
//
//			@Override
//			public void onPositiveButtonClick(DialogInterface dialog, int which) {
//				Intent intent = new Intent(ctx, LoginActivity.class);
//				ctx.startActivity(intent);
//				dialog.dismiss();
//			}
//
//			@Override
//			public void onNegativeButtonClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//	}
}
