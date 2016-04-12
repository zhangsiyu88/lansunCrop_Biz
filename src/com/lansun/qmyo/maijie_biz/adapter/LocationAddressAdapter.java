package com.lansun.qmyo.maijie_biz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.ClaimStoreAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocationAddressAdapter extends BaseAdapter {

	private Context mCtx;
	private ArrayList<HashMap<String, Object>> mDataList;
	private int mFlag;
	private LayoutInflater mLayoutInflater;
	private ViewHolder viewHolder;
	
	
	public LocationAddressAdapter(Context ctx ,ArrayList<HashMap<String, Object>> dataList,int flag){
		mCtx = ctx;
		mDataList = dataList;
		mFlag = flag;
		mLayoutInflater = LayoutInflater.from(mCtx);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_listview_location_address, null);
			
			viewHolder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tv_location.setText((CharSequence) mDataList.get(position).get("location_address"));
		return convertView;
	}
	
	
	
	class ViewHolder{
		public TextView tv_location;
	}

}
