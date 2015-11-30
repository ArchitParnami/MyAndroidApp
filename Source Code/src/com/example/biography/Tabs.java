package com.example.biography;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.example.biography.about.AboutFragment;
import com.example.biography.awards.AwardsFragment;
import com.example.biography.news.NewsFragment;
import com.example.biography.videos.VideosFragment;

public class Tabs extends FragmentActivity {
	
	private FragmentTabHost mTabHost;
	private View currentTab;
	private int GRAY= Color.parseColor("#A4A4A4");
	
	public static ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs);
	
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
			
		mTabHost.addTab(mTabHost.newTabSpec("about").setIndicator("About", getResources().getDrawable(R.drawable.about)), AboutFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("awards").setIndicator("Awards", getResources().getDrawable(R.drawable.awards)),AwardsFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("news").setIndicator("News", getResources().getDrawable(R.drawable.news)), NewsFragment.class, null);
		//mTabHost.addTab(mTabHost.newTabSpec("share").setIndicator("Share", getResources().getDrawable(R.drawable.share)), ShareFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("videos").setIndicator("Videos", getResources().getDrawable(R.drawable.videos)), VideosFragment.class, null);
		
		setTabColor(mTabHost);
		
		currentTab = mTabHost.getCurrentTabView();
		currentTab.setBackgroundColor(GRAY);
		
		mTabHost.setOnTabChangedListener( new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				currentTab.setBackgroundColor(Color.WHITE);
				currentTab = mTabHost.getCurrentTabView();
				currentTab.setBackgroundColor(GRAY);
				
			}
		});
		
		imageLoader = new ImageLoader(this);
		
	}
	
	private void setTabColor(FragmentTabHost tabHost) {
		
		for(int i=0; i< tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
			TextView tv = (TextView)tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			tv.setTextColor(Color.BLACK);
		}
		
	}
	
}
