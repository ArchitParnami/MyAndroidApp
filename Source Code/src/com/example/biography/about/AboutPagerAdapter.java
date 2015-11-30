package com.example.biography.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class AboutPagerAdapter extends FragmentStatePagerAdapter{

	String[] columnNames;
	String[] data;
	final int item_count;
	
	public AboutPagerAdapter(FragmentManager fm, String[] columnNames, String[] data) {
		super(fm);
		
		this.columnNames = columnNames;
		this.data = data;
		item_count = columnNames.length;
	}
	
	@Override
	public Fragment getItem(int pos) {
		AboutChildFragment fragment = new AboutChildFragment();
		Bundle bundle = new Bundle();
	
		bundle.putString(AboutChildFragment.CHILD_NAME, columnNames[pos]);
		bundle.putString(AboutChildFragment.CHILD_CONTENT, data[pos]);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public int getCount() {
		return item_count;
	}
	
}
