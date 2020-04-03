package com.example.bomb_defuser.util;

import java.util.Random;

public class Generator {

    public static int[][] generate( int bombnumber, final int width, final int height){
       //Random for generating numbers
        Random r = new Random();

        //A 2 dimensional array use to represent the playing grid
        int [][] grid = new int[width][height];
        for (int x = 0; x < width; x++){
            grid[x] = new int[height];
        }

        while( bombnumber > 0){
            int x = r.nextInt(width);
            int y = r.nextInt(height);

            //-1 is the bomb
            if ( grid[x][y] != -1){
                grid[x][y] = -1;
                bombnumber--;
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

    //Find the number of neighboring bombs for an empty tile
    private static int getNeighborNumber( final int[][] grid, final int x, final int y, final int width, final int height){

        if( grid[x][y] == -1 ){
            return -1;
        }

        int count = 0;

        //Top Left
        if( isMineAt(grid,x - 1,y + 1,width,height)){
            count++;
        }
        //Top
        if( isMineAt(grid,x     ,y + 1,width,height)){
            count++;
        }
        //Top Right
        if( isMineAt(grid,x + 1,y + 1,width,height)){
            count++;
        }
        //Left
        if( isMineAt(grid,x - 1,y       ,width,height)){
            count++;
        }
        //Right
        if( isMineAt(grid,x + 1,y       ,width,height)){
            count++;
        }
        //Bottom Left
        if( isMineAt(grid,x - 1,y - 1,width,height)){
            count++;
        }
        //Bottom
        if( isMineAt(grid,x     ,y - 1,width,height)){
            count++;
        }
        //Bottom Right
        if( isMineAt(grid,x + 1,y - 1,width,height)){
            count++;
        }

        return count;
    }

    //Checks a specific tile on the grid to see if there is a bomb there
    private static boolean isMineAt( final int [][] grid, final int x, final int y, final int width, final int height){
        if( x >= 0 && y >=0 && x < width && y < height){
            if( grid[x][y] == -1){
                return true;
            }
        }
        return false;
    }
}
