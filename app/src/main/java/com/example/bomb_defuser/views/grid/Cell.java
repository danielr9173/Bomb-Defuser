package com.example.bomb_defuser.views.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.bomb_defuser.GameEngine;
import com.example.bomb_defuser.R;

public class Cell extends BaseCell implements View.OnClickListener{

    public Cell(Context context, int position){
        super(context);

        setPosition(position);

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        GameEngine.getInstance().click( getXPos(), getYPos() );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("Minesweeper", "Cell::onDraw");

        drawButton(canvas);

        if( isFlagged() ){
            drawFlag(canvas);
        }else if( isRevealed() && isBomb() && !isClicked() ){
            drawNormalBomb(canvas);
        }else{
            if( isClicked() ){
                if ( getValue() == -1 ){
                    drawBombExploded(canvas);
                }else {
                    drawNumber(canvas);
                }
            }else {
                drawButton(canvas);
            }
        }
    }

    private void drawBombExploded(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_bombexploded);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawFlag(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_flag);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawButton(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_hidden);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNormalBomb(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_bomb);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNumber(Canvas canvas){
        Drawable drawable = null;

        switch (getValue()){
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_0);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.minesweepertile_8);
                break;
        }
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }
}
