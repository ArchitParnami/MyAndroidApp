package com.example.biography.videos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.biography.Biography;

public class VideoInfoLoaderTask extends AsyncTask<Void, Void, String[]>{

	private final WeakReference<TextView> titleTextViewReference;
	private final WeakReference<TextView> durationTextViewReference;
	TextView tv_title;
	TextView tv_duration;
	YouTubeVideo video;
	Context context;

	
	public VideoInfoLoaderTask(YouTubeVideo v, TextView title, TextView duration, Context context) {
		
		video = v;
		tv_title  = title;
		tv_duration = duration;
		this.context = context;
		
		titleTextViewReference = new WeakReference<TextView>(tv_title);
		durationTextViewReference = new WeakReference<TextView>(tv_duration);
	}
	
	@Override
	protected String[] doInBackground(Void... params) {
		
		return  Youtube_Fetchinfo(context, video.getUrl(),0);
	}
	
	@Override
	protected void onPostExecute(String[] result) {
		
		if(isCancelled()) {
			result = null;
		}
		
		if(result != null) {
			
			if(titleTextViewReference != null) {
				TextView title_tv = titleTextViewReference.get();
				VideoInfoLoaderTask videoInfoLoaderTask = (VideoInfoLoaderTask)title_tv.getTag();
				if(videoInfoLoaderTask == this && title_tv != null) {
					title_tv.setText(result[0]);
				}
			}
			
			if(durationTextViewReference != null) {
				TextView duration_tv = durationTextViewReference.get();
				VideoInfoLoaderTask videoInfoLoaderTask = (VideoInfoLoaderTask)duration_tv.getTag();
				if(videoInfoLoaderTask == this && duration_tv != null) {
					duration_tv.setText(result[1]);
				}
			}
		
			video.setTitle(result[0]);
			video.setDuration(result[1]);
			Biography.db.insertVideoInfo(video.getID(), result[0], result[1]);
		}
	
		
	}
	
	
	public String[] Youtube_Fetchinfo(Context context,String url,int timeout){
	    String infourl="";
	    if (!(url.toLowerCase(Locale.UK).contains("youtube")))
	        return null;
	 
	    //get video ID
	    Pattern p = Pattern.compile(".+v=([^&\"]*)");
	    Matcher m = p.matcher(url);
	    
	    if (m.find()) {
	        infourl = "http://gdata.youtube.com/feeds/api/videos/"+m.group(1);
	    }
	    else
	        return null;
	    
	 
	    int responseCode = 0;
	    int duration=0;
	    
	    String data[] = new String[2];
	    String title="";
	    
	 
	    try {
	        //Get video information
	        String defaultUA = "Mozilla/5.0 (Linux; U; Android 2.1; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3";
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(infourl);
	        HttpResponse httpResponse = null;
	 
	        httpClient.getParams().setParameter("http.connection.timeout",Integer.valueOf(timeout));
	        HttpParams params1 = httpClient.getParams();
	        HttpConnectionParams.setConnectionTimeout(params1, timeout);
	        HttpConnectionParams.setSoTimeout(params1, timeout);
	        params1.setParameter(HttpProtocolParams.USER_AGENT, defaultUA);
	        httpResponse = httpClient.execute(httpGet);
	        responseCode = httpResponse.getStatusLine().getStatusCode();
	 
	        if (responseCode == HttpStatus.SC_OK) {
	            InputStream istream = httpResponse.getEntity().getContent();
	            InputStreamReader reader = new InputStreamReader(istream);
	            BufferedReader objBuf = new BufferedReader(reader);
	            StringBuilder builder = new StringBuilder();
	            String sLine;
	 
	            while((sLine = objBuf.readLine()) != null){
	                builder.append(sLine);}
	            String html = builder.toString();
	            istream.close();
	 
	            //find duration info
	            Pattern dp = Pattern.compile("seconds='(\\d+?)'");
	            Matcher dm = dp.matcher(html);
	            if (dm.find()) {
	                try {
	                        duration = Integer.parseInt(dm.group(1));
	                }catch(Exception e){duration=0;}
	            }
	 
	            //find video title
	            Pattern tp = Pattern.compile("<title.*?>(.*?)</");
	            Matcher tm = tp.matcher(html);
	            if (tm.find()) {
	                title = tm.group(1);
	                if (title.length()>0){
	               
	                	int hrs = 0;
	                	int mins = 0;
	                	int secs = 0;
	                	
	                	if (duration >= 60) {
	                		mins = duration / 60;
	                		secs = duration % 60;
	                		
	                		if(mins >= 60) {
	                			hrs = mins/60;
	                			mins = mins % 60;
	                		}
	                	}
	                    
	                	String time="";
	                	
	                    if(hrs!=0) {
	                    	time += String.valueOf(hrs) + ":";
	                    }
	                    
	                    time += String.valueOf(mins) + ":" + String.valueOf(secs);;
	                   
	                	title = title.replace("&amp;","&");
	                    title = title.replace("&gt;","<");
	                    title = title.replace("&lt;",">");
	                    title = title.replace("&quot;","\"");
	                   
	                    data[0] = title;
	                    data[1] = time;
	                    
	                    return data;
	                }
	            }
	        }
	        else if (responseCode == 403){ //forbidden
	            data[0] = "forbidden";
	            data[1] = null;
	            return data;
	        }
	    } 
	    
	    catch (ClientProtocolException e) {
	    } 
	    
	    catch (IOException e) {
	    }
	    	
	  return null;
	
	}
	
}
