package com.example.biography.videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.biography.Biography;
import com.example.biography.R;

public class VideosFragment extends Fragment {
	ListView lv;
	YouTubeVideo[] videos = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.videos, null, true);
		lv = (ListView)v.findViewById(R.id.videoList);
		videos = Biography.db.getAllVideos();
		lv.setAdapter(new VideoListAdapter(getActivity(), videos));

		return v;
	}
}
