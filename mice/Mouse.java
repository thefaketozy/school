import java.util.*;

public class Mouse extends Animal {

    private Random rand = new Random();
    private int counter = 0;

    Mouse(int index, int x, int y, int width, int length, int strikingDistance, LinkedList<Animal> animals) {
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
        System.out.println("Mouse " + index + " to " + this.x + " " + this.y);
        this.x = x;
        this.y = y;
    }

    public boolean isOnElephant() { // Only used during spawning
        for (Animal animal : animals) {
            if (animal instanceof Elephant && animal.getX() == this.x && animal.getY() == this.y) {
                return true;
            }
        }
        return false;
    }

    public void respawn() {
        this.x = rand.nextInt(width);
        this.y = rand.nextInt(length);
    }

    @Override
    public void run() {
        synchronized (animals) {
            while (counter < 2) { // makes the mouse move twice
                boolean moveRandom = true;
                boolean alone = true;
                for (int i = 0; i < animals.size(); i++) { // check if alone (if within striking distance of another
                                                           // mouse)
                    if (distance(this.x, this.y, animals.get(i)) <= strikingDistance
                            && animals.get(i).getIndex() != this.index && animals.get(i) instanceof Mouse) {
                        alone = false;
                        break; // Stop unnecessary iterations
                    }
                }
                // Determine if within striking distance of an elephant
                for (int i = 0; i < animals.size(); i++) {
                    if (distance(this.x, this.y, animals.get(i)) <= strikingDistance
                            && animals.get(i) instanceof Elephant
                            && animals.get(i).alive()) { //If within striking distance of an alive elephant
                        if (!alone) { // Attack the elephant if not alone
                            System.out.println("Mouse " + index + " attacks!");
                            moveRandom = false;
                            int minDistance = strikingDistance + 1; // Vars for determining movement
                            int[] minDistDir = { 0, 0 };
                            int newX;
                            int newY;
                            List<int[]> directions = Arrays.asList(new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 },
                                    { 0, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } });
                            Collections.shuffle(directions);
                            for (int[] dir : directions) {
                                newX = this.x + dir[0];
                                newY = this.y + dir[1];
                                if (minDistance > distance(newX, newY, animals.get(i))) { // Find movement that brings
                                                                                          // it closest to elephant
                                    minDistance = distance(newX, newY, animals.get(i));
                                    minDistDir = dir;
                                }
                            }
                            move(this.x + minDistDir[0], this.y + minDistDir[1]);
                            break;
                        } else if (alone) { // Frozen in fear, does not move
                            System.out.println("Mouse " + index + " is frozen in fear!");
                            moveRandom = false;
                            break;
                        }
                    }
                }
                // If loop is concluded and elephant isn't within striking distance of any
                // elephant, move randomly

                if (moveRandom) {
                    List<int[]> directions = Arrays.asList(new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 },
                                    { 0, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } });
                    Collections.shuffle(directions);
                    int[] dir = directions.get(0);
                    move(this.x + dir[0], this.y + dir[1]);
                }
                counter++;
            }
            animals.notifyAll();
        }
    }
}