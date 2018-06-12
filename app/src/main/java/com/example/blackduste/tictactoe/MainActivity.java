package com.example.blackduste.tictactoe;

import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int movesCounter = 0;

    // 0 == squirtle , 1 == bulbasaur
    int activePlayer = 0;
    boolean isGameActive = true;
    // value 2 means the grid location is untouched
    int[] gridLocationData = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningState = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGridSelected(View view) {
        ImageView pokemon = (ImageView) view;
        int gridLocationVal = Integer.parseInt((pokemon.getTag().toString()));
        if (gridLocationData[gridLocationVal] == 2 && isGameActive) {
            pokemon.setTranslationY(-1000f);
            gridLocationData[gridLocationVal] = activePlayer;
            if (activePlayer == 0) {
                pokemon.setImageResource(R.drawable.squirtle);
                movesCounter++;
                activePlayer = 1;
            } else {
                pokemon.setImageResource(R.drawable.bulbasaur);
                movesCounter++;
                activePlayer = 0;
            }
            pokemon.animate().translationYBy(1000f).setDuration(300);
        }
        for (int[] winningPosition : winningState) {
            if (gridLocationData[winningPosition[0]] == gridLocationData[winningPosition[1]] &&
                    gridLocationData[winningPosition[1]] == gridLocationData[winningPosition[2]] &&
                    gridLocationData[winningPosition[0]] != 2) {

                // Someone has won
                isGameActive = false;
                if (gridLocationData[winningPosition[0]] == 0) {
                    //Squirtle is winner
                    TextView winner = findViewById(R.id.winnerTextView);
                    LinearLayout layout = findViewById(R.id.linearLayout);
                    winner.setText("Squirtle Won!!");
                    layout.setVisibility(View.VISIBLE);
                } else {
                    // Bulbasaur is winenr
                    TextView winner = findViewById(R.id.winnerTextView);
                    winner.setText("bulbasaur Won!!");
                    LinearLayout layout = findViewById(R.id.linearLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            } else {
                if (movesCounter == 9) {
                    // Draw
                    isGameActive = false;
                    TextView winner = findViewById(R.id.winnerTextView);
                    winner.setText("It's a Draw!!");
                    LinearLayout layout = findViewById(R.id.linearLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgainBtn(View view) {
        LinearLayout layout = findViewById(R.id.linearLayout);
        layout.setVisibility(View.INVISIBLE);

        movesCounter = 0;

        activePlayer = 0;
        for (int i = 0; i < gridLocationData.length; i++) {
            gridLocationData[i] = 2;
        }

        isGameActive = true;

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            // to remmove image use this:
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);


        }


    }
}
