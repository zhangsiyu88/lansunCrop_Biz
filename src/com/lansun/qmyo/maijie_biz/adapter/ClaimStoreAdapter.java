package com.lansun.qmyo.maijie_biz.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.ListViewAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClaimStoreAdapter extends BaseAdapter {

	
	private Context mCtx;
	private ArrayList<HashMap<String, Object>> mDataList;
	private int mFlag;
	private LayoutInflater mLayoutInflater;
	private ViewHolder viewHolder;

	public ClaimStoreAdapter(Context ctx ,ArrayList<HashMap<String, Object>> dataList,int flag){
		mCtx = ctx;
		mDataList = dataList;
		mFlag = flag;
		mLayoutInflater = LayoutInflater.from(mCtx);
	}
	
	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_listview_claim_store, null);
			
			viewHolder.tv_store_name =  (TextView) convertView.findViewById(R.id.tv_store_name);
			viewHolder.tv_statue =  (TextView) convertView.findViewById(R.id.tv_statue);
			viewHolder.tv_address =  (TextView) convertView.findViewById(R.id.tv_address);
			viewHolder.v_line =  convertView.findViewById(R.id.v_line);
			
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tv_store_name.setText((CharSequence) mDataList.get(position).get("store_name"));
		if((int) mDataList.get(position).get("statue")==-1){
			viewHolder.tv_statue.setText("未认领");
		}
		viewHolder.tv_address.setText((CharSequence) mDataList.get(position).get("address"));
		if(position == mDataList.size()-1){
			viewHolder.v_line.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	
    class ViewHolder{
    	public TextView tv_store_name;
		public TextView tv_statue;
		public TextView tv_address;
		public View v_line;
    }
}
