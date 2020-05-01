/**
 * <h1> Grid </h1>
 * This class is used as the GridView for everything that is on the board.
 * This class extends the GridView class for the reason that each cell
 * is being display as a grid like formation.
 * Created by: Daniel Ramirez, Robert Sosa
 * Date: 4/1/2020
 */

package com.example.bomb_defuser.views.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.bomb_defuser.GameEngine;

public class Grid extends GridView {

    public Grid(Context context , AttributeSet attrs){
        super(context,attrs);

        GameEngine.getInstance().createGrid(context);

        setNumColumns(GameEngine.getInstance().getWIDTH());
        setAdapter(new GridAdapter());
    }

    // This will have the correct dimensions of the board //
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return (GameEngine.getInstance().getWIDTH() * GameEngine.getInstance().getHEIGHT());
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return GameEngine.getInstance().getCellAt(position);
        }
    }
}
