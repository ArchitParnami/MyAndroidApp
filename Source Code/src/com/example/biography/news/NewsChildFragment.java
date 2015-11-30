package com.example.biography.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biography.Biography;
import com.example.biography.R;
import com.example.biography.Tabs;

public class NewsChildFragment extends Fragment{

	public static String NEWS_ID = "id";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v= inflater.inflate(R.layout.news_child_fragment_layout, null, true);
		
		Bundle bundle = getArguments();
		int id = bundle.getInt(NEWS_ID);
		
		TextView tv_source = (TextView)v.findViewById(R.id.tv_news_source);
		TextView tv_datetime = (TextView)v.findViewById(R.id.tv_news_datetime);
		TextView tv_title = (TextView)v.findViewById(R.id.tv_news_title);
		TextView tv_author = (TextView)v.findViewById(R.id.news_author);
		
		ImageView iv_newsImage = (ImageView)v.findViewById(R.id.news_image);
		TextView tv_imageDesc = (TextView)v.findViewById(R.id.tv_image_desc);
		
		TextView tv_news = (TextView)v.findViewById(R.id.tv_description);
		
		News news = Biography.db.getNews(id);
		
		if(news != null) {
			
			tv_source.setText(news.getSource());
			tv_datetime.setText(news.getDate() + " " + news.getTime());
			tv_title.setText(news.getTitle());
			tv_author.setText(news.getAuthor());
			
			if(!news.getUrl().equals("NULL")) {
			
				Tabs.imageLoader.load(iv_newsImage, news.getUrl(), 1);
				tv_imageDesc.setText(news.getImageDesc());
			}
			
			else {
				iv_newsImage.setVisibility(View.GONE);
				tv_imageDesc.setVisibility(View.GONE);
			}
		
			
			tv_news.setText(news.getDescription());
			
		}
		
		
		return v;
	}
}
