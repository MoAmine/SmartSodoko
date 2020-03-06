package com.example.smartsodoku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class JeuActivity extends AppCompatActivity {

    private static final int cc = 1;
    Grille grille;
    Intent intent;
    int x, y;
    int indexX, indexY;
    private Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        final Intent intent = new Intent(this, ChoixActivity.class);

        grille = findViewById(R.id.grille);
        grille.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = (int) event.getX();
                y = (int) event.getY();

                indexX = grille.getXFromMatrix(x);
                indexY = grille.getYFromMatrix(y);

                if (grille.isNotFix(indexX, indexY) && indexX < 9 && indexY < 9) {
                    // startActivity(intent);
                    startActivityForResult(intent, cc);

                } else {
                    Toast.makeText(JeuActivity.this, "CaseNonVide", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        valider = (Button) findViewById(R.id.valider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              switch(grille.getValisation()) {
                  case 0:
                  grille.setValisation(1);
                      break;
                  default:
                      grille.setValisation(0);
                      break;
              }
              grille.postInvalidate();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            int result = data.getIntExtra("result", 0);
            Log.i("TAG", "  heer .... lv----------------" + result);

            grille.set(indexX, indexY, result);
            Log.i("TAG", "  heer .... lv----------------" + result);

        }

    }
}
