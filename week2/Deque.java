import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int dequeSize;
 
    private class Node
    {
        Node next = null;
        Node previous = null;
        Item item = null;
    }
    // construct an empty deque
    public Deque()
    {
        first = new Node();
        last = new Node();
        first.next = last;
        last.previous = first;
        dequeSize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() { return dequeSize == 0; }

    // return the number of items on the deque
    public int size() { return dequeSize; }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null) { throw new IllegalArgumentException("cannot add a null item"); }
        Node newNode = new Node();
        newNode.item = item;
        Node oldSecondToFirst = first.next;
        first.next = newNode;
        newNode.next = oldSecondToFirst;
        newNode.previous = first;
        oldSecondToFirst.previous = newNode;
        dequeSize++;  
    }
    
    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null) { throw new IllegalArgumentException("cannot add a null item"); }
        Node newNode = new Node();
        newNode.item = item;
        Node oldSecondToLast = last.previous;
        last.previous = newNode;
        newNode.next = last;
        newNode.previous = oldSecondToLast;
        oldSecondToLast.next = newNode;
        dequeSize++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (dequeSize == 0) { throw new NoSuchElementException(); }
        Node nodeToRemove = first.next;
        first.next = nodeToRemove.next;
        Node newFirst = first.next;
        newFirst.previous = first;
        dequeSize--;
        return nodeToRemove.item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (dequeSize == 0) { throw new NoSuchElementException(); }
        Node nodeToRemove = last.previous;
        last.previous = nodeToRemove.previous;
        Node newLast  = last.previous;
        newLast.next = last;
        dequeSize--;
        return nodeToRemove.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first.next;
        public boolean hasNext()
        {
            return current.item != null;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        public Item next()
        {
            if (current.item == null) { throw new java.util.NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<String> testDeque = new Deque<String>();
        String s = "";
        boolean front = true;

        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            if (front)
            {    
                StdOut.println("adding " + s + " to front");
                testDeque.addFirst(s);
            }
            else 
            {
                StdOut.println("adding " + s + " to back");
                testDeque.addLast(s);            
            }
            front = !front; 
        }
        StdOut.println("-----------------------------");
        StdOut.println("Iterating over all contents");
        StdOut.print("There are " + testDeque.dequeSize + " items\n");

        for (String str: testDeque)
        {
            StdOut.println(str);
        }

        StdOut.println("-----------------------------");
        StdOut.println("Removing components, alternating front and back");
        front = true;
        while (!testDeque.isEmpty())
        {
            if (front) { StdOut.println(testDeque.removeFirst()); }
            else { StdOut.println(testDeque.removeLast()); }
            front = !front;
        }

    }

}
