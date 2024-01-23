import java.util.LinkedList;
public class Memento<E> {

    private LinkedList<E> theList;

    public Memento(LinkedList<E> theList) {
        this.theList = theList;

    }
    
    public LinkedList<E> getSavedLinkedList() {
        return theList;
    }
}