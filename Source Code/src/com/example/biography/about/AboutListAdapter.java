package com.example.biography.about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.biography.R;

public class AboutListAdapter extends ArrayAdapter<String>{
	
	Activity context;
	String[] data;
	String[] aboutColsData;
	
	public AboutListAdapter(Activity context, String[] data, String[] aboutColsData) {
		super(context, R.layout.about_single_list_item, data);
		this.context = context;
		this.data = data;
		this.aboutColsData = aboutColsData;
	}
	
	static class AboutListViewHolder {
		TextView tv_item;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final int pos = position;
		View rowView = convertView;
		final AboutListViewHolder holder;
		
		if(rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.about_single_list_item, null, true);
			holder = new AboutListViewHolder();
			holder.tv_item = (TextView)rowView.findViewById(R.id.about_single_item);
			rowView.setTag(holder);
		}
		
		else {
			holder = (AboutListViewHolder)rowView.getTag();
		}
		
		holder.tv_item.setText(data[pos]);
		
		holder.tv_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent in = new Intent(context, AboutPagerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putStringArray(AboutPagerActivity.COLUMN_NAMES, data);
				bundle.putStringArray(AboutPagerActivity.COLUMN_DATA, aboutColsData);
				in.putExtras(bundle);
				AboutPagerActivity.index = pos;
				context.startActivity(in);
			}
		});
		
		return rowView;
	}
	
}
