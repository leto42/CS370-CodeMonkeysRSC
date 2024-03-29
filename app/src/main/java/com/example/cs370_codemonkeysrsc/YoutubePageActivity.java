package com.example.cs370_codemonkeysrsc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs370_codemonkeysrsc.model.YouTubeModel;
import com.example.cs370_codemonkeysrsc.network.VideoSearchAsyncTask;
import com.example.cs370_codemonkeysrsc.network.YouTubeAPI;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class YoutubePageActivity extends YouTubeBaseActivity {
    private Button new_button; // designed to return to input page *
    private Button home_button; // designed to return to main page
    private Button youtube_play_button;
    private YouTubePlayerView youtubeplayerview;
    private YouTubePlayer.OnInitializedListener youtube_listener;
    private String video_ID;
    private String returnedSongTitle;
    private int GenreID; //ORIGINAL
    private String searchTerm;


    public int getGenreID() {return GenreID;} // not currently working

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_page);
        home_button = findViewById(R.id.home_button);
        new_button = findViewById(R.id.new_button);
        youtube_play_button = findViewById(R.id.YouTube_play_button);
        youtubeplayerview = findViewById(R.id.YouTubePlayer_view);

        // String GenreID = getIntent().getStringExtra("GENRE_ID"); ORIGINAL
        GenreID = getIntent().getIntExtra("GENRE_ID", 0);
        Log.d("YoutubePageActivity","receivedGenreID: " + GenreID); // checking that GenreID successfully received. (yes)

        Deezer deezerSearch = new Deezer();

        deezerSearch.setListener(new Deezer.DeezerListener() {
            @Override
            public void onDeezerCallback(String songTitle) {
                returnedSongTitle = songTitle;
                Log.d("YoutubePageActivity", "returnedSongTitle: " + returnedSongTitle);
                // Temporary display message to see the genre id being selected.
                Context context = getApplicationContext();
                CharSequence text = returnedSongTitle;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                searchTerm = returnedSongTitle + " song lyrics";
            }
        });

        deezerSearch.execute(GenreID);
        Log.d("YoutubePageActivity", "returnedSongTitle: " + returnedSongTitle);

        // set up youtube play button
        youtube_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //create a new task
            VideoSearchAsyncTask video_task = new VideoSearchAsyncTask();


            // add a VideoListener to the task
            video_task.setListener(new VideoSearchAsyncTask.VideoListener() {
                @Override
                public void onVideoSearchCallback(List<YouTubeModel> YouTubeModels) {
                    // show the first response on the screen
                    YouTubeModel first = YouTubeModels.get(0);

                    // set video_ID
                    video_ID = first.getVideoID();
                    youtubeplayerview.initialize(YouTubeAPI.getYouTube_API_KEY(), youtube_listener);
                }
            });
                //Start the task
                video_task.execute(searchTerm);
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // returns user to MainActivity page.
                Intent intent = new Intent(YoutubePageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // returns user to GenrePageActivity.
                Intent intent = new Intent(YoutubePageActivity.this, GenrePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // set up youtube listener
        youtube_listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setFullscreen(false);
                youTubePlayer.loadVideo(video_ID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
    }
}
