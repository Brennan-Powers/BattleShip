/**
 * Created by Brennan Powers on 11/13/2017
 */

package com.example.nick.battleship;

public class Ship {
    private int size;
    public boolean floating;
    public int[] sizeArray;
    public int[][] BArray;
    public int orientation;
    public int ID;
    public int timesHit;
    public String name;

    public boolean checkFloat() {
		/*
		if(timesHit == size)
			return false;
		else
			return true;
			*/
        return (timesHit != size); //false if sunk, true if floating
    }


    public Ship(int inputSize, int id, String n) {
        floating = true;
        size = inputSize;
        ID = id;
        timesHit = 0;
        name = n;

        sizeArray = new int[size];
        for(int i = 0 ; i < size ; i++ ) {
            sizeArray[i] = 0;
        }
    }

    public Ship(int inputSize) {
        floating = true;
        size = inputSize;
        timesHit = 0;

        sizeArray = new int[size];
        for(int i = 0 ; i < size ; i++ ) {
            sizeArray[i] = 0;
        }

        // 0 means spot on ship is ok, 1 is that part of the ship is hit
        //System.out.println(size);
    }

    public int getShipSize() {
        int a = size;
        return a;
    }

    public int[] getShipArray() {
        int[] a = sizeArray;
        return a;
    }

    public void setBArray(int[][] a) {
        BArray = a;
    }//Location array. first column is the ships row location, second column is the ships column location
    // ex, destroyer at 0,0 ori=0 will make the array:
    // 0 , 0
    // 1 , 0


    public void setOri(int a) {
        orientation = a;
    }
}
