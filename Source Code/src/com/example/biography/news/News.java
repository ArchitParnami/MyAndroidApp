package com.example.biography.news;

public class News {
	
	private String url = null;
	private String title = null;
	private String date = null;
	private String time = null;
	private String description = null;
	private String source = null;
	private String author = null;
	private String imageDesc = null;
	
	
	public News(String url, String title, String date, String time) {
		this.title = title;
		this.date = date;
		this.time = time;
		this.url = url;
	}
	
	public News(String url, String title, String date, String time, String desc, String src, String auth, String imgdsc) {
		this(url, title, date, time);
		description = desc;
		source = src;
		author = auth;
		imageDesc = imgdsc;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getSource() {
		return source;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getImageDesc(){
		return imageDesc;
	}
	
	
	
}
