/**
 * <h1> Generator </h1>
 * This class is used to generate the playing board that has all the
 * wires and numbers on the board.
 * Created by: Daniel Ramirez, Robert Sosa
 * Date: 4/1/2020
 */

package com.example.bomb_defuser.util;

import com.example.bomb_defuser.GameEngine;

import java.util.Random;

public class Generator {

    public static int[][] generate( int wirenumber, final int width, final int height){
       //Random for generating numbers
        Random r = new Random();

        //A 2 dimensional array use to represent the playing grid
        int [][] grid = new int[width][height];

        while( wirenumber > 0){
            int x = r.nextInt(width);
            int y = r.nextInt(height);

            //-1 is a wire
            if ( grid[x][y] != -1){
                grid[x][y] = -1;
                wirenumber--;
            }
        }

        while(GameEngine.getInstance().getPinCount() >= 0){
            int x = r.nextInt(width);
            int y = r.nextInt(height);

            //this is a pin code
            if( grid[x][y] != -1){
                grid[x][y] = GameEngine.getInstance().getPinCode(GameEngine.getInstance().getPinCount());
                GameEngine.getInstance().setPinCount(GameEngine.getInstance().getPinCount()-1);
            }
        }

        grid = calculateNeighbors(grid, width, height);

        return grid;
    }

    private static int[][] calculateNeighbors( int[][] grid, final int width, final int height ){
        for( int x = 0; x < width; x++){
            for ( int y = 0; y < height; y++){
                grid[x][y] = getNeighborNumber(grid, x, y, width, height);
            }
        }

        return grid;
    }

    //Find the number of neighboring wires for an empty tile
    private static int getNeighborNumber( final int[][] grid, final int x, final int y, final int width, final int height){

        if( grid[x][y] == -1 ) return -1;

        int count = 0;

        if( isWireAt(grid,x - 1,y + 1,width,height)) count++; // top-left
        if( isWireAt(grid,x,y + 1,width,height)) count++; // top
        if( isWireAt(grid,x + 1,y + 1,width,height)) count++; // top-right
        if( isWireAt(grid,x - 1,y,width,height)) count++; // left
        if( isWireAt(grid,x + 1 ,y,width,height)) count++; // right
        if( isWireAt(grid,x - 1 ,y - 1,width,height)) count++; // bottom-left
        if( isWireAt(grid,x,y - 1,width,height)) count++; // bottom
        if( isWireAt(grid,x + 1 ,y - 1,width,height)) count++; // bottom-right

        if(grid[x][y] == GameEngine.getInstance().getPinCode(3) && count == 0)
            return GameEngine.getInstance().getPinCode(3);
        else if(grid[x][y] == GameEngine.getInstance().getPinCode(2) && count == 0)
            return GameEngine.getInstance().getPinCode(2);
        else if(grid[x][y] == GameEngine.getInstance().getPinCode(1) && count == 0)
            return GameEngine.getInstance().getPinCode(1);
        else if(grid[x][y] == GameEngine.getInstance().getPinCode(0) && count == 0)
            return GameEngine.getInstance().getPinCode(0);
        else if(grid[x][y] != 0 && grid[x][y] != -1){
            Random random = new  Random();
            int a = random.nextInt(width);
            int b = random.nextInt(height);

            while (grid[a][b] != 0){
                a = random.nextInt(width);
                b = random.nextInt(height);
            }
            grid[a][b] = grid[x][y];
        }

        return count;
    }

    //Checks a specific tile on the grid to see if there is a wire there
    private static boolean isWireAt(final int [][] grid, final int x, final int y, final int width, final int height){
        if( x >= 0 && y >=0 && x < width && y < height){
            return grid[x][y] == -1;
        }
        return false;
    }
}
