package com.example.biography.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.biography.R;

public class NewsPagerActivity extends FragmentActivity {

	public static String ITEM_COUNT = "count";
	public static int currentItem = 0;
	ViewPager pager;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_news_pager);
		
		Intent intent = getIntent();
		int count = intent.getIntExtra(ITEM_COUNT,1);
		
		pager = (ViewPager)findViewById(R.id.newsPager);
		NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(), count);
		
		pager.setAdapter(newsPagerAdapter);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		pager.setCurrentItem(currentItem);
	}
}
