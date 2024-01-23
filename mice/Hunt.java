/* Mice vs. Elephants Project
Seth Schroeder
COMP 333
Professor Jeffrey Wiegley
August 2nd, 2020
*/

import java.util.*;

public class Hunt {

    static int width;
    static int length;
    static LinkedList<Animal> animals = new LinkedList<Animal>();
    static Random rand = new Random();

    public static void main(String[] args) {

        // Get command line arguments.
        width = Integer.parseInt(args[0]);
        length = Integer.parseInt(args[1]);
        int strikingDistance = Integer.parseInt(args[2]);
        int numberOfElephants = Integer.parseInt(args[3]);
        int numberOfMice = Integer.parseInt(args[4]);
        long turnCounter = 1;

        for (int i = 1; i <= numberOfElephants; i++) {
            Elephant elephant = new Elephant(i, rand.nextInt(width), rand.nextInt(length), width, length,
                    strikingDistance, animals);
            animals.add(elephant);
        }
        for (int i = 1; i <= numberOfMice; i++) {
            Mouse mouse = new Mouse(i, rand.nextInt(width), rand.nextInt(length), width, length, strikingDistance,
                    animals);
            while (mouse.isOnElephant()) { // Will move the mouse randomly if on elephant
                mouse.respawn();
            }
            animals.add(mouse);
        }

        synchronized (animals) {
            for (Animal animal : animals) { // Take first turn
                animal.start();
            }
            System.out.println("Turn: " + turnCounter);
        }

        for (Animal animal : animals) {
            try {
                animal.join();
            } catch (Exception e) {
            }
        }
        int elephantsLeft = numberOfElephants;

        while (elephantsLeft > 0) {
            elephantsLeft = 0;
            turnCounter++;
            System.out.println("Turn: " + turnCounter);
            synchronized (animals) {
                for (Animal animal : animals) {
                    if (animal instanceof Elephant && animal.alive()) {
                        animal = new Elephant(animal.getIndex(), animal.getX(), animal.getY(), width, length,
                                strikingDistance, animals);
                        elephantsLeft++;
                        animal.start();
                    } else if (animal instanceof Mouse && elephantsLeft > 0) {
                        animal = new Mouse(animal.getIndex(), animal.getX(), animal.getY(), width, length,
                                strikingDistance, animals);
                        animal.start();
                    }
                }
            }
            for (Animal animal : animals) {
                try {
                    animal.join();
                } catch (Exception e) {

                }
            }
        }

    }
}
