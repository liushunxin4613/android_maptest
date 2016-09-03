package com.leo.maptest;

import activity.LocationActivity;
import activity.MapLocationActivity;
import activity.ServiceActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity implements OnItemClickListener{
	
	private ArrayAdapter<String> adapter;
	private Class<?> classArr[] = {
			LocationActivity.class
			,ServiceActivity.class
			,MapLocationActivity.class
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new ArrayAdapter<String>(this, R.layout.item_textview, new String[]{"定位","service绑定调用","地图"});
		
		setListAdapter(adapter);
		
		getListView().setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
		startActivity(new Intent(this, classArr[i]));
	}

}
