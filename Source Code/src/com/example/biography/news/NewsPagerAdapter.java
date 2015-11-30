package com.example.biography.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class NewsPagerAdapter extends FragmentStatePagerAdapter{
	
	final int itemCount;
	
	public NewsPagerAdapter(FragmentManager fm, int count) {
		super(fm);
		itemCount = count;
	}
	
	@Override
	public Fragment getItem(int i) {
		
		Fragment fragment = new NewsChildFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(NewsChildFragment.NEWS_ID, i);
		fragment.setArguments(bundle);
		return fragment;
		
	}
	
	@Override
	public int getCount() {
		return itemCount;
	}
	
}
