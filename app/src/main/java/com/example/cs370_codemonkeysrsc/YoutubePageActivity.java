package com.example.cs370_codemonkeysrsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class YoutubePageActivity extends AppCompatActivity {
    private Button new_button; // designed to return to input page *
    private Button home_button; // designed to return to main page
    private Button like_button; // designed to save song to favorites
    private String returnedSongTitle;
    private int GenreID; //ORIGINAL
    //static int GenreID;

    public int getGenreID() {return GenreID;} // not currently working

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_page);
        home_button = findViewById(R.id.home_button);
        new_button = findViewById(R.id.new_button);
        like_button = findViewById(R.id.like_button);

        // String GenreID = getIntent().getStringExtra("GENRE_ID"); ORIGINAL
        GenreID = getIntent().getIntExtra("GENRE_ID", 0);
        Log.d("YoutubePageActivity","receivedGenreID: " + GenreID); // checking that GenreID successfully received. (yes)

        Deezer deezerSearch = new Deezer();
        // *** need to get GenreID to Deezer class ***

        /*
        Intent intent = new Intent(YoutubePageActivity.this, Deezer.class);
        intent.putExtra("GENRE_ID", GenreID);
        startActivity(intent);
        */

        deezerSearch.setListener(new Deezer.DeezerListener() {
            @Override
            public void onDeezerCallback(String songTitle) {
                returnedSongTitle = songTitle;
                Log.d("YoutubePageActivity", "returnedSongTitle: " + returnedSongTitle);
            }
        });
        Log.d("YoutubePageActivity", "returnedSongTitle: " + returnedSongTitle);
        // Temporary display message to see the genre id being selected.
        Context context = getApplicationContext();
        CharSequence text = returnedSongTitle;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();



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
