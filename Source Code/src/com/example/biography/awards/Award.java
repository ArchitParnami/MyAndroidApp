package com.example.biography.awards;

public class Award {
	
	private String name;
	private String year;
	private String givenBy;
	
	public Award(String name, String year, String givenBy) {
		this.name = name;
		this.year = year;
		this.givenBy = givenBy;
	}
	
	public String getName() {
		return name;
	}
	
	public String getYear(){
		return year;
	}
	
	public String getGivenBy(){
		return givenBy;
	}
}
