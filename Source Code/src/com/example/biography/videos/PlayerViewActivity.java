/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.biography.videos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.biography.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * A simple YouTube Android API demo application which shows how to create a simple application that
 * displays a YouTube Video in a {@link YouTubePlayerView}.
 * <p>
 * Note, to use a {@link YouTubePlayerView}, your activity must extend {@link YouTubeBaseActivity}.
 */
public class PlayerViewActivity extends YouTubeFailureRecoveryActivity {

  public static String URL_ID = "id";
  public static String VIDEO_TITLE = "title";
  String urlID = null;
  String tittle = null;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.video_playerview);

    Intent in = getIntent();
    urlID = in.getStringExtra(URL_ID);
    tittle = in.getStringExtra(VIDEO_TITLE);
    
    TextView tv_title = (TextView)findViewById(R.id.child_video_title);
    tv_title.setText(tittle);
    
    YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
    youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
  }

  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
      boolean wasRestored) {
    if (!wasRestored) {
      player.cueVideo(urlID);
    }
  }

  @Override
  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
    return (YouTubePlayerView) findViewById(R.id.youtube_view);
  }

}
