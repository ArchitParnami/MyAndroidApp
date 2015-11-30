package com.example.biography;

import java.io.InputStream;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.excelAPI.ReadExcel;

public class DatabaseUpdater extends AsyncTask<InputStream, Integer, String>{
		
	Biography activity;
	
	public DatabaseUpdater(Biography activity) {
		this.activity = activity;
	}
	
	@Override
	protected String doInBackground(InputStream... params) {
		
		InputStream s = params[0];
		ReadExcel excel = new ReadExcel(s);
		
		final int excelVersion = excel.getCurrentVersion();
		final int dbVersion = Biography.db.getSubVersion();
					
		if(excelVersion > dbVersion) {
			
			final int about_Updated = 0;
			final int awards_Updated = 1;
			final int news_Updated = 2;
			final int videos_Updated = 3;
			
			boolean[] sheetsUpdated = new boolean[4];
			
			excel.readUpdatedSheets(sheetsUpdated);
			
			if(sheetsUpdated[about_Updated]) {
				
				int noOfAboutColumns = excel.getAboutColumnCount();
				
				String[] columns = new String[noOfAboutColumns] ;
				String[] data = new String[noOfAboutColumns];
				
				excel.readAboutSheetInto(columns, data);
				Biography.db.insertIntoAboutTable(columns, data);
				

			}
			
			if(sheetsUpdated[awards_Updated]) {
				
				int noOfAwards = excel.getNoOfAwards();
				int columnCount = 3;
				
				String data[][] = new String[noOfAwards][columnCount];
				excel.readAwardsSheetInto(data);
				Biography.db.insertIntoAwardTable(data);
				
			}
			
			if(sheetsUpdated[news_Updated]) {
				
				int columnCount = 8;
				int newsCount = excel.getNoOfNews();
				
				String data[][] = new String[newsCount][columnCount];
				excel.readNewsSheetInto(data);
				Biography.db.insertIntoNewsTable(data);

			}
			
			if(sheetsUpdated[videos_Updated]) {
				int noOfVideos = excel.getNoOfVideos();
				String data[] = new String[noOfVideos];
				excel.readVideosSheetInto(data);
				Biography.db.insertIntoVideosTable(data);
			}
			
		Biography.db.setSubVersion(excelVersion);
			
		
		try {
			s.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	 }
		
		return null;
	}

	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Intent in = new Intent(activity, Tabs.class);
		activity.startActivity(in);
		activity.finish();
			
		
	}
	
}
