import java.util.LinkedList;

public interface Originator<E> {
    //private LinkedList<E> theList = new LinkedList();

    public Memento<E> createMemento();

    public void restoreMemento(Memento<E> memento);

}