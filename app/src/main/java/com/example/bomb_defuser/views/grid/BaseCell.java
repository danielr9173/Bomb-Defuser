package com.example.bomb_defuser.views.grid;

import android.content.Context;
import android.view.View;

import com.example.bomb_defuser.GameEngine;

public abstract class BaseCell extends View {

    private int value;
    private boolean isWire;
    private boolean isRevealed;
    private boolean isClicked;
    private boolean isDefuse;

    private int x , y;
    private int position;

    public BaseCell(Context context ){
        super(context);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        isWire = false;
        isRevealed = false;
        isClicked = false;
        isDefuse = false;

        if( value == -1 ){
            isWire = true;
        }

        this.value = value;
    }

    public boolean isWire() {
        return isWire;
    }

    public void setWire(boolean wire) {
        isWire = wire;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed() {
        isRevealed = true;
        invalidate();
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked() {
        this.isClicked = true;
        this.isRevealed = true;

        invalidate();
    }

    public boolean isDefuse() {
        return isDefuse;
    }

    public void setDefuse(boolean defuse) {
        isDefuse = defuse;
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition( int x , int y ){
        this.x = x;
        this.y = y;

        this.position = y * GameEngine.getInstance().getWIDTH() + x;

        invalidate();
    }
}

