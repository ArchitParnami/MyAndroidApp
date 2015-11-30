package com.example.biography.videos;

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

public class VideoListAdapter extends ArrayAdapter<YouTubeVideo>{

    VideoInfoLoader videoInfoLoader;
	
	YouTubeVideo[] videos;
	Activity context;
	
	public VideoListAdapter(Activity context, YouTubeVideo[] videos) {
		super(context, R.layout.videos_single_list_item, videos);
		this.context = context;
		this.videos = videos;
		videoInfoLoader = new VideoInfoLoader(context);
	}
	
	static class VideoListViewHolder {
		ImageView iv_thumbnail;
		TextView tv_title;
		TextView tv_duration;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final int pos = position;
		View rowView = convertView;
		final VideoListViewHolder holder;
		
		if(rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.videos_single_list_item, null, true);
			holder = new VideoListViewHolder();
			holder.iv_thumbnail = (ImageView)rowView.findViewById(R.id.videoThumbnail);
			holder.tv_title = (TextView)rowView.findViewById(R.id.video_title);
			holder.tv_duration = (TextView)rowView.findViewById(R.id.video_duration);
			rowView.setTag(holder);
			
		}
		
		else {
			holder = (VideoListViewHolder)rowView.getTag();
		}
		
		final String url = videos[pos].getUrl();
		final String urlID = url.substring(url.lastIndexOf('=') + 1);
		final String newUrl = "http://img.youtube.com/vi/" + urlID + "/default.jpg";
		
		Tabs.imageLoader.load(holder.iv_thumbnail, newUrl, 2);
		
		videoInfoLoader.load(videos[pos], holder.tv_title, holder.tv_duration);
		
		rowView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(context, PlayerViewActivity.class);
				in.putExtra(PlayerViewActivity.URL_ID, urlID);
				in.putExtra(PlayerViewActivity.VIDEO_TITLE, videos[pos].getTitle());
				context.startActivity(in);
			}
		});
		
		return rowView;
	}
	
}
