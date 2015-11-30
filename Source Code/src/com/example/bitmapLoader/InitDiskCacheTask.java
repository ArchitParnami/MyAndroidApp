package com.example.bitmapLoader;

import java.io.File;

import android.os.AsyncTask;

public class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
	
	BitmapLoader loader;
	public InitDiskCacheTask(BitmapLoader loader) {
		this.loader = loader;
	}
	
	@Override
	protected Void doInBackground(File... params) {
		
		synchronized (loader.mDiskCacheLock) {
			
			File cacheDir = params[0];
			
			try {
				loader.mDiskLruCache = DiskLruCache.openCache(loader.context, cacheDir, loader.DISK_CACHE_SIZE);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			loader.mDiskCacheStarting = false;
			loader.mDiskCacheLock.notifyAll();
		}
		
		return null;
	}
}
