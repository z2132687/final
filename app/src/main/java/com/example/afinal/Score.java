package com.example.afinal;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Score extends AppCompatActivity {

    private TextView scoreTextVirw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreTextVirw = findViewById(R.id.scoreTextView);

        // Get the score from the intent
        int score = getIntent().getIntExtra("SCORE", 0);

        // Display the score
        scoreTextVirw.setText("Score: " + score);
    }
}