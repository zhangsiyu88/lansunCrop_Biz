package com.lansun.qmyo.maijie_biz.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.ListViewAdapter.ViewHolder;
import com.lansun.qmyo.maijie_biz.bean.FansNoteBean;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FansNoteAdapter extends BaseAdapter {

	
	private Context mCtx;
	private ArrayList<FansNoteBean> mDataList;
	private int mFlag;
	private LayoutInflater mLayoutInflater;
	private ViewHolder viewHolder;

	public FansNoteAdapter(FragmentActivity activity,ArrayList<FansNoteBean> preData, int flag) {
		mCtx = activity;
		mDataList = preData;
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
			convertView = mLayoutInflater.inflate(R.layout.item_listview_fans_note, null);
			
			viewHolder.tv_date_item =  (TextView) convertView.findViewById(R.id.tv_date_item);
			viewHolder.tv_rewards_counts_item =  (TextView) convertView.findViewById(R.id.tv_rewards_counts_item);
			viewHolder.tv_all_rewards_counts_item =  (TextView) convertView.findViewById(R.id.tv_all_rewards_counts_item);
			viewHolder.v_line =  convertView.findViewById(R.id.v_line);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_date_item.setText((CharSequence) mDataList.get(position).getDate());
		viewHolder.tv_rewards_counts_item.setText(String.valueOf(mDataList.get(position).getRewardCount()));
		viewHolder.tv_all_rewards_counts_item.setText(String.valueOf(mDataList.get(position).getAllRewardCount()));
		
		if(position == mDataList.size()-1){
			viewHolder.v_line.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	
    class ViewHolder{
		public TextView tv_date_item,tv_rewards_counts_item,tv_all_rewards_counts_item;
		public View v_line;
    }
}
