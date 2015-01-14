import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    public Deque() {

    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node newFirst = new Node();
        newFirst.item = item;

        if (isEmpty()) {
            this.first = newFirst;
            this.last = newFirst;
        } else {
            Node oldFirst = this.first;
            newFirst.next = oldFirst;
            oldFirst.prev = newFirst;
            this.first = newFirst;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node newLast = new Node();
        newLast.item = item;

        if (isEmpty()) {
            this.first = newLast;
            this.last = newLast;
        } else {
            Node oldLast = this.last;
            newLast.prev = oldLast;
            oldLast.next = newLast;
            this.last = newLast;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = this.first.item;
        this.first = this.first.next;
        size--;

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = this.last.item;
        this.last = this.last.prev;
        size--;

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = this.current.item;
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}
