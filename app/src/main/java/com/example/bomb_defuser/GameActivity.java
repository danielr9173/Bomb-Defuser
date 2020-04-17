package com.example.bomb_defuser;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class GameActivity extends AppCompatActivity {

    private static final long START_TIMER = 300250; //this is milliseconds for 5 mins.

    private TextView txtTimer,txtWires,txtMoves;
    private CountDownTimer countDownTimer;
    private long timeLeft;
    private boolean restart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        txtTimer = findViewById(R.id.txtTimer);
        txtWires = findViewById(R.id.txtWiresLeft);
        txtMoves = findViewById(R.id.txtActionTaken);

        Log.e("GameActivity", "onCreate"); //For testing to see if its working
        GameEngine.getInstance().createGrid(this);

        timeLeft = START_TIMER;
    }

    @Override
    protected void onStart() {
        super.onStart();

        startCountDown();
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(GameEngine.getInstance().getStartTimer()) {
                    timeLeft = millisUntilFinished;
                    updateTimer();
                    txtWires.setText(getString(R.string.Wires_Left,         //update both Wires, and
                            GameEngine.getInstance().getWireCounter()));    //Moves text views
                    txtMoves.setText(getString(R.string.Score,
                            GameEngine.getInstance().getScoreCounter()));
                }
                else
                    countDownTimer.cancel();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                updateTimer();
            }
        }.start();
    }

    private void updateTimer() {
        int mins = (int) (timeLeft / 1000) / 60;
        int sec = (int) (timeLeft / 1000) % 60;
        txtTimer.setText(getString(R.string.Timer,mins,sec));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null)      //When the activity is finished it needs to be canceled.
            countDownTimer.cancel();
    }
}
