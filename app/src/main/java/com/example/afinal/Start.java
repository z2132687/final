package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import com.example.afinal.R;
=======
import com.example.test.R;
>>>>>>> d739600073978a258cc53382312b2496742ed253

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    private void startGame() {
<<<<<<< HEAD
        Intent intent = new Intent(this, MainActivity.class);
=======
        Intent intent = new Intent(this, Start.class);
>>>>>>> d739600073978a258cc53382312b2496742ed253
        startActivity(intent);
        finish();
    }
}