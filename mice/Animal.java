import java.util.*;

public abstract class Animal extends Thread {
    protected int index; // The animals have an internal index for identifying themselves and it begins
                         // at 1 for human readability
    protected int x; // position vars
    protected int y;
    protected int width;
    protected int length;
    protected int strikingDistance;
    protected LinkedList<Animal> animals;
    protected boolean alive;

    Animal(LinkedList<Animal> animals) {
        this.animals = animals;
    }

    Animal(int index, int x, int y, int width, int length, int strikingDistance, LinkedList<Animal> animals) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        this.strikingDistance = strikingDistance;
        this.animals = animals;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean alive() {
        return this.alive;
    }

    
    protected int distance(int x, int y, Animal otherAnimal) { // resuable pythagorean theorem
        int otherX = otherAnimal.getX();
        int otherY = otherAnimal.getY();
        return (int) Math.sqrt(Math.pow((x - otherX), 2) + Math.pow((y - otherY), 2));
    }

    abstract public void move(int x, int y);
}
