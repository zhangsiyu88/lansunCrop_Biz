package com.lansun.qmyo.maijie_biz.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.android.pc.ioc.image.RecyclingImageView;
import com.lansun.qmyo.maijie_biz.R;
import com.nostra13.universalimageloader.core.ImageLoader;



public class UpLoadPhotoAdapter extends CommonAdapter {

	public UpLoadPhotoAdapter(Activity activity, ArrayList<String> data) {
		this.activity = activity;
		this.data = data;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public int getCount() {
		return data.size() + 1;
	}

	@Override
	public View view(int position, View convertView, ViewGroup parent) {
		
		if (position == data.size()) {
		//当所选位置等于ArrayList<String> data的最后一个位置上时，将这个View变为R.layout.upload_photo的充气View
			convertView = inflater.inflate(R.layout.upload_photo, null);
			return convertView;
		}

		//一般情况下，将图片路径通过ImageLoader将其比变成图片展示在对应的View控件上
		convertView = inflater.inflate(R.layout.upload_photo_item, null);
		RecyclingImageView iv_upload_photo = (RecyclingImageView) convertView.findViewById(R.id.iv_upload_photo);
		
		iv_upload_photo.setScaleType(ScaleType.FIT_XY);
		String path = data.get(position);
		ImageLoader.getInstance().displayImage(path, iv_upload_photo);
		return convertView;
	}

}
