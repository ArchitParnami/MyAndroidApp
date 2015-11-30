package com.example.biography.awards;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.biography.R;

public class AwardListAdapter extends ArrayAdapter<Award>{
	
	Activity context;
	Award[] awards;
	
	public AwardListAdapter(Activity context, Award[] awards) {
		super(context, R.layout.awards_single_list_item, awards);
		this.context = context;
		this.awards = awards;
	}
	
	static class AwardListViewHolder{
		TextView tv_name;
		TextView tv_year;
		TextView tv_givenBy;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		View rowView = convertView;
		final AwardListViewHolder holder;
		
		if(rowView == null) {
			
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.awards_single_list_item, null, true);
			holder = new AwardListViewHolder();
			holder.tv_name = (TextView)rowView.findViewById(R.id.tv_award);
			holder.tv_year = (TextView)rowView.findViewById(R.id.tv_year);
			holder.tv_givenBy = (TextView)rowView.findViewById(R.id.tv_givenBy);
			rowView.setTag(holder);
		}
		else {
			holder = (AwardListViewHolder)rowView.getTag();
		}
		
		holder.tv_name.setText(awards[pos].getName());
		holder.tv_year.setText(awards[pos].getYear());
		holder.tv_givenBy.setText(awards[pos].getGivenBy());
		
		return rowView;
	}

}
