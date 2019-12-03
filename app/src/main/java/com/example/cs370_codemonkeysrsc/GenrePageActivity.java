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

public class GenrePageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Array that holds all genre IDs from Deezer.
    // the IDs are in the same order as the choices in the spinner.
    private final int[] GenreIDs = {2, 85, 16, 153, 75, 186, 98, 84, 71, 113, 106, 173, 466, 81, 129,
                                    95, 197, 464, 132, 116, 144, 122, 152, 165, 67, 169, 65};
    private static int chosenGenreID;
    private RadioGroup choices_group;
    private RadioButton yes_button, no_button, selected_button;
    private Button submit_button;
    private Button home_button;
    private static Boolean allowExplicit;
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


        yes_button = findViewById(R.id.yes_radio_button);
        no_button = findViewById(R.id.no_radio_button);
        choices_group = findViewById(R.id.choices_radio_group);
        submit_button = findViewById(R.id.submit_button);
        home_button = findViewById(R.id.home_button);

        Spinner spinner = findViewById(R.id.genre_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genre_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Set up button to go to Youtube page. [SUBMIT button]


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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        adapterView.getItemAtPosition(pos);
        chosenGenreID = GenreIDs[pos];

        // Temporary display message to see the genre id being selected.
        Context context = getApplicationContext();
        CharSequence text = String.format("Genre ID: %d", GenreIDs[pos]);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        // end of genre toast

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onRadioButtonClicked(View view) {
        int selected_num = choices_group.getCheckedRadioButtonId();
        selected_button = findViewById(selected_num);

        // See which choice was selected.
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.yes_radio_button:
                if (checked)
                    // Allow explicit lyrics.
                    allowExplicit = true;
                    break;
            case R.id.no_radio_button:
                if (checked)
                    // No explicit lyrics.
                    allowExplicit = false;
                    break;
        }
    }
}
