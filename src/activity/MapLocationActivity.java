package activity;

import android.util.Log;
import base.BaseMapActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.leo.maptest.R;

public class MapLocationActivity extends BaseMapActivity implements LocationSource,AMapLocationListener{
	
	public static final String TAG = "MapLocationActivity";
	
	//定位监听
	private OnLocationChangedListener changedListener;
	
	//定位客户端
	private AMapLocationClient locationClient;
	
	@Override
	protected int getContentViewId() {
		return R.layout.activity_map;
	}

	@Override
	protected int getMapViewId() {
		return R.id.ac_map_map;
	}
	
	@Override
	protected void initData() {
		setUpAMap();
	}
	
	/**
	 * 设置AMAP
	 */
	private void setUpAMap() {
		MyLocationStyle locationStyle = new MyLocationStyle();
		locationStyle.myLocationIcon(
				BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
		locationStyle.strokeWidth(0.1f);
		
		aMap.setMyLocationStyle(locationStyle);
		aMap.setMyLocationRotateAngle(180);
		
		aMap.setLocationSource(this);//定位监听,activate和deactivate方法
		
		aMap.getUiSettings().setMyLocationButtonEnabled(true);//定位按钮是否会显示
		aMap.setMyLocationEnabled(true);//显示定位层并触发定位,默认false
		aMap.setMapType(AMap.LOCATION_TYPE_LOCATE);//定位模式,定位,跟随,旋转
		
		//3-19,越大缩放比例越大
//		aMap.moveCamera(CameraUpdateFactory.zoomTo(3));//设置缩放比例
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		changedListener = listener;
		if(locationClient == null){
			locationClient = new AMapLocationClient(this);
			locationClient.setLocationListener(this);//定位监听
			
			AMapLocationClientOption option = new AMapLocationClientOption();//定位模式和参数
			option.setLocationMode(AMapLocationMode.Hight_Accuracy);//高精度模式
			
			locationClient.setLocationOption(option);
			locationClient.startLocation();//启动定位
			
		}
	}

	
	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		changedListener = null;
		if (locationClient != null) {
			locationClient.stopLocation();//停止定位
			locationClient.onDestroy();//释放资源
		}
		locationClient = null;
	}

	private int key = 0;
	
	private boolean is = true;
	
	/**
	 * 回调成功后
	 * @param aMapLocation
	 */
	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		if (changedListener != null && aMapLocation != null) {
			if (aMapLocation.getErrorCode() == 0) {
				changedListener.onLocationChanged(aMapLocation);//显示小蓝点
				aMapLocation.getCity();//城市
				aMapLocation.getLatitude();//纬度
				aMapLocation.getLongitude();//经度
				if (key < 5) {
					Log.i(TAG, "城市是: " + aMapLocation.getCity() + " ,经度是: " + aMapLocation.getLongitude() + " ,经度是: " + aMapLocation.getLatitude());
					key++;
				}
				
//				float f = aMap.getCameraPosition().zoom;
//				Log.i(TAG, "zoom= " + f);
				
				if (is) {
					aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()), 15));
					is = false;
				}
				
			} else {
				Log.i(TAG, "定位失败");
			}
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		deactivate();//停止定位
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationClient != null) {
			locationClient.onDestroy();//释放资源
		}
	}

}
