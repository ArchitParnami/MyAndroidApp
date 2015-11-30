package com.example.biography.videos;

public class YouTubeVideo {
	
	private int id;
	private String url;
	private String title;
	private String duration;
	
	public YouTubeVideo(int id, String url, String title, String duration) {
		this.id = id;
		this.url = url;
		this.title = title;
		this.duration =duration;
	}
	
	public void setTitle(String title){
		this.title = title; 
	}
	
	public void setDuration(String dur) {
		duration = dur;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public int getID() {
		return id;
	}
	
}
