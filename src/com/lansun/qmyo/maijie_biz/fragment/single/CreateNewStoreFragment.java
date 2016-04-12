package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.BizCooperationActivity;
import com.lansun.qmyo.maijie_biz.bean.AddressEntity;
import com.lansun.qmyo.maijie_biz.db.DataBaseManager;
import com.lansun.qmyo.maijie_biz.db.DbInfos;
import com.lansun.qmyo.maijie_biz.eventbus.EventBus;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.utils.DataPickerUtils;
import com.lansun.qmyo.maijie_biz.utils.DataPickerUtils.OnRoll2SelectListener;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil;
import com.lansun.qmyo.maijie_biz.utils.DialogUtil.TipAlertDialogCallBack;
import com.umeng.analytics.MobclickAgent;


/**
 * 功能清单：
 *     1、键盘的弹出与消失控制
 *     2、界面的WindowManager控制
 *     3、DataPicker内部数据的选取
 *     4、所填内容向本地数据库的写入操作
 * 
 * 
 * @author  Yeun.Zhang
 *
 */
public class CreateNewStoreFragment extends HeaderFragment implements OnClickListener,OnRoll2SelectListener{
	
	
	private ViewGroup rootView;
	private RelativeLayout rl_store_address;
	private Button btn_crate_claim;
	private TextView tv_select_location_address;
	private SQLiteDatabase db;
	private RelativeLayout rl_address_city;
	private RelativeLayout rl_address_biz_area;
	private TextView tv_address_biz_area;
	private TextView tv_address_city;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_create_new_store, null);
		rootView = contentView;
		initView();
		
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	public void initView(){
		EventBus.getDefault().register(this);
		
		db = DataBaseManager.getInstance().getDataBase(DbInfos.DB_NAME);
				
		rl_store_address = (RelativeLayout) rootView.findViewById(R.id.rl_store_address);
		
		rl_address_city = (RelativeLayout) rootView.findViewById(R.id.rl_address_city);
		rl_address_biz_area = (RelativeLayout) rootView.findViewById(R.id.rl_address_biz_area);
		tv_address_city = (TextView) rootView.findViewById(R.id.tv_address_city);
		tv_address_biz_area = (TextView) rootView.findViewById(R.id.tv_address_biz_area);
		btn_crate_claim = (Button) rootView.findViewById(R.id.btn_crate_claim);
		tv_select_location_address = (TextView) rootView.findViewById(R.id.tv_select_location_address);
		
		rl_store_address.setOnClickListener(this);
		rl_address_city.setOnClickListener(this);
		rl_address_biz_area.setOnClickListener(this);
		btn_crate_claim.setOnClickListener(this);
		
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
	
	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		ArrayList<String> data = new ArrayList<String>();
		Fragment f;
		switch (v.getId()) {
		case R.id.rl_store_address:
			//1.使用Fragment
			f= new MapStoreAdressFragment();
			((BizCooperationActivity)(getActivity())).entrySubFragment(f);
			//2.使用Activity
			break;
		case R.id.rl_address_city://所在城市的DataPicker
			data.clear();
			data.add("北京");
			data.add("上海");
//			selectDialog(data, "city");
			DataPickerUtils instance = DataPickerUtils.getInstance((Activity) mContext,data);
//			instance.setmContext(getActivity());
			instance.setAffectView(tv_address_city);
			instance.popDataPicker();
			instance.setRoll2SelectListener(this);
			break;
		case R.id.rl_address_biz_area:
			data.clear();
			data.add("人民广场");
			data.add("五角场");
			data.add("新世界城");
			data.add("西直门");
			DataPickerUtils _instance = DataPickerUtils.getInstance(getActivity(), data);
			_instance.setAffectView(tv_address_biz_area);
			_instance.popDataPicker();
			_instance.setRoll2SelectListener(this);
			break;
		case R.id.btn_crate_claim:
			lToast("信息不全，请检查后，再次尝试");
			DialogUtil.createCommonDialog(getActivity(), "请仔细检查您所创建的门店", 
					"Meliferart(新天地店)\n上海市黄浦区人民广场巴拉巴拉~~", 
					"确定创建", "取消", new TipAlertDialogCallBack() {
				@Override
				public void onPositiveButtonClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					//0.当前页面的销毁，数据写入本地数据库中
					int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
					if(count!=0){
						for(int i=0;i<=count;i++){
							getActivity().getSupportFragmentManager().popBackStack();
						}
					}
					
					//1.将写好的输入好的数据送入到数据库里存储起来
					putDataIntoDb();
					//2.将数据从数据库中拿到手，再回传给商业合作信息页面展示
					getDataFromDb();
					EventBus.getDefault().post("来自创建门店页面");
					
				}
				@Override
				public void onNegativeButtonClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
					});
			
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	/**
	 * 常规Java工程中，需要使用DAO方式去访问数据库，下面实现简略版本的数据库的读取操作
	 */
	protected void getDataFromDb() {
		
		Cursor cursor = db.rawQuery("select * from "+DbInfos.NewStoreTableField.TB_NAME+
				" where "+DbInfos.NewStoreTableField._ID+" = ?", new String[]{String.valueOf(2004)});
		
		// 如果可以移动到第一条数据, 说明查询到了至少一个结果
		if(cursor.moveToFirst()){
			// getColumnIndex获取指定列名的索引号
			
			int _id = cursor.getInt(cursor.getColumnIndex(DbInfos.NewStoreTableField._ID));
			String store_name = cursor.getString(cursor.getColumnIndex(DbInfos.NewStoreTableField.STORE_NAME));
			String biz_area = cursor.getString(cursor.getColumnIndex(DbInfos.NewStoreTableField.BIZ_AREA));
			String city = cursor.getString(cursor.getColumnIndex(DbInfos.NewStoreTableField.CITY));
			String store_address = cursor.getString(cursor.getColumnIndex(DbInfos.NewStoreTableField.STORE_ADDRESS));
			String store_address_plus = cursor.getString(cursor.getColumnIndex(DbInfos.NewStoreTableField.STORE_ADDRESS_PLUS));
			
			System.out.println(store_name+store_address+store_address_plus+city+biz_area);
		}
		
	}

	@SuppressWarnings("static-access")
	protected void putDataIntoDb() {
		/**
		 * 1、门店名称
		 * 2、定位地址
		 * 3、相关楼层信息
		 * 4、门店所在城市
		 * 5、门店所属商圈
		 * 
		 */

		//以事物的方式将内容写入本地数据库
		long start = System.currentTimeMillis();
		db.beginTransaction();
		try {
			for (int i = 0; i < 2000; i++) {
				//注意下面写入数据表中的数据格式，该加""的记得加双引号""
				db.execSQL("insert into "+DbInfos.NewStoreTableField.TB_NAME+
						" (" +
						DbInfos.NewStoreTableField.STORE_NAME+"," +
						DbInfos.NewStoreTableField.STORE_ADDRESS+"," +
						DbInfos.NewStoreTableField.STORE_ADDRESS_PLUS+"," +
						DbInfos.NewStoreTableField.CITY+"," +
						DbInfos.NewStoreTableField.BIZ_AREA+
						")" +
						"values ('呷哺呷哺"+i+"',"+"'新天地"+i+"',"+"'新世界城4楼"+i+"',"+"'上海"+i+"',"+"'人民广场"+i+ "')" );
//				  ("呷哺呷哺100"，"新天地100"，"新世界城100"，"上海100"，"人民广场100")
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			long duration = System.currentTimeMillis() - start;
			System.out.println("插入完毕, 总共用时: " + duration);
		}
	}

	public void onEventMainThread(Object item) {  
	 if(item instanceof AddressEntity){
		 tv_select_location_address.setText(((AddressEntity) item).address);
	 }
	}
	


	@Override
	public void roll2select(String str) {
		//对选中的数据 str 进行网络访问
		lToast("走你，发送给服务器咯~"+str);
		//或者写入本地~
		lToast("走你，写到本地咯~"+str);
	}
}
