
/*
* Project 3
* Seth Schroeder
* COMP 482
*/
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Project3 {
    static List<Integer> listOne = new ArrayList<Integer>(); // Global lists for use with integers
    static List<Integer> listTwo = new ArrayList<Integer>();

    static int max(int a, int b) {
        if (a > b)
            return a;
        return b;
    }

    static int min(int a, int b) {
        if (a < b)
            return a;
        return b;
    }

    static double getMedian(List<Integer> list, int first, int last) { //Get the median item in a list
        int diff = (last - first);
        if (diff % 2 == 0) {
            return list.get(first + (diff / 2));
        } else {
            return 1.0 * (list.get(first + (diff / 2)) + list.get(first + (diff / 2) + 1)) / 2;
        }
    }

    static double getMiddle(List<Integer> l1, List<Integer> l2, int l1First, int l1Last, int l2First,
            int l2Last) { //Big function that gets the middle item of both arrays without merging them

        if ((l1Last - l1First == 0) && ((l2Last - l2First == 0))) { //If the sub-arrays have been emptied, return the median
            return (l1.get(0) + l2.get(0)) / 2;
        }

        if ((l1Last - l1First == 1) && ((l2Last - l2First == 1))) { //If there's one item in each array 
            return (1.0 * (max(l1.get(l1First), l2.get(l2First)) + min(l1.get(l1Last), l2.get(l2Last)))) / 2;
        }

        double m1 = getMedian(l1, l1First, l1Last);
        double m2 = getMedian(l2, l2First, l2Last);

        if (m2 == m1) { //If both medians are the same, return
            return m2;
        }

        if (m1 < m2) { 
            if ((l1Last - l1First) % 2 == 0) { 
                l1First = l1First + (l1Last - l1First) / 2;
                l2Last = l2First + (l2Last - l2First) / 2;
            } else {
                l1First = l1First + (l1Last - l1First) / 2;
                l2Last = l2First + (l2Last - l2First) / 2 + 1;
            }
        }

        else {
            if ((l2Last - l2First) % 2 == 0) {
                l2First = l2First + (l2Last - l2First) / 2;
                l1Last = l1First + (l1Last - l1First) / 2;
            } else {
                l2First = l2First + (l2Last - l2First) / 2;
                l1Last = l1First + (l1Last - l1First) / 2 + 1;
            }
        }
        return getMiddle(l1, l2, l1First, l1Last, l2First, l2Last);
    }

    static int getInversions(List <Integer> list) { //Calculate the number of inversions
        int inv = 0;
        for(int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    inv++;
                }
            }
        }

        return inv; 
    }
    

    public static void main(String[] args) throws Exception {

        // load input file into a scanner
        File inputFile = new File("input3.txt");
        Scanner input = new Scanner(inputFile);
        input.useDelimiter("\\s+");
        int n; // number of sorted integers to put in each list
        n = input.nextInt();
        int k;
        for (int i = 0; i < n; i++) { // Add n integers to the first list
            k = input.nextInt();
            listOne.add(k);
        }
        while (input.hasNextInt()) { // Add remaining ints to the second list
            k = input.nextInt();
            listTwo.add(k);
        }
        System.out.println(getMiddle(listOne, listTwo, 0, listOne.size() - 1, 0, listTwo.size() - 1));

        //Combine the two lists
        List<Integer> bigList = new ArrayList<Integer>();
        for(int i: listOne) {
            bigList.add(i);
        }
        for(int i: listTwo) {
            bigList.add(i);
        }

        System.out.println(getInversions(bigList));

        input.close(); // Don't forget this again.
    }
}