package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.biography.awards.Award;
import com.example.biography.news.News;
import com.example.biography.videos.YouTubeVideo;

public class DatabaseManager extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "Biography_DB";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_ABOUT = "about";
	
	private static final String TABLE_AWARDS = "awards";
	private static final String KEY_AWARD_NAME = "award";
	private static final String KEY_AWARD_YEAR = "year";
	private static final String KEY_AWARD_GIVEN_BY = "given_by";
	
	private static final String TABLE_NEWS = "news";
	private static final String KEY_NEWS_ID = "id";
	private static final String KEY_NEWS_IMAGE_URL = "image_url";
	private static final String KEY_NEWS_TITLE = "title";
	private static final String KEY_NEWS_DATE = "date";
	private static final String KEY_NEWS_TIME = "time";
	private static final String KEY_NEWS_DESC = "description";
	private static final String KEY_NEWS_SOURCE = "source";
	private static final String KEY_NEWS_AUTHOR = "author";
	private static final String KEY_NEWS_IMAGE_DESC = "images_desc";
	
	private static final String TABLE_VIDEOS = "videos";
	private static final String KEY_VIDEO_ID = "id";
	private static final String KEY_VIDEO_LINK = "link";
	private static final String KEY_VIDEO_TITLE = "title";
	private static final String KEY_VIDEO_DURATION = "duration";

	private static final String TABLE_SUBVERSION = "subversion";
	private static final String KEY_VERSION = "version";
	
	private String[] TABLE_ABOUT_KEYS;
	
	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		
		String CREATE_AWARDS_TABLE = "CREATE TABLE " + TABLE_AWARDS + " (" + KEY_AWARD_NAME + " TEXT, " +
																			 KEY_AWARD_YEAR + " TEXT, " +
																			 KEY_AWARD_GIVEN_BY + " TEXT" + ")";
		
		String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_NEWS + " (" + KEY_NEWS_ID + " INTEGER," +
																		 KEY_NEWS_IMAGE_URL + " TEXT, " +
																		 KEY_NEWS_TITLE + " TEXT, " +
																		 KEY_NEWS_DATE + " TEXT, " +
																		 KEY_NEWS_TIME + " TEXT, " + 
																		 KEY_NEWS_DESC + " TEXT," + 
																		 KEY_NEWS_SOURCE + " TEXT," + 
																		 KEY_NEWS_AUTHOR + " TEXT," +
																		 KEY_NEWS_IMAGE_DESC + " TEXT" +
																    ")";
																		 
		String CREATE_VIDEOS_TABLE = "CREATE TABLE " + TABLE_VIDEOS + " (" + KEY_VIDEO_ID + " INTEGER," +
																			 KEY_VIDEO_LINK + " TEXT," + 
																			 KEY_VIDEO_TITLE + " TEXT," + 
																			 KEY_VIDEO_DURATION + " TEXT" +
																	    ")";
		
		String CREATE_SUBVERSION_TABLE = "CREATE TABLE " + TABLE_SUBVERSION + " (" + KEY_VERSION + " TEXT" + ")";
		
		db.execSQL(CREATE_AWARDS_TABLE);
		db.execSQL(CREATE_NEWS_TABLE);
		db.execSQL(CREATE_VIDEOS_TABLE);
		db.execSQL(CREATE_SUBVERSION_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	private void createAboutTable() {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		String DropIfExists = "DROP TABLE IF EXISTS " + TABLE_ABOUT;
		db.execSQL(DropIfExists);
		
		String aboutKeys="";
		int i=0;
		
		for(; i < TABLE_ABOUT_KEYS.length - 1; i++) {
			aboutKeys += TABLE_ABOUT_KEYS[i] + " TEXT, "; 
		}
		
		aboutKeys += TABLE_ABOUT_KEYS[i] + " TEXT";
		
		String CREATE_ABOUT_TABLE = "CREATE TABLE "  + TABLE_ABOUT + " (" + aboutKeys + ")";
		
		db.execSQL(CREATE_ABOUT_TABLE);
		
		db.close();
	
	}
	

	public void insertIntoAboutTable(String columns[], String data[]) {
			
			TABLE_ABOUT_KEYS = columns;
			createAboutTable();
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			for(int i=0; i < TABLE_ABOUT_KEYS.length; i++) {
				values.put(TABLE_ABOUT_KEYS[i], data[i]);
			}
			
			db.insertWithOnConflict(TABLE_ABOUT, null, values, SQLiteDatabase.CONFLICT_IGNORE);
						
			db.close();
				
	}
	
	public void insertIntoAwardTable(String data[][]) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TABLE_AWARDS, null, null);
		
		for(int i=0 ; i < data.length; i++) {
			
			ContentValues values = new ContentValues();
			
			values.put(KEY_AWARD_NAME, data[i][0]);
			values.put(KEY_AWARD_YEAR, data[i][1]);
			values.put(KEY_AWARD_GIVEN_BY, data[i][2]);

			db.insertWithOnConflict(TABLE_AWARDS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		}
		
		db.close();
	}
	
	public void insertIntoNewsTable(String data[][]) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TABLE_NEWS, null, null);
		
		for(int i=0 ; i < data.length; i++) {
			
			ContentValues values = new ContentValues();
			values.put(KEY_NEWS_ID, i);
			values.put(KEY_NEWS_IMAGE_URL, data[i][0]);
			values.put(KEY_NEWS_TITLE, data[i][1]);
			values.put(KEY_NEWS_DATE, data[i][2]);
			values.put(KEY_NEWS_TIME, data[i][3]);
			values.put(KEY_NEWS_DESC, data[i][4]);
			values.put(KEY_NEWS_SOURCE, data[i][5]);
			values.put(KEY_NEWS_AUTHOR, data[i][6]);
			values.put(KEY_NEWS_IMAGE_DESC, data[i][7]);
			
			
			db.insertWithOnConflict(TABLE_NEWS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		}
		
		db.close();
	}
	
	public void insertIntoVideosTable(String data[]) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_VIDEOS, null, null);
	
		for(int i = 0; i < data.length; i++) {
			ContentValues values = new ContentValues();
			values.put(KEY_VIDEO_ID, i);
			values.put(KEY_VIDEO_LINK, data[i]);
			values.put(KEY_VIDEO_TITLE,"");
			values.put(KEY_VIDEO_DURATION,"");
			db.insertWithOnConflict(TABLE_VIDEOS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		}
	
		db.close();
	}
	
	public String[] getAboutColumnNames() {
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		String QUERY = "SELECT * FROM " + TABLE_ABOUT;
		cursor = db.rawQuery(QUERY, null);
		
		int noOfCols = cursor.getColumnCount();
		
		String[] columns = new String[noOfCols];
		
		for(int i=0; i < noOfCols; i++) {
			columns[i] = cursor.getColumnName(i).replace("_", " ");
		}
		
		cursor.close();
		db.close();
		
		return columns;
	}
	
	public String[] getDataFromAboutColumns(){
		
		String[] data = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String SELECT_QUERY = "SELECT * FROM " + TABLE_ABOUT;
		Cursor cursor;
		cursor = db.rawQuery(SELECT_QUERY, null);
		if(cursor.moveToFirst()) {
			int count = cursor.getColumnCount();
			data = new String[count];
			for(int i=0; i < count; i++) {
				data[i] = cursor.getString(i);
			}
		}
		
		cursor.close();
		db.close();
		return data;
		
	}
	
	public Award[] getAllAwards() {
		
		Award awards[] = null;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		String SELECT_QUERY = "SELECT * FROM " + TABLE_AWARDS;
		
		cursor = db.rawQuery(SELECT_QUERY, null);
		
		if(cursor.moveToFirst()) {
			final int rowCount =  cursor.getCount();
			awards = new Award[rowCount];
			int i=0;
			
			do {
				
				awards[i] = new Award(cursor.getString(0), cursor.getString(1), cursor.getString(2));
				++i;
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		return awards;
		
		
	
	}
	
	public int getSubVersion(){
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor;
		String QUERY = "SELECT * FROM " + TABLE_SUBVERSION;
		
		cursor = db.rawQuery(QUERY, null);
		
		int val=0;
		
		if(cursor.moveToFirst()) {
			val =  Integer.parseInt(cursor.getString(0));
		}
		
		cursor.close();
		db.close();
		
		return val;
		
	}
	
	public void setSubVersion(int v) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_VERSION, String.valueOf(v));
		
		db.delete(TABLE_SUBVERSION, null, null);
		db.insertWithOnConflict(TABLE_SUBVERSION, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		
		db.close();
	}
	
	public News[] getAllNews() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		String QUERY = "SELECT "+ 
					 	KEY_NEWS_ID + "," + KEY_NEWS_IMAGE_URL + "," + KEY_NEWS_TITLE + "," + KEY_NEWS_DATE + "," + KEY_NEWS_TIME +
					 	" FROM " + TABLE_NEWS;
		
		cursor = db.rawQuery(QUERY, null);
		News[] news = null;
		if(cursor.moveToFirst()) {
			int count = cursor.getCount();
			news = new News[count];
			int i=0;
			do {
				news[i] = new News(cursor.getString(1), cursor.getString(2), cursor.getString(3),
								   cursor.getString(4));
				++i;
			}while(cursor.moveToNext());
		}
	
		cursor.close();
		db.close();
		return news;
	}
	
	public News getNews(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		String QUERY = "SELECT * FROM " + TABLE_NEWS + " WHERE " + KEY_NEWS_ID + "=" + id;
		News news = null;
		cursor = db.rawQuery(QUERY, null);
		if(cursor.moveToFirst()) {
			news = new News(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
					cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
		}
		
		cursor.close();
		db.close();
		return news;
	}
	
	public YouTubeVideo[] getAllVideos() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		String QUERY = "SELECT * FROM " + TABLE_VIDEOS;
		YouTubeVideo videos[] = null;
		cursor = db.rawQuery(QUERY, null);
		if(cursor.moveToFirst()) {
			int count = cursor.getCount();
			videos = new YouTubeVideo[count];
			int i=0;
			do {
				
				videos[i] = new YouTubeVideo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
				++i;
			}while(cursor.moveToNext());
			
		}
		
		cursor.close();
		db.close();
		return videos;
	}
	
	public void insertVideoInfo(int videoID, String title, String duration) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_VIDEO_TITLE, title);
		values.put(KEY_VIDEO_DURATION, duration);
		
		db.updateWithOnConflict(TABLE_VIDEOS, values, KEY_VIDEO_ID + "=?", new String[]{String.valueOf(videoID)}, SQLiteDatabase.CONFLICT_IGNORE);
		db.close();
	}
	
	
}
