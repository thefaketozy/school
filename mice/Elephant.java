import java.util.*;

public class Elephant extends Animal {

    public Elephant(int index, int x, int y, int width, int length, int strikingDistance, LinkedList<Animal> animals) {
        super(index, x, y, width, length, strikingDistance, animals);
        this.alive = true;
    }

    public void move(int x, int y) { // Moves to specified coordinates
        if (x > width) { // Prevent animal from moving off the board
            x = width;
        } else if (x < 0) {
            x = 0;
        }
        if (y > length) {
            y = length;
        } else if (y < 0) {
            y = 0;
        }
        System.out.println("Elephant " + index + " to " + this.x + " " + this.y);
        this.x = x;
        this.y = y;
    }

    private void snotLaunch(int i) {
        int launchDistance = strikingDistance * 2;
        List<int[]> directions = Arrays.asList(new int[][] {{ launchDistance, 0 },
                                                            { -launchDistance, 0 }, 
                                                            { 0, launchDistance }, 
                                                            { 0, launchDistance },
                                                            { launchDistance, launchDistance },
                                                            { launchDistance, -launchDistance },
                                                            { -launchDistance, launchDistance },
                                                            { -launchDistance, -launchDistance } });
        Collections.shuffle(directions);
        int[] launchDir = directions.get(0);
        int newX = animals.get(i).getX() + launchDir[0];
        int newY = animals.get(i).getY() + launchDir[1];
        animals.get(i).move(newX, newY);
        System.out.println("Elephant " + index + " snorts up mouse " + animals.get(i).getIndex() + " and launches it to" + newX + " " + newY +  " with snot all over itself!!");
    }

    @Override
    public void run() {
        synchronized (animals) {
            boolean moveRandom = true; // The elephant should make a random move by default
            boolean canMove = true;
            int mouseCount = 0; // Number of mice reported on same square
            int onMouse = 0; // to be used only if there needs to be a snot launch
            for (int i = 0; i < animals.size(); i++) {
                if (this.x == animals.get(i).getX() && this.y == animals.get(i).getY()
                        && animals.get(i) instanceof Mouse) { // If elephant is on the same square as a mouse
                    mouseCount++;
                    onMouse = i;
                }
            }
            if (mouseCount >= 2) {
                // die
                System.out.println("Elephant " + index + " dies!");
                this.alive = false;
                moveRandom = false;
            } else if (mouseCount == 1) {
                snotLaunch(onMouse);
            } else {
                for (int i = 0; i < animals.size(); i++) {
                    if (distance(this.x, this.y, animals.get(i)) <= strikingDistance
                            && animals.get(i) instanceof Mouse) { // if within striking distance of a mouse
                        moveRandom = false;
                        System.out.println("Elephant " + index + " is frightened and must flee!");
                        List<int[]> directions = Arrays.asList(new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 },
                                { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } }); // All the directions the elephant can
                                                                               // move in
                        Collections.shuffle(directions); // Randomizes them
                        for (int[] dir : directions) { // Iterate through potential directions and move the elephant
                                                       // when one is found that does NOT bring the elephant closer to
                                                       // any mouse
                            int newX = 0;
                            int newY = 0;
                            for (int j = 0; j < animals.size(); j++) {
                                newX = this.x + dir[0];
                                newY = this.y + dir[1];
                                if (distance(newX, newY, animals.get(j)) < distance(this.x, this.y, animals.get(i))
                                        && animals.get(j) instanceof Mouse) { // If the potential move will bring it
                                                                              // closer to any mouse
                                    canMove = false;
                                }
                            }
                            if (canMove) { // If a direction that allows the elephant to move is found, the elephant
                                           // moves and this loop is exited
                                move(newX, newY);
                                break;
                            }
                            else {
                                System.out.println("Elephant " + index + " is frozen in fear!");
                                break;
                            }
                        }
                        break;
                    }
                }

                // move randomly
                if (moveRandom) { // This is not executed if the elephant has already moved to escape a mouse
                    List<int[]> directions = Arrays.asList(new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 },
                            { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } });
                    Collections.shuffle(directions);
                    int[] dir = directions.get(0);
                    move(this.x + dir[0], this.x + dir[1]);
                }
            }
            animals.notifyAll();
        }
    }
}