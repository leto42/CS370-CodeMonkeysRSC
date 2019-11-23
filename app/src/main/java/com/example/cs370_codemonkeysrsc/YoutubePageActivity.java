package com.example.cs370_codemonkeysrsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePageActivity extends YouTubeBaseActivity {
    private Button new_button; // designed to return to input page *
    private Button home_button; // designed to return to main page
    private Button like_button; // designed to save song to favorites
    private Button youtube_play_button;
    private YouTubePlayerView youtubeplayerview;
    private YouTubePlayer.OnInitializedListener youtube_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_page);
        home_button = findViewById(R.id.home_button);
        new_button = findViewById(R.id.new_button);
        like_button = findViewById(R.id.like_button);
        youtube_play_button = findViewById(R.id.YouTube_play_button);
        youtubeplayerview = findViewById(R.id.YouTubePlayer_view);

        // set up youtube listener
        youtube_listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // example of playing video
                youTubePlayer.loadVideo("OTHxzgoJM5E");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        // set up youtube play button
        youtube_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeplayerview.initialize(YouTubeAPI.getYouTube_API_KEY(), youtube_listener);

            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // returns user to MainActivity page.
                Intent intent = new Intent(YoutubePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // returns user to GenrePageActivity.
                Intent intent = new Intent(YoutubePageActivity.this, GenrePageActivity.class);
                startActivity(intent);
            }
        });

        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "Toast" notification that song has been added to Favorites.
                Context context = getApplicationContext();
                CharSequence text = "Song saved to Favorites!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });


    }
}
