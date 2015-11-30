package com.example.biography.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.biography.Biography;
import com.example.biography.R;

public class AboutFragment extends Fragment{

	private ListView lv;
	
	public String[] aboutCols = null;
	public String[] aboutColsData = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.about, null, true);
		lv = (ListView) v.findViewById(R.id.aboutList);
		
		aboutCols = Biography.db.getAboutColumnNames();
		aboutColsData = Biography.db.getDataFromAboutColumns();

		lv.setAdapter(new AboutListAdapter(getActivity(), aboutCols, aboutColsData));

		
		return v;
		
	}
}
