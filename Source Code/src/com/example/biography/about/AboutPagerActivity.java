package com.example.biography.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.biography.R;

public class AboutPagerActivity extends FragmentActivity{

	public static int index = 0;
	ViewPager pager;
	PagerAdapter pagerAdapter;
	
	public static String COLUMN_NAMES = "columns";
	public static String COLUMN_DATA = "data";
	
	
	String[] columns;
	String[] data;
	
	int lastItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_pager);
		
		
		Intent in = getIntent();
		Bundle bundle = in.getExtras();
		columns = bundle.getStringArray(COLUMN_NAMES);
		data = bundle.getStringArray(COLUMN_DATA);
		
		
		pager = (ViewPager)findViewById(R.id.aboutPager);
		pagerAdapter = new AboutPagerAdapter(getSupportFragmentManager(),columns, data);
		pager.setAdapter(pagerAdapter);
	

	
	}

	@Override
	protected void onResume() {
		super.onResume();
		pager.setCurrentItem(index);
		
	}
	
}
