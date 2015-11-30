package com.example.biography;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;

import com.example.database.DatabaseManager;

public class Biography extends Activity {

	public static DatabaseManager db;
	InputStream fileStream;
	String fileName="AppDataOld.xls";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biography);
		
		db = new DatabaseManager(this);
		databaseInitializer();
		
		
	}
	
	private void databaseInitializer() {
		
		try {
			fileStream = getResources().getAssets().open(fileName);
			DatabaseUpdater updater = new DatabaseUpdater(this);
			updater.execute(fileStream);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
}
