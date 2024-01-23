/* COMP 333 Project 2
Seth Schroeder */

import java.util.LinkedList;

public class SmartLinkedList<E> implements Originator<E> {
    private LinkedList<E> theList = new LinkedList<E>();
    private Caretaker<E> caretaker = new Caretaker<E>();


    public Memento<E> createMemento() {
        return new Memento<E>(theList);
    }

    public void restoreMemento(Memento<E> memento) {
        theList = memento.getSavedLinkedList();
    }

    public void add(E e) {
        caretaker.addMemento(createMemento());
        theList.add(e);
    }

    public void addFirst(E e) {
        caretaker.addMemento(createMemento());
        theList.addFirst(e);
    }

    public void addLast(E e) {
        caretaker.addMemento(createMemento());
        theList.addLast(e);
    }

    public void clear(E e) {
        caretaker.addMemento(createMemento());
        theList.clear();
    }

    public boolean contains(Object o) {
        return theList.contains(o);
    }

    public E get(int index) {
        return theList.get(index);
    }

    public E getFirst() {
        return theList.getFirst();
    }

    public E getLast() {
        return theList.getLast();
    }

    public E remove(int index) {
        caretaker.addMemento(createMemento());
        return theList.remove();
    }

    public int size() {
        return theList.size();
    }
}