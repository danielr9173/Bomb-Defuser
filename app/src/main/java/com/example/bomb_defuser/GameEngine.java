package com.example.bomb_defuser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.bomb_defuser.util.Generator;
import com.example.bomb_defuser.views.grid.Cell;

public class GameEngine {
    private static GameEngine instance;

    private int WIRE_NUMBER;
    private int WIDTH;
    private int HEIGHT;

    private boolean startTimer = true;
    private int scoreCounter = 0;
    private int wireCounter;  // Really buggy when used!

    private Context context;

    private Cell[][] BombDefuserGrid;

    public static GameEngine getInstance() {
        if( instance == null ){
            instance = new GameEngine();
        }
        return instance;
    }

    //These are all the getters and setters
    public void setWIRE_NUMBER(int WIRE_NUMBER) {
        this.WIRE_NUMBER = WIRE_NUMBER;
    }
    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }
    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }
    public int getWIRE_NUMBER() {
        return WIRE_NUMBER;
    }
    public int getWIDTH() {
        return WIDTH;
    }
    public int getHEIGHT() {
        return HEIGHT;
    }
    public boolean getStartTimer() {
        return startTimer;
    }
    public int getScoreCounter() {
        return scoreCounter;
    }
    public int getWireCounter() {
        return wireCounter;
    }

    public GameEngine(){
    }

    public void createGrid(Context context){
        Log.e("GameEngine", ""+WIDTH+HEIGHT+WIRE_NUMBER); //For testing!
        this.context = context;
        startTimer = true;
        scoreCounter = 0;
        wireCounter = WIRE_NUMBER;

        BombDefuserGrid = new Cell[WIDTH][HEIGHT];
        // create the grid and store it
        int[][] GeneratedGrid = Generator.generate(WIRE_NUMBER,WIDTH, HEIGHT);
        setGrid(context,GeneratedGrid);
    }

    private void setGrid( final Context context, final int[][] grid ){
        for( int x = 0 ; x < WIDTH ; x++ ){
            for( int y = 0 ; y < HEIGHT ; y++ ){
                if( BombDefuserGrid[x][y] == null ){
                    BombDefuserGrid[x][y] = new Cell( context , x,y);
                }
                BombDefuserGrid[x][y].setValue(grid[x][y]);
                BombDefuserGrid[x][y].invalidate();
            }
        }
    }



    public Cell getCellAt(int position) {
        int x = position % WIDTH;
        int y = position / WIDTH;

        return BombDefuserGrid[x][y];
    }

    private Cell getCellAt(int x, int y){
        return BombDefuserGrid[x][y];
    }

    public void click( int x , int y ){
        if( x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x,y).isClicked() ){
            getCellAt(x,y).setClicked();
            scoreCounter++;

            if( getCellAt(x,y).getValue() == 0 ){       //if empty display all numbers around that
                for( int xt = -1 ; xt <= 1 ; xt++ ){    //panel.
                    for( int yt = -1 ; yt <= 1 ; yt++){
                        if( xt != yt ){
                            click(x + xt , y + yt);
                        }
                    }
                }
            }
            if( getCellAt(x,y).isWire() ){
                startTimer = false;
                onGameLost();
            }
        }
        checkEnd();
    }



    private void checkEnd(){
        int wiresNotFound = WIRE_NUMBER;
        int notRevealed = WIDTH * HEIGHT;
        for ( int x = 0 ; x < WIDTH ; x++ ){
            for( int y = 0 ; y < HEIGHT ; y++ ){
                if( getCellAt(x,y).isRevealed() || getCellAt(x,y).isDefuse() ){
                    notRevealed--;
                }

                if( getCellAt(x,y).isDefuse() && getCellAt(x,y).isWire() ){
                    wiresNotFound--;
                }
            }
        }

        if( wiresNotFound == 0 && notRevealed == 0 ){
            startTimer = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("You Won!!!")
                    .setMessage("The bomb has been defused! Do you want to play again?")
                    // "Reset Quiz" Button
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, GameActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        //This clears the intent
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        //This clears the intent
                            context.startActivity(intent);
                        }
                    });
            builder.create().show();
        }
    }

    public void defuse(int x , int y ){
        boolean isDefused = getCellAt(x,y).isDefuse();
        getCellAt(x,y).setDefuse(!isDefused);
        getCellAt(x,y).invalidate();
    }

    private void onGameLost(){
        // handle lost game
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("BOOM!!!")
                .setMessage("The bomb exploded! Do you want to try again?")
                // "Reset Quiz" Button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, GameActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        //This clears the intent
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        //This clears the intent
                        context.startActivity(intent);
                    }
                });
        builder.create().show();

        for ( int x = 0 ; x < WIDTH ; x++ ) {
            for (int y = 0; y < HEIGHT; y++) {
                getCellAt(x,y).setRevealed();
            }
        }
    }
}
