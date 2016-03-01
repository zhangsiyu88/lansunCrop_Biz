package com.lansun.qmyo.maijie_biz.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;



public class ListViewAdapter extends BaseAdapter {

	public List<HashMap<String,Object>>  dataList ;
	public Context mContext;
	private LayoutInflater inflater;
	private TextView tv_desc;
	private TextView tv_degree;
	private TextView tv_effecttime;
	private ViewHolder viewHolder;
	public int mFlag;
	
	
	/*
	 * 利用构造方法将上下文和数据传入进来
	 */
	public  ListViewAdapter(Context context,List<HashMap<String,Object>> list,int flag){
		mContext = context;
		dataList = list;
		inflater = LayoutInflater.from(context);
		mFlag = flag;
	}
	
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_listview_actsandstate_auditing, null);
			
			viewHolder.tv_actsdesc = (TextView) convertView.findViewById(R.id.tv_actsdesc);
			viewHolder.tv_actsdegree= (TextView) convertView.findViewById(R.id.tv_actsdegree);
			viewHolder.tv_actseffectivehours = (TextView) convertView.findViewById(R.id.tv_actseffectivetime);
			viewHolder.tv_actsstatue = (TextView) convertView.findViewById(R.id.tv_actsstatue);
			viewHolder.iv_actsstatue = (ImageView) convertView.findViewById(R.id.iv_actsstatue);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tv_actsdesc.setText((CharSequence) dataList.get(position).get("actsdesc"));
		System.out.println((CharSequence) dataList.get(position).get("actsdesc"));
		viewHolder.tv_actsdegree.setText((CharSequence) dataList.get(position).get("actsdegree"));
		viewHolder.tv_actseffectivehours.setText((CharSequence) dataList.get(position).get("actseffectivehours"));
		
		//同一个ListView控件就是通过筛选展示四个不同的ListView
		//通过Flag来判断
		switch (mFlag) {
			case 0:
				viewHolder.tv_actsstatue.setText("审核中");
				viewHolder.iv_actsstatue.setBackgroundResource(R.drawable.icon_auditing);
				break;
			case 1:
				viewHolder.tv_actsstatue.setText("待支付");
				viewHolder.iv_actsstatue.setBackgroundResource(R.drawable.icon_gotopay);
				break;
			case 2:
				viewHolder.tv_actsstatue.setText("已上线");
				viewHolder.iv_actsstatue.setBackgroundResource(R.drawable.icon_online);
				break;
			case 3:
				viewHolder.tv_actsstatue.setText("已下线");
				viewHolder.iv_actsstatue.setBackgroundResource(R.drawable.icon_offline);
				break;
				
			default:
				break;
		}
		return convertView;
	}

	class ViewHolder {
		public TextView tv_actsdesc;
		public TextView tv_actsdegree;
		public TextView tv_actseffectivehours;
		public TextView tv_actsstatue;
		public ImageView iv_actsstatue;
		
	}
}
