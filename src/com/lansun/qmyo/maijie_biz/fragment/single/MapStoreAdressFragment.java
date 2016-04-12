package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.BizCooperationActivity;
import com.lansun.qmyo.maijie_biz.adapter.LocationAddressAdapter;
import com.lansun.qmyo.maijie_biz.bean.AddressEntity;
import com.lansun.qmyo.maijie_biz.eventbus.EventBus;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

public class MapStoreAdressFragment extends HeaderFragment implements LocationSource,AMapLocationListener,OnItemClickListener{

	private SupportMapFragment fragment;
	private AMap aMap;
	private UiSettings mUiSettings;
	private LatLng latLng;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy aMapManager;
	private AMapLocation aMapLocation;
	private ViewGroup rootView;
	private PullToRefreshListView lv_location_address;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_store_map, null);
		rootView = contentView;
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initView() {
		lv_location_address = (PullToRefreshListView) rootView.findViewById(R.id.lv_location_address);
		lv_location_address.setOnItemClickListener(this);
		ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
		HashMap hashMap = new HashMap();
		for(int i=0;i<20;i++){
			hashMap.put("location_address","国顺东路800号东楼"+i);
			arrayList.add(hashMap);
		}
		LocationAddressAdapter adapter = new LocationAddressAdapter(getActivity(),arrayList,-1);
		lv_location_address.setAdapter(adapter);
		
		if (aMap == null) {
			fragment = ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map));
			aMap = fragment.getMap();
		}
		//设置定位（我的位置）样式
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationEnabled(true);//我的位置功能打开

		mUiSettings = aMap.getUiSettings();
		mUiSettings.setZoomControlsEnabled(false);
		mUiSettings.setCompassEnabled(false);
		mUiSettings.setScaleControlsEnabled(true);
		mUiSettings.setMyLocationButtonEnabled(true);
		
		String Lat =  "31.30017";
		String Lng = "121.52524";
		latLng = new LatLng(Double.parseDouble(Lat),Double.parseDouble(Lng));
		/*LatLng shopLatLng = new LatLng(GlobalValue.gps.getWgLat(),GlobalValue.gps.getWgLon());*/
		LatLng shopLatLng = latLng;
		
//		LatLng mylocatin = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
//		
//		String address = aMapLocation.getAddress();
//		System.out.println("地址为："+address);
//		
//		//视点定位在 输入的地理坐标上
//		CameraUpdate came = CameraUpdateFactory.newCameraPosition(new CameraPosition(mylocatin, 18, 30, 0));
//		aMap.animateCamera(came);
		
		MarkerOptions shopMarker = new MarkerOptions().anchor(0.5f, 0.5f).position(shopLatLng).title("NASA").snippet("国防部45-60楼");
		aMap.addMarker(shopMarker);
		
		aMap.setLocationSource(this);// 设置定位监听
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

	
	//------------------------------AMAP-----------------------------------------------------
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null) {
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
		}
		if (aLocation != null) {
			this.aMapLocation = aLocation;// 判断超时机制
			Double geoLat = aLocation.getLatitude();
			Double geoLng = aLocation.getLongitude();
			
			
		}
		
	}

	
	//----------------------------------------LocationSource--------------------------------------------------------
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (aMapManager == null) {
			aMapManager = LocationManagerProxy.getInstance(getActivity());
			aMapManager.requestLocationData(LocationProviderProxy.AMapNetwork,2000, 10, this);
		}
		LatLng mylocatin = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
		//视点定位在 输入的地理坐标上
		CameraUpdate came = CameraUpdateFactory.newCameraPosition(new CameraPosition(mylocatin, 18, 30, 0));
		aMap.animateCamera(came);
		
		
		
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (aMapManager != null) {
			aMapManager.removeUpdates(this);
			aMapManager.destroy();
		}
		aMapManager = null;
		unRegisterSensorListener();
		
	}
	
	public void registerSensorListener() {
	}
	public void unRegisterSensorListener() {
	}
	
	//------------------------------AMAP-----------------------------------------------------
	/**
	 * 销毁定位
	 */
	private void stopLocation() {
		if (aMapManager != null) {
			aMapManager.removeUpdates(this);
			aMapManager.destroy();
		}
		aMapManager = null;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		lToast("GetIt！选中！");
		//关闭掉当前页面
		((BizCooperationActivity)(getActivity())).getSupportFragmentManager().popBackStack();
		//将选中的地址会写至前面的页面上
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.address = "国顺东路800号东楼"+position;
		EventBus.getDefault().post(addressEntity);
	}
	
	
}
