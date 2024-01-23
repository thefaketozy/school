import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Numbers { // This is the data structure that stores the input
    public List<Integer> nums = new ArrayList<Integer>();
    public List<Integer> sums = new ArrayList<Integer>();

    public Numbers() {
        // do stuff
    }

    public void sortNums() { // Sorts the numbers
        Collections.sort(nums);
    }

    public void addNum(int n) {
        nums.add(n);
    }

    public void addSum(int n) {
        sums.add(n);
    }

    public boolean searchNums(int target) { // List must be sorted for method to work

        for (int i = 0; i < nums.size() - 2; i++) {
            int x = i + 1; //Points to element adjecent to i
            int y = nums.size() - 1; //Points to last element in the list
            while (x < y) {
                if (nums.get(x) + nums.get(i) + nums.get(y) == target) { //Return true if some found
                    return true;
                }
                else if (nums.get(x) + nums.get(i) + nums.get(y) < target) { //If less than target, increment x
                    x++;
                }
                else if (nums.get(x) + nums.get(i) + nums.get(y) > target) { //If greater than target, increment y
                    y--;
                }
            }
        }
        return false; // This should only be reached if no sum is found

    }

}
