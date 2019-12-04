package com.example.cs370_codemonkeysrsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class GenrePageActivity extends AppCompatActivity {

    // Array that holds all genre IDs from Deezer.
    // the IDs are in the same order as the choices in the spinner.
    private final int[] GenreIDs = {2, 85, 16, 153, 75, 186, 98, 84, 71, 113, 106, 173, 466, 81, 129,
            95, 197, 464, 132, 116, 144, 122, 152, 165, 67, 169, 65};
    private static int chosenGenreID;
    private Button home_button;
    private static String genre_choice;
    private static String app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_page);
        home_button = findViewById(R.id.home_button);

        // Button to return to MainActivity page.
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenrePageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
