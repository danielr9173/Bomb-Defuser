package com.example.bomb_defuser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class GameActivity extends AppCompatActivity {

    private static final long START_TIMER = 300250; //this is milliseconds for 5 mins.

    private TextView txtTimer,txtWires,txtMoves,txtPincode1,txtPincode2,txtPincode3,txtPincode4;
    private CountDownTimer countDownTimer;
    private GridView grid;
    private LinearLayout pincode;
    private long timeLeft;
    private int buttonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        txtTimer = findViewById(R.id.txtTimer);
        txtWires = findViewById(R.id.txtWiresLeft);
        txtMoves = findViewById(R.id.txtActionTaken);
        grid = findViewById(R.id.gameGrid);
        pincode = findViewById(R.id.lnlPincode);

        txtPincode1 = findViewById(R.id.txtPincode1);
        txtPincode2 = findViewById(R.id.txtPincode2);
        txtPincode3 = findViewById(R.id.txtPincode3);
        txtPincode4 = findViewById(R.id.txtPincode4);
        buttonPressed = 0;

        Log.e("GameActivity", "onCreate"); //For testing to see if its working
        GameEngine.getInstance().createGrid(this);


        timeLeft = START_TIMER;
        startCountDown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pin_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(GameEngine.getInstance().getWireCount() == 0) {
            if (grid.getVisibility() == View.VISIBLE) {
                grid.setVisibility(View.GONE);
                pincode.setVisibility(View.VISIBLE);
                Toast.makeText(this,"!! Press the button again to go back !!",Toast.LENGTH_LONG).show();
            } else if (pincode.getVisibility() == View.VISIBLE) {
                pincode.setVisibility(View.GONE);
                grid.setVisibility(View.VISIBLE);
                Toast.makeText(this,"!! Press the button again to go back !!",Toast.LENGTH_LONG).show();
            }
        }else
            Toast.makeText(this,"!! Need to defuse all the wires first !!",Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(GameEngine.getInstance().getStartTimer()) {
                    timeLeft = millisUntilFinished;
                    updateTimer();
                    txtWires.setText(getString(R.string.Wires_Left,         //update both Wires, and
                            GameEngine.getInstance().getWireCount()));    //Wire and Score text views
                    txtMoves.setText(getString(R.string.Score,
                            GameEngine.getInstance().getScoreCount()));
                }
                else
                    countDownTimer.cancel();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                updateTimer();
                GameEngine.getInstance().onGameLost();
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

    public void num_Onclick(View v) {
        Button b = findViewById(v.getId());
        if(buttonPressed < 4) {
            switch (buttonPressed) {
                case 0:
                    txtPincode1.setText(b.getText());
                    break;
                case 1:
                    txtPincode2.setText(b.getText());
                    break;
                case 2:
                    txtPincode3.setText(b.getText());
                    break;
                case 3:
                    txtPincode4.setText(b.getText());
                    break;
            }
            buttonPressed++;
        }
    }

    public void power_Onclick(View v){
        int count = 0;
        for(int i=0;i<4;i++){
            switch (i) {
                case 0:
                    if(Integer.parseInt(txtPincode1.getText().toString()) == (GameEngine.getInstance().getPinCode(0))-10) count++;
                    break;
                case 1:
                    if(Integer.parseInt(txtPincode2.getText().toString()) == (GameEngine.getInstance().getPinCode(1))-10) count++;
                    break;
                case 2:
                    if(Integer.parseInt(txtPincode3.getText().toString()) == (GameEngine.getInstance().getPinCode(2))-10) count++;
                    break;
                case 3:
                    if(Integer.parseInt(txtPincode4.getText().toString()) == (GameEngine.getInstance().getPinCode(3))-10) count++;
                    break;
            }
        }
        if(count == 4) {
            countDownTimer.cancel();  //Stop the clock
            AlertDialog.Builder builder = new AlertDialog.Builder(this);  //Display an alert dialog that they have won!
            builder.setTitle("You Won!!!")
                    .setMessage("The bomb has been defused! Do you want to play again?")
                    // "Reset Quiz" Button
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        //This clears the intent
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        //This clears the intent
                            startActivity(intent);
                        }
                    });
            builder.create().show();
        }
        else    //Notify that its the wrong code
            Toast.makeText(this,"Pincode Denied! Try Again",Toast.LENGTH_LONG).show();
    }

    public void clr_Onclick(View v) {
        txtPincode1.setText("0");
        txtPincode2.setText("0");
        txtPincode3.setText("0");
        txtPincode4.setText("0");
        buttonPressed = 0;
    }
}
