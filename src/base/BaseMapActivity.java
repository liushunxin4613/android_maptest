package base;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseMapActivity extends Activity{
	
	private MapView mapView;
	protected AMap aMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(getContentViewId());
		
		mapView = (MapView) findViewById(getMapViewId());
		
		mapView.onCreate(savedInstanceState);//����
		
		init();
		
	}
	
	/**
	 * ��ȡ����ͼ
	 * @return
	 */
	protected abstract int getContentViewId();
	
	/**
	 * ��ȡmapview id
	 * @return
	 */
	protected abstract int getMapViewId();

	/**
	 * ��ʼ��amap
	 */
	private void init() {
		if(aMap == null){
			aMap = mapView.getMap();
			initData();
		}
	}
	
	/**
	 * ��ʼ��amap֮�����
	 */
	protected void initData(){
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

}
