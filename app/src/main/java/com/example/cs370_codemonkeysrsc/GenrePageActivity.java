package com.example.cs370_codemonkeysrsc;

import androidx.appcompat.app.AppCompatActivity;

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

public class GenrePageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RadioGroup choices_group;
    private RadioButton yes_button, no_button, selected_button;
    private Button submit_button;
    private Button home_button;
    private static Boolean allowExplicit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_page);

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
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenrePageActivity.this, YoutubePageActivity.class);
                startActivity(intent);
            }
        });
        // Button to return to MainActivity page.
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenrePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        adapterView.getItemAtPosition(pos);
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
