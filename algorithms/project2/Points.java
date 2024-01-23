/*
* Seth Schroeder
* Part of Project2
*/

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.Arrays;

public class Points {

    public Points() {
        // construct
    }

    private List<int[]> points = new ArrayList<int[]>(); // lattice points stored as 2-item arrays in this List

    public void addPoint(int x, int y) {

        int point[] = new int[] { x, y };
        points.add(point);

    }

    private int l1Measure(int[] firstPoint, int[] secondPoint) { // L1 Metric/manhattan method, to be used by a search
                                                                 // function

        int distance;
        distance = (Math.abs(firstPoint[0] - secondPoint[0]) + Math.abs(firstPoint[1] - secondPoint[1]));
        return distance;
    }

    private double l2Measure(int[] firstPoint, int[] secondPoint) { // L2 Metric, to be used by the search function

        double distance;
        distance = Math.sqrt(Math.pow(firstPoint[0] - secondPoint[0], 2) + Math.pow(firstPoint[1] - secondPoint[1], 2));
        return distance;
    }

    public int[] l1Center() { // This is the method that calculates the center point by L1, by combining the
                              // median
                              // values of the x and y coordinates
        int n = points.size();
        int i = 0;
        int xArray[] = new int[n];
        int yArray[] = new int[n];
        for (int[] arr : points) { // Split x and y coordinates into separate arrays
            xArray[i] = arr[0];
            yArray[i] = arr[1];
            i++;
        }
        Arrays.sort(xArray); // Sort them
        Arrays.sort(yArray);
        // Find median of each
        int medX;
        int medY;
        if (n % 2 == 0) { // if we have an even number of points
            int medOne = n / 2;
            int medTwo = medOne + 1;
            medX = (xArray[medOne] + xArray[medTwo]) / 2;
            medY = (yArray[medOne] + yArray[medTwo]) / 2;
        } else { // we have an odd number of points
            int med = (int) Math.ceil(n / 2);
            medX = xArray[med];
            medY = yArray[med];
        }
        int result[] = new int[] { medX, medY };
        return result;

    }

    public int l1CenterDistance(int[] center) { // Calculates sum of distance between the center and all the other
                                                // points
        int distance = 0;
        for (int[] point : points) {
            distance += l1Measure(center, point); // Add distance from center to current point to distance var
        }

        return distance;
    }

    private int getMaxX() { //Finds the maximum x value
        int max = 0;
        for (int[] point: points) {
            if(point[0] > max) {
                max = point[0];
            }
        }
        return max;
    }

    private int getMaxY() { //Finds the maximum y value
        int max = 0;
        for (int[] point: points) {
            if(point[1] > max) {
                max = point[1];
            }
        }
        return max;
    }

    public int[] l2Center() { // Returns the center via L2 metric by brute force
        double minDistance = 0; //Minimum distance so far
        double distance = 0; // For distance of all points from point i
        int maxX = getMaxX();
        int maxY = getMaxY();
        int minX = 0;
        int minY = 0;
        int center[] = new int[2]; //This value will be returned
        int minCenter[] = new int[2];

        for(int x = 0; x < maxX; x++) { //Brute force begins, loops through all potential points, keeps track of most center point found so far.
            center[0] = x;
            for(int y = 0; y < maxY; y++) {
                center[1] = y;
                distance = l2CenterDistance(center);
                if(minDistance == 0 || distance < minDistance) {
                    minDistance = distance;
                    minX = center[0];
                    minY = center[1];
                }
            }
        }
        
        minCenter[0] = minX;
        minCenter[1] = minY;
        return minCenter;        
    }

    public double l2CenterDistance(int[] center) { //Calculates distance of all points from the provided center point by L2 metric
        double distance = 0;

        for (int[] point: points) {
            distance += l2Measure(center, point);
        }

        return distance;
    }
}
