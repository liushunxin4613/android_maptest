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
	
	//��λ����
	private OnLocationChangedListener changedListener;
	
	//��λ�ͻ���
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
	 * ����AMAP
	 */
	private void setUpAMap() {
		MyLocationStyle locationStyle = new MyLocationStyle();
		locationStyle.myLocationIcon(
				BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
		locationStyle.strokeWidth(0.1f);
		
		aMap.setMyLocationStyle(locationStyle);
		aMap.setMyLocationRotateAngle(180);
		
		aMap.setLocationSource(this);//��λ����,activate��deactivate����
		
		aMap.getUiSettings().setMyLocationButtonEnabled(true);//��λ��ť�Ƿ����ʾ
		aMap.setMyLocationEnabled(true);//��ʾ��λ�㲢������λ,Ĭ��false
		aMap.setMapType(AMap.LOCATION_TYPE_LOCATE);//��λģʽ,��λ,����,��ת
		
		//3-19,Խ�����ű���Խ��
//		aMap.moveCamera(CameraUpdateFactory.zoomTo(3));//�������ű���
	}

	/**
	 * ���λ
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		changedListener = listener;
		if(locationClient == null){
			locationClient = new AMapLocationClient(this);
			locationClient.setLocationListener(this);//��λ����
			
			AMapLocationClientOption option = new AMapLocationClientOption();//��λģʽ�Ͳ���
			option.setLocationMode(AMapLocationMode.Hight_Accuracy);//�߾���ģʽ
			
			locationClient.setLocationOption(option);
			locationClient.startLocation();//������λ
			
		}
	}

	
	/**
	 * ֹͣ��λ
	 */
	@Override
	public void deactivate() {
		changedListener = null;
		if (locationClient != null) {
			locationClient.stopLocation();//ֹͣ��λ
			locationClient.onDestroy();//�ͷ���Դ
		}
		locationClient = null;
	}

	private int key = 0;
	
	private boolean is = true;
	
	/**
	 * �ص��ɹ���
	 * @param aMapLocation
	 */
	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		if (changedListener != null && aMapLocation != null) {
			if (aMapLocation.getErrorCode() == 0) {
				changedListener.onLocationChanged(aMapLocation);//��ʾС����
				aMapLocation.getCity();//����
				aMapLocation.getLatitude();//γ��
				aMapLocation.getLongitude();//����
				if (key < 5) {
					Log.i(TAG, "������: " + aMapLocation.getCity() + " ,������: " + aMapLocation.getLongitude() + " ,������: " + aMapLocation.getLatitude());
					key++;
				}
				
//				float f = aMap.getCameraPosition().zoom;
//				Log.i(TAG, "zoom= " + f);
				
				if (is) {
					aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()), 15));
					is = false;
				}
				
			} else {
				Log.i(TAG, "��λʧ��");
			}
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		deactivate();//ֹͣ��λ
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationClient != null) {
			locationClient.onDestroy();//�ͷ���Դ
		}
	}

}
