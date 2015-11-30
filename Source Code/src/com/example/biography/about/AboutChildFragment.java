package com.example.biography.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biography.R;

public class AboutChildFragment extends Fragment{
	
	public static String CHILD_NAME = "name";
	public static String CHILD_CONTENT = "content";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.about_child_fragment_layout, null, true);
		TextView tv_childContent = (TextView)v.findViewById(R.id.about_child_content);
		TextView tv_heading = (TextView)v.findViewById(R.id.tv_about_child);
		
		Bundle bundle = getArguments();
		
		tv_childContent.setText(bundle.getString(CHILD_CONTENT));
		tv_heading.setText(bundle.getString(CHILD_NAME));
		
		return v;
	}

}
