package com.example.biography.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.biography.Biography;
import com.example.biography.R;

public class NewsFragment extends Fragment {
	
	ListView lv;
	News[] news = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.news, null, true);
		lv = (ListView)v.findViewById(R.id.newsList);
		news = Biography.db.getAllNews();
		lv.setAdapter(new NewsListAdapter(getActivity(), news));
		
		return v;
	}
}
