package com.example.biography.news;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biography.R;
import com.example.biography.Tabs;

public class NewsListAdapter extends ArrayAdapter<News>{

	Activity context;
	News[] news;
		
	public NewsListAdapter(Activity context, News[] news) {
		super(context, R.layout.news_single_list_item, news);
		this.context = context;
		this.news = news;
	}
	
	public static class NewsListViewHolder {
		ImageView iv_newsImage;
		TextView tv_newsTitle;
		TextView tv_newsDate;
		TextView tv_newsTime;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final int pos = position;
		View rowView = convertView;
		final NewsListViewHolder holder;
		
		if(rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.news_single_list_item, null, true);
			holder = new NewsListViewHolder();
			
			holder.iv_newsImage = (ImageView)rowView.findViewById(R.id.newsImage);
			holder.tv_newsTitle = (TextView)rowView.findViewById(R.id.news_title);
			holder.tv_newsDate = (TextView)rowView.findViewById(R.id.tv_news_date);
			holder.tv_newsTime = (TextView)rowView.findViewById(R.id.tv_news_time);
			rowView.setTag(holder);
		}
		
		else {
			holder = (NewsListViewHolder) rowView.getTag();
		}
		
		holder.tv_newsTitle.setText(news[pos].getTitle());
		holder.tv_newsDate.setText(news[pos].getDate());
		holder.tv_newsTime.setText(news[pos].getTime());
		
		if(!news[pos].getUrl().equals("NULL")){
			
			Tabs.imageLoader.load(holder.iv_newsImage, news[pos].getUrl(), 0);
		}
		else {
			holder.iv_newsImage.setImageBitmap(null);
		}
		
		
		
		rowView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent in = new Intent(context, NewsPagerActivity.class);
				in.putExtra(NewsPagerActivity.ITEM_COUNT, news.length);
				NewsPagerActivity.currentItem = pos;
				context.startActivity(in);
			}
		});
		
		return rowView;
		
	}
}
