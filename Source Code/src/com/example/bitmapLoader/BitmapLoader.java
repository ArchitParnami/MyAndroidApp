package com.example.bitmapLoader;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class BitmapLoader {

	private Bitmap mPlaceHolderImage;
	private LruCache<String, Bitmap> mMemoryCache;
	
	DiskLruCache mDiskLruCache;
	final Object mDiskCacheLock = new Object();
	boolean mDiskCacheStarting = true;
	long DISK_CACHE_SIZE;
	
	String DISK_CACHE_SUBDIR;
	Context context;
	
	int scaleToWidth_px[];
	int scaleToHeight_px[];
	
	int dimenCount;
		
	public BitmapLoader(Context context, Bitmap placeHolderImage, long disk_cache_size, String disk_cache_subdir, 
							int scaleToWidth_dp[], int scaleToHeight_dp[]) {
		
		this.context = context;
		mPlaceHolderImage = placeHolderImage;
		DISK_CACHE_SIZE = disk_cache_size;
		DISK_CACHE_SUBDIR = disk_cache_subdir;
		
		final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;
		
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			
			protected int sizeOf(String key, Bitmap value) {
				
				return (value.getRowBytes() * value.getHeight()) / 1024;
			};
		};
		
		File cacheDir = getDiskCacheDir(DISK_CACHE_SUBDIR);
		
		InitDiskCacheTask initDiskCacheTask = new InitDiskCacheTask(this);
		initDiskCacheTask.execute(cacheDir);
		
		dimenCount = scaleToWidth_dp.length;
		
		scaleToWidth_px = new int[dimenCount];
		scaleToHeight_px = new int[dimenCount];
		
		
		for(int i=0; i < dimenCount ; i++) {
			scaleToWidth_px[i] = (int)convertDpToPixel(scaleToWidth_dp[i], context);
			scaleToHeight_px[i] = (int)convertDpToPixel(scaleToHeight_dp[i], context);
		}
		
	}
	
	private File getDiskCacheDir(String cacheName) {
	
		final String cachePath = 
				Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? context.getExternalCacheDir().getPath() :
					context.getCacheDir().getPath();
	
		return new File(cachePath + File.separator + cacheName);
	}
	
	private Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
	
	private float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}
	
	void addBitmapToMemCache(String key, Bitmap bitmap) {
			if(getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap);
			}
	}
	
	
	Bitmap getBitmapFromDiskCache(String key) {
		synchronized (mDiskCacheLock) {
		
			while(mDiskCacheStarting) {
				try {
					mDiskCacheLock.wait();
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(mDiskLruCache != null) {
				
				return mDiskLruCache.get(key);
			}
			
			return null;
		}
	}
	
	void addBitmapToDiskCache(String key, Bitmap bitmap) {
		synchronized (mDiskCacheLock) {
		
			if(mDiskLruCache != null && mDiskLruCache.get(key) == null) {
				mDiskLruCache.put(key, bitmap);
			}
		}
	}
	
	public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		if(imageView!=null) {
			
			final Drawable drawable = imageView.getDrawable();
			if(drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		
		return null;
	}
	
	public void load(ImageView iv, String url, int dimenID) {
			
			String imageKey = url + "-" + dimenID;
			
			final Bitmap bitmap = getBitmapFromMemCache(imageKey);
			
			if(bitmap != null) {
				iv.setImageBitmap(bitmap);
			}
			
			else {
				
				if(cancelPotentialWork(imageKey, iv)) {
					final BitmapWorkerTask task = new BitmapWorkerTask(this, iv, url);
					final AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(), mPlaceHolderImage, task);
					iv.setImageDrawable(asyncDrawable);
					task.execute(dimenID);
				}
			
			
			}
		
	}
	
	private boolean cancelPotentialWork(String imageKey, ImageView imageView) {
		
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
		
		if(bitmapWorkerTask != null) {
			
			String bitmapKey = bitmapWorkerTask.imageKey;
			
			if(imageKey != bitmapKey) {
				bitmapWorkerTask.cancel(true);
			}
			
			else {
				return true;
			}
		}
		
		return true;
	}
	
	
}
