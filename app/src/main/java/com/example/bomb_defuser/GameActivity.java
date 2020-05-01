/**
 * <h1> GameActivity </h1>
 * This is the class for the Activity_Game and has all the user
 * interactions that is on this activity.
 * Created by: Daniel Ramirez, Robert Sosa
 * Date: 4/1/2020
 */

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

        // Initialize all the Views
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
        GameEngine.getInstance().createGrid(this); //create the grid of the board


        timeLeft = GameEngine.getInstance().getGameTime(); //Set the game timer
        startCountDown(); //Start the count down
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pin_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(GameEngine.getInstance().getWireCount() == 0) {      //Check if the wires have been all defused
            if (grid.getVisibility() == View.VISIBLE) {
                grid.setVisibility(View.GONE);                  //Display the pin code board
                pincode.setVisibility(View.VISIBLE);
                Toast.makeText(this,"!! Press the button again to go back !!",Toast.LENGTH_LONG).show();
            } else if (pincode.getVisibility() == View.VISIBLE) {
                pincode.setVisibility(View.GONE);               //Display the game board
                grid.setVisibility(View.VISIBLE);
                Toast.makeText(this,"!! Press the button again to go back !!",Toast.LENGTH_LONG).show();
            }
        }else
            Toast.makeText(this,"!! Need to defuse all the wires first !!",Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    // This is for the in game timer //
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(GameEngine.getInstance().getStartTimer()) {
                    timeLeft = millisUntilFinished;
                    updateTimer();
                    txtWires.setText(getString(R.string.Wires_Left,         //update both Wires, and
                            GameEngine.getInstance().getWireCount()));    // update the Wire and Score text views
                    txtMoves.setText(getString(R.string.Score,
                            GameEngine.getInstance().getScoreCount()));
                }
                else
                    countDownTimer.cancel(); //If the game has ended stop timer
            }

            @Override
            public void onFinish() {   //If the timer ran out then the game is loss
                timeLeft = 0;
                updateTimer();
                GameEngine.getInstance().onGameLost();
            }
        }.start();
    }

    // Covert the milliseconds to minutes and seconds
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

    // This is used to input the pin code to the bomb
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

    // This is the power button that is the big red button. This checks if the pin code is correct or not
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
                    })
                    .setCancelable(false);
            builder.create().show();
        }
        else    //Notify that its the wrong code
            Toast.makeText(this,"Pincode Denied! Try Again",Toast.LENGTH_LONG).show();
    }

    // Clear the pin code on the board
    public void clr_Onclick(View v) {
        txtPincode1.setText("0");
        txtPincode2.setText("0");
        txtPincode3.setText("0");
        txtPincode4.setText("0");
        buttonPressed = 0;
    }
}
