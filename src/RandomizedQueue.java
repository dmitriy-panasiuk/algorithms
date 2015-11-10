import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size = 0;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size() == items.length) {
            resize(items.length * 2);
        }

        items[this.size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        prepareDequeue();
        Item item = items[--this.size];
        items[this.size] = null;
        if (this.size > 0 && this.size == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return items[StdRandom.uniform(size())];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] iteratorItems;
        private int iteratorSize;

        private RandomizedQueueIterator() {
            iteratorSize = size();
            iteratorItems = (Item[]) new Object[iteratorSize];
            for (int i = 0; i < iteratorSize; i++) {
                iteratorItems[i] = items[i];
            }
        }

        @Override
        public boolean hasNext() {
            return iteratorSize > 0;
        }

        @Override
        public Item next() {
            if (iteratorSize == 0) {
                throw new NoSuchElementException();
            }

            prepareNext();
            Item item = items[--iteratorSize];
            items[iteratorSize] = null;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void prepareNext() {
            int randomN = StdRandom.uniform(iteratorSize);

            Item item1 = iteratorItems[randomN];
            iteratorItems[randomN] = iteratorItems[iteratorSize - 1];
            items[iteratorSize - 1] = item1;
        }
    }

    private void prepareDequeue() {
        int randomN = StdRandom.uniform(size());

        Item item1 = items[randomN];
        items[randomN] = items[size() - 1];
        items[size() - 1] = item1;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < items.length && i < capacity; i++) {
            copy[i] = items[i];
        }

        this.items = copy;
    }

    public static void main(String[] args) {

    }
}
