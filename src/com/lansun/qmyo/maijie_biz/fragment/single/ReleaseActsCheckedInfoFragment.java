package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.MainActivity;
import com.lansun.qmyo.maijie_biz.adapter.DetailHeaderDeletePagerAdapter;
import com.lansun.qmyo.maijie_biz.adapter.PhotoShowAdapter;
import com.lansun.qmyo.maijie_biz.bean.ActivityInfoBean;
import com.lansun.qmyo.maijie_biz.db.DataBaseManager;
import com.lansun.qmyo.maijie_biz.db.DbInfos;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.view.ImageGalleryDeleteDialog;
import com.lansun.qmyo.maijie_biz.view.ImageGalleryDeleteDialog.OnNotifyGridViewListener;
import com.umeng.analytics.MobclickAgent;

public class ReleaseActsCheckedInfoFragment extends HeaderFragment implements OnClickListener,OnNotifyGridViewListener {
	

	private ViewGroup rootView;
	private Button bt_nextstep;
    private TextView    tv_store_name,tv_activity_degree,
						tv_activity_classfication,
						tv_activity_tag,tv_activity_name,
						tv_activity_deatials,tv_activity_end,
						tv_activity_start,tv_activity_duration;

	private GridView gv_select_photos;
	private SQLiteDatabase db;
	private ActivityInfoBean bean;
	private ArrayList<String> picList;
	private ArrayList<String> detailList;
	private ArrayList<String> rulesList;
	private ImageGalleryDeleteDialog imageGalleryDeleteDialog;
	private DetailHeaderDeletePagerAdapter headPagerAdapter;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		db = DataBaseManager.getInstance().getDataBase(DbInfos.DB_NAME);
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_releaseactsinfo_checked, null);
		rootView = contentView;
		initData();
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	/**
	 * 从数据库中拿到之前存入本地数据库的 内容
	 */
	private void initData() {
		//首先拿到最近一次输入的一行数据内容
		int rows = 0;
		Cursor cur   =  db.rawQuery("select count(*) from "+DbInfos.ReleaseActivityTableField.TB_NAME, null);
		rows = cur.getCount();
		System.out.println("(rawQuery方式)表中的数据行数：   "+ rows);
		
	    SQLiteStatement statement = db.compileStatement("select count(*) from "+DbInfos.ReleaseActivityTableField.TB_NAME);
	    long count = statement.simpleQueryForLong();
	    System.out.println("(通过SQLiteStatement)表中的数据行数：   "+ count);
		
		Cursor cursor = db.rawQuery("select * from "+DbInfos.ReleaseActivityTableField.TB_NAME+
				" where "+DbInfos.ReleaseActivityTableField._ID+" = ?", new String[]{String.valueOf(count)});
		
		// 如果可以移动到第一条数据, 说明查询到了至少一个结果
		if(cursor.moveToFirst()){
			// getColumnIndex获取指定列名的索引号
			
			int _id = cursor.getInt(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField._ID));
			/*String store_name = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.STORE_NAME));*/
			String activity_name = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_NAME));
			String activity_degree = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_DEGREE));
			String activity_classfication = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_CLASSFICATION));
			String activity_tag = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_TAG));
			String activity_title = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_TITLE));
			String activity_details = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_DETAIL));
			String activity_rules = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_RULES));
			String activity_pic_path = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.PICTURE_PATH));
			String activity_duration = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_DURATION));
			String activity_end = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_END));
			String activity_start = 
					cursor.getString(cursor.getColumnIndex(DbInfos.ReleaseActivityTableField.ACTIVITY_START));
			
			System.out.println(activity_name
								+activity_degree
								+activity_classfication
								+activity_tag
								+activity_title
								+activity_details
								+activity_rules
								+activity_pic_path
								+activity_tag
								+activity_duration
								+activity_start
								+activity_end);
			
			//A.处理图片路径
			picList = new ArrayList<String>();
			String[] splitPath = activity_pic_path.split("\\%\\^\\&");
			for(int i=0;i<splitPath.length;i++ ){
				picList.add(splitPath[i]);
				System.out.println(splitPath[i]);
			}
			/*//B.处理活动详情
			detailList = new ArrayList<String>();
			String[] splitDetails = activity_details.split("\\%\\^\\&");
			for(int i=0;i<splitDetails.length;i++ ){
				detailList.add(splitDetails[i]);
				System.out.println(splitDetails[i]);
			}
			//C.处理使用规则
			rulesList = new ArrayList<String>();
			String[] splitRules = activity_rules.split("\\%\\^\\&");
			for(int i=0;i<splitRules.length;i++ ){
				rulesList.add(splitRules[i]);
				System.out.println(splitRules[i]);
			}*/
			
			
			bean = new ActivityInfoBean();
			bean._name          = activity_name;
			bean._title         = activity_title;
			bean._degree        = activity_degree;
			bean._classfication = activity_classfication;
			bean._duration      = activity_duration;
			bean._end           = activity_end;
			bean._start         = activity_start;
			bean._tag           = activity_tag;
			
			/*bean._picture_path  = ;
			bean._rules         = ;
			bean._detail        = ;*/
		}
	}

	private void initView() {
		bt_nextstep =               (Button)   rootView.findViewById(R.id.bt_nextstep);
		tv_store_name =             (TextView) rootView.findViewById(R.id.tv_store_name);//暂无
		tv_activity_degree =        (TextView) rootView.findViewById(R.id.tv_activity_degree);
		tv_activity_classfication = (TextView) rootView.findViewById(R.id.tv_activity_classfication);
		tv_activity_tag =           (TextView) rootView.findViewById(R.id.tv_activity_tag);
		tv_activity_name =          (TextView) rootView.findViewById(R.id.tv_activity_name);
		tv_activity_deatials =      (TextView) rootView.findViewById(R.id.tv_activity_deatials);//暂无
		tv_activity_duration =      (TextView) rootView.findViewById(R.id.tv_activity_duration);
		tv_activity_start =         (TextView) rootView.findViewById(R.id.tv_activity_start);
		tv_activity_end =           (TextView) rootView.findViewById(R.id.tv_activity_end);
		
		
		bt_nextstep.setOnClickListener(this);
		gv_select_photos = (GridView) rootView.findViewById(R.id.gv_select_photos);
		PhotoShowAdapter adapter = new PhotoShowAdapter((Activity)mContext, picList);
		gv_select_photos.setAdapter(adapter);
		gv_select_photos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 openPhotoDetails(position);
			}
		});
		
		         
		tv_activity_classfication.setText(bean._degree);   
		tv_activity_tag.setText(bean._tag);   
		tv_activity_degree.setText(bean._degree);   
		tv_store_name.setText(bean._name);   
		tv_activity_duration.setText(bean._duration);   
		tv_activity_end.setText(bean._end);   
		tv_activity_start.setText(bean._start);   
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
	}

	@Override
	protected String getTitle() {
		return getString(R.string.about_us);
	}

	@Override
	protected int getMenuResId() {
		return -1;
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("");
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd(""); 
	}

	@Override
	public boolean onKeyDown(int keyCode) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			close();
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_nextstep:
			Fragment f = new ActivityPreShowFragment();
			((MainActivity)getActivity()).entrySubFragment(f);
			break;
		default:
			break;
		}		
		super.onClick(v);
	}

	
	public void openPhotoDetails(int position){
		headPagerAdapter = new DetailHeaderDeletePagerAdapter(mContext, picList);
		imageGalleryDeleteDialog = ImageGalleryDeleteDialog.newInstance(headPagerAdapter, position,picList);
		imageGalleryDeleteDialog.setOnNotifyGridViewListener(this);
		imageGalleryDeleteDialog.show(getFragmentManager(), "GalleryCannotDelete");
	}
	
	@Override
	public void notifyGridView() {
		//前面不执行图片的删除操作
		//故此处不做任何的更新操作
	}
	@Override
	public void notifyGridView(int i) {
		//前面不执行图片的删除操作
		//故此处不做任何的更新操作
	}

}
