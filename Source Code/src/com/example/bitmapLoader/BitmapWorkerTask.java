package com.example.bitmapLoader;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap>{

	private final BitmapLoader loader;
	private final WeakReference<ImageView> imageViewReference;
	public final String url;
	public String imageKey = null;
	public int dimenID;
	Bitmap bitmap;
	
	public BitmapWorkerTask(BitmapLoader loader, ImageView iv, String url) {
		this.loader = loader;
		this.url = url;
		imageViewReference = new WeakReference<ImageView>(iv);
	}
	
	@Override
	protected Bitmap doInBackground(Integer... params) {
		dimenID = params[0];
		
		imageKey = url + "-" + dimenID;
			
		bitmap = loader.getBitmapFromDiskCache(imageKey);
		
		if(bitmap == null) {			
			BufferedInputStream is = getStreamFromUrl(url);
			if(is != null) {
				bitmap = decodeSampledBitmapFromStream(is, loader.scaleToWidth_px[dimenID], loader.scaleToHeight_px[dimenID]);
				if(bitmap != null) {
					loader.addBitmapToDiskCache(imageKey, bitmap);
				}
			}
				
		}
			
		if(bitmap != null){
			loader.addBitmapToMemCache(imageKey, bitmap);
		}
			
		return bitmap;
	}
	
	
	
	protected void onPostExecute(Bitmap result) {
		
		if(isCancelled()) {
			result = null;
		}
		
		if(imageViewReference != null && result !=null) {
			
			final ImageView imageView = imageViewReference.get();
			final BitmapWorkerTask bitmapWorkerTask = BitmapLoader.getBitmapWorkerTask(imageView);
			
			if(this == bitmapWorkerTask && imageView != null) {
				imageView.setImageBitmap(result);
			}
		}
				
	};
	
	
	  BufferedInputStream getStreamFromUrl(String s_url) {
		
		try {
			URL url = new URL(s_url);
			
			if(s_url.endsWith(".jpg") || s_url.endsWith(".png")) {
				
				URLConnection connection = url.openConnection();
				connection.connect();
				InputStream is =  connection.getInputStream();
			     return new BufferedInputStream(is, 8192);
			}
						
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	  
	
	Bitmap decodeSampledBitmapFromStream(BufferedInputStream inputStream, int scaleToWidth, int scaleToHeight) {
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(inputStream, null, options);
		options.inSampleSize = calculateSampleSize(options, scaleToWidth, scaleToHeight);
		options.inJustDecodeBounds = false;
		try{
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		};
		
		inputStream = getStreamFromUrl(url);
		
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
		try{
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		};
		
		return bitmap;
	}
	
	private int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		
		final int height = options.outHeight;
		final int width = options.outWidth;
		
		int inSampleSize = 1;
		
		if(height  > reqHeight || width  > reqWidth) {
			final int heightRatio = Math.round((float)height/(float)reqHeight);
			final int widthRatio = Math.round((float)width/(float)reqWidth);
			
			inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
		}
		
		return inSampleSize;
	}
	
	
}
