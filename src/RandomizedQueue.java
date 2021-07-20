import edu.princeton.cs.algs4.StdArrayIO;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] itemArrayList;
    private int capacity = 1;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        itemArrayList = new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == capacity) {
            capacity *= 2;
            Object[] newArray = new Object[capacity];
            for (int i = 0; i < n; i++) {
                newArray[i] = itemArrayList[i];
            }
            itemArrayList = newArray;
        }
        itemArrayList[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(n);
        Object temp = itemArrayList[idx];
        itemArrayList[idx] = itemArrayList[n - 1];
        itemArrayList[--n] = null;

        if (n > 0 && n <= capacity / 4) {
            capacity /= 2;
            Object[] newArray = new Object[capacity];
            for (int i = 0; i < n; i++) {
                newArray[i] = itemArrayList[i];
            }
            itemArrayList = newArray;
        }

        return (Item) temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(size());
        return (Item) itemArrayList[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<Item> {
        private int[] randomIndex;
        private int current = 0;

        public ArrayListIterator() {
            randomIndex = new int[size()];
            for (int i = 0; i < size(); i++) {
                randomIndex[i] = i;
            }
        }

        @Override
        public boolean hasNext() {
            return current < randomIndex.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int idx = StdRandom.uniform(current, itemArrayList.length);

            return (Item) itemArrayList[randomIndex[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        try {
            randomizedQueue.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println("Dequeue from empty queue: " + e);
        }
        try {
            randomizedQueue.enqueue(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Enqueue null item: " + e);
        }

        System.out.println("Randomized Queue size: " + randomizedQueue.size());

        randomizedQueue.enqueue("a");
        randomizedQueue.enqueue("b");
        randomizedQueue.enqueue("c");

        for (String s : randomizedQueue) {
            System.out.println("Random item: " + s);
        }

        try {
            randomizedQueue.iterator().remove();
        } catch (UnsupportedOperationException e) {
            System.out.println("Iterator remove method: " + e);
        }

        int countA = 0;
        int countB = 0;
        int countC = 0;

        for (int i = 0; i < 3000000; i++) {
            switch (randomizedQueue.sample()) {
                case "a":
                    countA += 1;
                    break;
                case "b":
                    countB += 1;
                    break;
                case "c":
                    countC += 1;
                    break;
            }
        }
        System.out.print("a appeared: " + countA + "\nb appeared: " + countB + "\nc appeared: " + countC + "\n");
    }
}
