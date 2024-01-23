/*
*
* Project 2
* Seth Schroeder
* Nov. 1, 2020
* 
*/

import java.util.*;
import java.io.*;

class Project2 {
    public static void main(String[] args) throws Exception {
        //read file input
        File inputFile = new File("input2.txt");
        Scanner scanner = new Scanner(inputFile); //Loads the file in
        Points points = new Points();

        scanner.useDelimiter("\\s+");
        int x, y;
        while(scanner.hasNextInt()) {   //Adds the points into the point object
            x = scanner.nextInt();
            y = scanner.nextInt();
            points.addPoint(x, y);
        }

        //Perform calculations
        int l1Center[] = points.l1Center(); //Finds Centermost point
        int l1Dist = points.l1CenterDistance(l1Center); //Finds distance from centermost point
        int l2Center[] = points.l2Center();
        double l2Dist = points.l2CenterDistance(l2Center);

        //Print results
        System.out.printf("(%d,%d) %d\n", l1Center[0], l1Center[1], l1Dist);
        System.out.printf("(%d,%d) %f", l2Center[0], l2Center[1], l2Dist);

        scanner.close();
        
    }
}
