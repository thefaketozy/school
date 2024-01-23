/*
* Project 4
* Seth Schroeder
* COMP 482
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

class Project4 {

    public static void main(String[] args) throws Exception {
        // load input file into a scanner
        File inputFile = new File("input4.txt");
        Scanner input = new Scanner(inputFile);
        input.useDelimiter("\\s+");
        int x, y; // dimensions of the array
        x = input.nextInt();
        y = input.nextInt(); // grab the first two ints in the file and use them as dimensions.
        int[][] table = new int[x][y]; // declare the array
        for (int i = 0; i < x; i++) { // nested loops load the integers into the 2d array.
            for (int j = 0; j < y; j++) {
                table[i][j] = input.nextInt();
            }
        }

        int longestPath = longestPath(table, x, y);

        System.out.println(longestPath);

        input.close();

    }

    static int longestPath(int table[][], int x, int y) {
        int dynamicTable[][] = new int[x][y]; //Create a new table for dynamic programming
        int longest = 0; //longest value so far
        
        for (int[] row : dynamicTable) {
            Arrays.fill(row, 1);
        }
        for (int i = 1; i < x; i++) {
            for (int j = 1; j < y; j++) { //And we loop through both 2D arrays
                boolean increment = false;
                if (table[i][j - 1] > table[i][j]) {
                    increment = true;
                }
                if (table[i - 1][j] > table[i][j]) {
                    increment = true;
                }
                if (increment) { //If either the spot above or to the left of the current is greater than current
                    dynamicTable[i][j] = Math.max(dynamicTable[i][j - 1], dynamicTable[i - 1][j]) + 1; //Adds max to the current + 1. (increments the count)
                }
                
                if (dynamicTable[i][j] > longest) { //increase longest value if a longer is found in the dynamic table
                    longest = dynamicTable[i][j];
                }
            }

        }
        
        return longest;
    }
}