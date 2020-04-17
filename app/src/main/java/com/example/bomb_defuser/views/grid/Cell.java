package com.example.bomb_defuser.views.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.bomb_defuser.GameEngine;
import com.example.bomb_defuser.R;

public class Cell extends BaseCell implements View.OnClickListener , View.OnLongClickListener{

    public Cell( Context context , int x , int y ){
        super(context);

        setPosition(x,y);

        setOnClickListener(this);
        setOnLongClickListener(this);
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
    public boolean onLongClick(View v) {
        GameEngine.getInstance().defuse( getXPos() , getYPos() );

        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPanel(canvas);

        if( isDefuse() ){
            drawDefuse(canvas);
        }else if( isRevealed() && isWire() && !isClicked() ){
            drawWire(canvas);
        }else {
            if( isClicked() ){
                if( getValue() == -1 ){
                    drawWireExploded(canvas);
                }else {
                    drawNumber(canvas);
                }
            }else{
                drawPanel(canvas);
            }
        }
    }

    private void drawWireExploded(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.wires_explosion);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawDefuse(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.defuse);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawPanel(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.panel);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawWire(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.wires);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNumber( Canvas canvas ){
        Drawable drawable = null;

        switch (getValue() ){
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.empty);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_8);
                break;
        }

        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }
}
