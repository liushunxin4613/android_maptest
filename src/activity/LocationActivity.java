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

	//��λ�ͻ���
	private AMapLocationClient locationClient;

	//����λ��ģʽ�Ͳ���
	private AMapLocationClientOption locationClientOption;

	private AMapLocationMode locationArr[] = {
			//˫��λ,���ؾ��ȸߵ�
			AMapLocationMode.Hight_Accuracy
			//������
			,AMapLocationMode.Battery_Saving
			//��GPS,��֧�����ں͵�ַ������Ϣ
			,AMapLocationMode.Device_Sensors
	};

	//��λ���ʱ��
	private int interval = 3000;
	//���糬ʱʱ��
	private int timeOut = 10000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		locationClient = new AMapLocationClient(this);

		locationClient.setLocationListener(this);

		locationClientOption = new AMapLocationClientOption();

		//��λģʽ
		locationClientOption.setLocationMode(locationArr[0]);

		//��ʼ��λ
		//һ�ζ�λ
		locationClientOption.setOnceLocation(true);

		//����3s�ھ�����ߵ�һ��
		locationClientOption.setOnceLocationLatest(true);

		//Ĭ��������λ,ʱ����Ϊ2000ms,����1000ms
		locationClientOption.setInterval(interval);

		//�Ƿ񷵻ص�ַ��Ϣ,Ĭ�Ϸ���
		locationClientOption.setNeedAddress(true);

		//�Ƿ�ǿ��ˢ��wifi,Ĭ�ϲ�ǿ��false
		locationClientOption.setWifiActiveScan(false);

		//�Ƿ�����ģ��λ��,Ĭ�ϲ���false
		locationClientOption.setMockEnable(false);

		//���綨λ��ʱʱ��
		locationClientOption.setHttpTimeOut(timeOut);

		//���ò���
		locationClient.setLocationOption(locationClientOption);

		//������λ
		locationClient.startLocation();

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {

		if (amapLocation != null) {
			//Ϊ0��λ�ɹ�
			if (amapLocation.getErrorCode() == 0) {
				//��λ�ɹ�
				//				amapLocation.getLocationType();//��ȡ��ǰ��λ�����Դ�������綨λ����������λ���ͱ�
				double w = amapLocation.getLatitude();//��ȡγ��
				double j = amapLocation.getLongitude();//��ȡ����
				//				amapLocation.getAccuracy();//��ȡ������Ϣ
				//				String s = amapLocation.getAddress();//��ַ�����option������isNeedAddressΪfalse����û�д˽�������綨λ����л��е�ַ��Ϣ��GPS��λ�����ص�ַ��Ϣ��
				//				amapLocation.getCountry();//������Ϣ
				//				amapLocation.getProvince();//ʡ��Ϣ
				//				amapLocation.getCity();//������Ϣ
				//				amapLocation.getDistrict();//������Ϣ
				//				amapLocation.getStreet();//�ֵ���Ϣ
				//				amapLocation.getStreetNum();//�ֵ����ƺ���Ϣ
				//				amapLocation.getCityCode();//���б���
				//				amapLocation.getAdCode();//��������
				//				amapLocation.getAoiName();//��ȡ��ǰ��λ���AOI��Ϣ
				//��ȡ��λʱ��
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(amapLocation.getTime());
				df.format(date);
				Log.i("MainActivity", "����: " + j + " ,γ��: "+ w);
				//				Log.i("MainActivity", "��ַ: " + s);

			} else {
				//������
				int i = amapLocation.getErrorCode();
				//������Ϣ
				String s = amapLocation.getErrorInfo();
				Log.e("MainActivity", "������: " + i + " ,����ԭ��: "+ s);
			}

		}

	}
}
