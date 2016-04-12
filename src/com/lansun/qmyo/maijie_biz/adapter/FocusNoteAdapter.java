package com.lansun.qmyo.maijie_biz.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.adapter.ListViewAdapter.ViewHolder;
import com.lansun.qmyo.maijie_biz.bean.FansNoteBean;
import com.lansun.qmyo.maijie_biz.bean.FocusNoteBean;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FocusNoteAdapter extends BaseAdapter {

	
	private Context mCtx;
	private ArrayList<FocusNoteBean> mDataList;
	private int mFlag;
	private LayoutInflater mLayoutInflater;
	private ViewHolder viewHolder;

//	public FocusNoteAdapter(FragmentActivity activity,ArrayList<FansNoteBean> preData, int flag) {
//    }

	public FocusNoteAdapter(FragmentActivity activity,ArrayList<FocusNoteBean> dataList, int flag) {
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
			convertView = mLayoutInflater.inflate(R.layout.item_listview_focus_note, null);
			
			viewHolder.tv_date_focus =  (TextView) convertView.findViewById(R.id.tv_date_focus);
			viewHolder.tv_add_focus_counts =  (TextView) convertView.findViewById(R.id.tv_add_focus_counts);
			viewHolder.tv_delete_focus_counts =  (TextView) convertView.findViewById(R.id.tv_delete_focus_counts);
			viewHolder.tv_all_fans_counts =  (TextView) convertView.findViewById(R.id.tv_all_fans_counts);
			viewHolder.v_line =  convertView.findViewById(R.id.v_line);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_date_focus.setText((CharSequence) mDataList.get(position).getDate());
		viewHolder.tv_add_focus_counts.setText(String.valueOf(mDataList.get(position).getAddCounts()));
		viewHolder.tv_delete_focus_counts.setText(String.valueOf(mDataList.get(position).getDeleteCounts()));
		viewHolder.tv_all_fans_counts.setText(String.valueOf(mDataList.get(position).getAllFansCounts()));
		
		if(position == mDataList.size()-1){
			viewHolder.v_line.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	
    class ViewHolder{
		public TextView tv_date_focus,tv_add_focus_counts,tv_delete_focus_counts,tv_all_fans_counts;
		public View v_line;
    }
}
