package activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

@SuppressLint("SimpleDateFormat") 
public class LocationActivity extends Activity implements AMapLocationListener{

	//定位客户端
	private AMapLocationClient locationClient;

	//发起定位的模式和参数
	private AMapLocationClientOption locationClientOption;

	private AMapLocationMode locationArr[] = {
			//双定位,返回精度高的
			AMapLocationMode.Hight_Accuracy
			//仅网络
			,AMapLocationMode.Battery_Saving
			//仅GPS,不支持室内和地址描述信息
			,AMapLocationMode.Device_Sensors
	};

	//定位间隔时间
	private int interval = 3000;
	//网络超时时间
	private int timeOut = 10000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		locationClient = new AMapLocationClient(this);

		locationClient.setLocationListener(this);

		locationClientOption = new AMapLocationClientOption();

		//定位模式
		locationClientOption.setLocationMode(locationArr[0]);

		//开始定位
		//一次定位
		locationClientOption.setOnceLocation(true);

		//返回3s内精度最高的一次
		locationClientOption.setOnceLocationLatest(true);

		//默认连续定位,时间间隔为2000ms,最少1000ms
		locationClientOption.setInterval(interval);

		//是否返回地址信息,默认返回
		locationClientOption.setNeedAddress(true);

		//是否强制刷新wifi,默认不强制false
		locationClientOption.setWifiActiveScan(false);

		//是否允许模拟位置,默认不许false
		locationClientOption.setMockEnable(false);

		//网络定位超时时间
		locationClientOption.setHttpTimeOut(timeOut);

		//设置参数
		locationClient.setLocationOption(locationClientOption);

		//启动定位
		locationClient.startLocation();

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {

		if (amapLocation != null) {
			//为0则定位成功
			if (amapLocation.getErrorCode() == 0) {
				//定位成功
				//				amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
				double w = amapLocation.getLatitude();//获取纬度
				double j = amapLocation.getLongitude();//获取经度
				//				amapLocation.getAccuracy();//获取精度信息
				//				String s = amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
				//				amapLocation.getCountry();//国家信息
				//				amapLocation.getProvince();//省信息
				//				amapLocation.getCity();//城市信息
				//				amapLocation.getDistrict();//城区信息
				//				amapLocation.getStreet();//街道信息
				//				amapLocation.getStreetNum();//街道门牌号信息
				//				amapLocation.getCityCode();//城市编码
				//				amapLocation.getAdCode();//地区编码
				//				amapLocation.getAoiName();//获取当前定位点的AOI信息
				//获取定位时间
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(amapLocation.getTime());
				df.format(date);
				Log.i("MainActivity", "经度: " + j + " ,纬度: "+ w);
				//				Log.i("MainActivity", "地址: " + s);

			} else {
				//错误码
				int i = amapLocation.getErrorCode();
				//错误信息
				String s = amapLocation.getErrorInfo();
				Log.e("MainActivity", "错误码: " + i + " ,错误原因: "+ s);
			}

		}

	}
}
