package com.example.biography.awards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.biography.Biography;
import com.example.biography.R;

public class AwardsFragment extends Fragment {
	
	ListView lv;
	Award awards[] = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.awards, null, true);
		lv = (ListView)v.findViewById(R.id.awardList);
		awards = Biography.db.getAllAwards();
		lv.setAdapter(new AwardListAdapter(getActivity(), awards));

		return v;
	}
}
