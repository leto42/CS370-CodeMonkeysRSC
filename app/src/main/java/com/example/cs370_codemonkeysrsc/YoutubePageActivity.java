package com.example.cs370_codemonkeysrsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class YoutubePageActivity extends AppCompatActivity {
    private Button new_button; // designed to return to input page *
    private Button home_button; // designed to return to main page
    private Button like_button; // designed to save song to favorites

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_page);
        home_button = findViewById(R.id.home_button);
        new_button = findViewById(R.id.new_button);

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
