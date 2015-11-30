package com.example.biography.videos;

import android.app.Activity;
import android.widget.TextView;


public class VideoInfoLoader {

	Activity context;
	
	public VideoInfoLoader(Activity context) {
		this.context = context;
	}
	
	public void load(YouTubeVideo video, TextView tv_title, TextView tv_duration) {

		if(!video.getTitle().equals("")) {
			tv_title.setText(video.getTitle());
			tv_duration.setText(video.getDuration());
		}
		
		else {
			
			if(cancelPotentialWork(video, tv_title)) {
				VideoInfoLoaderTask task = new VideoInfoLoaderTask(video, tv_title, tv_duration, context);
				tv_title.setTag(task);
				tv_duration.setTag(task);
				task.execute();
			}
		}
		
	}
	
	public boolean cancelPotentialWork(YouTubeVideo video, TextView tv_title) {
		VideoInfoLoaderTask videoInfoLoaderTask = (VideoInfoLoaderTask)tv_title.getTag();
		if(videoInfoLoaderTask != null) {
			
			YouTubeVideo videoLoading = videoInfoLoaderTask.video;
			if(video!= videoLoading) {
				videoInfoLoaderTask.cancel(true);
				return true;
			}
			
			else {
				return false;
			}
		}
		
		return true;
	}
}
