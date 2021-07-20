import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int n = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newFirst = new Node();
        newFirst.item = item;
        if (isEmpty()) {
            first = newFirst;
            last = newFirst;
        } else {
            first.prev = newFirst;
            newFirst.next = first;
            first = newFirst;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newLast = new Node();
        newLast.item = item;
        if (isEmpty()) {
            first = newLast;
            last = newLast;
        } else {
            newLast.prev = last;
            last.next = newLast;
            last = newLast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Node newFirst = first;
            first = first.next;
            n--;
            if (isEmpty()) {
                last = null;
            }
            else {
                first.prev = null;
            }
            return newFirst.item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Node newLast = last;
            last = last.prev;
            n--;
            if (isEmpty()) {
                first = null;
            }
            else {
                last.next = null;
            }
            return newLast.item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node cur = first;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            if (cur == null) {
                throw new NoSuchElementException();
            }
            Item item = cur.item;
            cur = cur.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> stringDeque = new Deque<>();
        try {
            stringDeque.removeFirst();
        }
        catch (NoSuchElementException e) {
            System.out.println(e);
        }
        try {
            stringDeque.removeLast();
        }
        catch (NoSuchElementException e) {
            System.out.println(e);
        }
        try {
            stringDeque.addFirst(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Add null item to first: " + e);
        }
        try {
            stringDeque.addLast(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Add null item to last: " + e);
        }
        stringDeque.addFirst("a");
        stringDeque.addLast("b");
        stringDeque.addLast("c");
        for (String s : stringDeque) {
            System.out.println(s);
        }
        try {
            stringDeque.iterator().remove();
        } catch (UnsupportedOperationException e) {
            System.out.println("Call iterator remove: " + e);
        }
        System.out.println("Remove items from the front of deque");
        try {
            for (int i = 0; i < 4; i++) {
                System.out.print("Item " + i + ": ");
                System.out.println(stringDeque.removeFirst());
            }
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
    }
}
