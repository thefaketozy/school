import java.util.ArrayList;

public class Caretaker<E> {
    ArrayList<Memento<E>> savedLists = new ArrayList<Memento<E>> ();
    public void addMemento(Memento<E> memento) {
        savedLists.add(memento);
    }

    public Memento<E> getMemento(int i) {
        return savedLists.get(i);
    }
}