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
		
		mapView.onCreate(savedInstanceState);//加载
		
		init();
		
	}
	
	/**
	 * 获取根视图
	 * @return
	 */
	protected abstract int getContentViewId();
	
	/**
	 * 获取mapview id
	 * @return
	 */
	protected abstract int getMapViewId();

	/**
	 * 初始化amap
	 */
	private void init() {
		if(aMap == null){
			aMap = mapView.getMap();
			initData();
		}
	}
	
	/**
	 * 初始化amap之后操作
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
