package com.lansun.qmyo.maijie_biz.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.ListViewAdapter.ViewHolder;
import com.lansun.qmyo.maijie_biz.bean.FansNoteBean;
import com.lansun.qmyo.maijie_biz.bean.InchargeNoteBean;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InchargeNoteAdapter extends BaseAdapter {

	
	private Context mCtx;
	private ArrayList<InchargeNoteBean> mDataList;
	private int mFlag;
	private LayoutInflater mLayoutInflater;
	private ViewHolder viewHolder;

	public InchargeNoteAdapter(FragmentActivity activity,
			ArrayList<InchargeNoteBean> dataList, int flag) {
		mCtx = activity;
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
			convertView = mLayoutInflater.inflate(R.layout.item_listview_incharge_note, null);
			
			viewHolder.tv_date =  (TextView) convertView.findViewById(R.id.tv_date);
			viewHolder.tv_efficient_fans =  (TextView) convertView.findViewById(R.id.tv_efficient_fans);
			viewHolder.tv_incharge_sum =  (TextView) convertView.findViewById(R.id.tv_incharge_sum);
			viewHolder.tv_state =  (TextView) convertView.findViewById(R.id.tv_state);
			viewHolder.v_line =  convertView.findViewById(R.id.v_line);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_date.setText((CharSequence) mDataList.get(position).getDate());
		viewHolder.tv_efficient_fans.setText(String.valueOf(mDataList.get(position).getEfficientFans()));
		viewHolder.tv_incharge_sum.setText(String.valueOf(mDataList.get(position).getInchargeMoney()));
		
		if(mDataList.get(position).getState()==0){
			viewHolder.tv_state.setText("未兑换");
		}else{
			viewHolder.tv_state.setText("已兑换");
		}
		
		if(position == mDataList.size()-1){
			viewHolder.v_line.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	
    class ViewHolder{
		public TextView tv_date,tv_efficient_fans,tv_incharge_sum,tv_state;
		public View v_line;
    }
}
