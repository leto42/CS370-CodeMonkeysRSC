package com.example.cs370_codemonkeysrsc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class GenrePageActivity extends AppCompatActivity {
    // Array that holds all genre IDs from Deezer.
    // the IDs are in the same order as the choices in the spinner.
    private final int[] GenreIDs = {2, 85, 16, 153, 75, 186, 98, 84, 71, 113, 106, 173, 466, 81, 129,
            95, 197, 464, 132, 116, 144, 122, 152, 165, 67, 169, 65};
    private static int chosenGenreID;
    private static String genre_choice;
    private static String app_name;
    RecyclerView myRecyclerView;
    List<Model> myModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_page);
        myRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(GenrePageActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(layoutManager);

        myModelList = new ArrayList<>();
        myModelList.add(new Model("African Music", 2));
        myModelList.add(new Model("Alternative", 85));
        myModelList.add(new Model("Asian Music", 16));
        myModelList.add(new Model("Blues", 153));
        myModelList.add(new Model("Brazilian Music", 75));
        myModelList.add(new Model("Christian", 186));
        myModelList.add(new Model("Classical", 98));
        myModelList.add(new Model("Country", 84));
        myModelList.add(new Model("Cumbia", 71));
        myModelList.add(new Model("Dance", 113));
        myModelList.add(new Model("Electro", 106));
        myModelList.add(new Model("Films/Games", 173));
        myModelList.add(new Model("Folk", 466));
        myModelList.add(new Model("Indian Music", 81));
        myModelList.add(new Model("Jazz", 129));
        myModelList.add(new Model("Kids", 95));
        myModelList.add(new Model("Latin Music", 197));
        myModelList.add(new Model("Metal", 464));
        myModelList.add(new Model("Pop", 132));
        myModelList.add(new Model("Rap/Hip-Hop", 116));
        myModelList.add(new Model("Reggae", 144));
        myModelList.add(new Model("Reggaeton", 122));
        myModelList.add(new Model("Rock", 152));
        myModelList.add(new Model("R & B", 165));
        myModelList.add(new Model("Salsa", 67));
        myModelList.add(new Model("Soul & Funk", 169));
        myModelList.add(new Model("Traditional Mexiano", 65));


        OnGenreItemClickListener genreItemClickListener = new OnGenreItemClickListener() {
            @Override
            public void onGenreItemClick(int selectedGenreId) {
                Intent intent = new Intent(GenrePageActivity.this, YoutubePageActivity.class);
                intent.putExtra("GENRE_ID", selectedGenreId);
                startActivity(intent);
                finish();
            }
        };

        GenreAdapter myAdapter = new GenreAdapter(myModelList, genreItemClickListener);
        myRecyclerView.setAdapter(myAdapter);
    }
}
