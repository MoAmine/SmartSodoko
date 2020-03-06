package com.example.smartsodoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button apropos;
    private Button jouer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jouer = (Button) findViewById(R.id.jouer);
        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openJeuActivity();
            }
        });

        apropos = (Button) findViewById(R.id.apropos);
        apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutActivity();
            }
        });
    }

    private void openAboutActivity() {
        Intent intentAbout = new Intent(this,AboutActivity.class);
        startActivity(intentAbout);
    }

    private void openJeuActivity() {
        Intent intentJeu = new Intent(this,JeuActivity.class);
        startActivity(intentJeu);
    }

}
