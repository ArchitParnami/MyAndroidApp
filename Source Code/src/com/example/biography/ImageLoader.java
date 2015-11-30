package com.example.biography;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.bitmapLoader.BitmapLoader;

public class ImageLoader {
	
	private BitmapLoader bitmapLoader;
	private Bitmap placeHolderImage;
	private long cacheSize;
	private String cacheDir;
	private int imageViewWidth_dp[] = {100, 300, 120};
	private int imageViewHeight_dp[] = {80, 200, 90};
	
	
	public ImageLoader(Activity context) {
		
		placeHolderImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.placeholder);
		cacheSize = 1024 * 1024 * 10; // 10 MB
		cacheDir = "NewsIcons";
		
		bitmapLoader = new BitmapLoader(context, placeHolderImage, cacheSize, cacheDir, 
										imageViewWidth_dp, imageViewHeight_dp);
	}
	
	public void load(ImageView iv, String url, int dimenID) {
		bitmapLoader.load(iv, url, dimenID);
	}

}
