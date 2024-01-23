/*
* Project 1
* COMP 482
* Seth Schroeder
* October 9, 2020
*/
import java.io.File;
import java.util.Scanner;


class Project1 {

    public static void main(String[] args) throws Exception {
        
        File inputFile = new File("input1.txt");
        Scanner inScanner = new Scanner(inputFile); //Loads the file in
        //Read individual lines into the numbers object
        inScanner.useDelimiter("\\s+");
        Numbers numbers = new Numbers();
        int n;
        boolean addSums = false; //This is set true when 0 is hit in the input file
        
        while(inScanner.hasNextInt()) { 
            n = inScanner.nextInt();
            if (n != 0 && !addSums) { //Adds numbers to the nums list
                numbers.addNum(n);
            }
            else if (n == 0) { //Detect 0, continues
                addSums = true;
                continue;
            }
            else if (addSums) { // Adds remaining numbers to sums list
                numbers.addSum(n);
            }

        }
        numbers.sortNums(); // Sorts

        //input in, do searching stuff
        for (int sum: numbers.sums) {
            System.out.printf("%d ", sum); //Print out target sum
            boolean foundCombo = numbers.searchNums(sum);   //Searches
            if (foundCombo) {
                System.out.println("YES");
            }
            else {
                System.out.println("NO");
            }
        }

        inScanner.close();

    }
}