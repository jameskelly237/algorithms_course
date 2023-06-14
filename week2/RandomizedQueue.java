import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        queue = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() { return size == 0; }

    // return the number of items on the randomized queue
    public int size() { return size; }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null) { throw new IllegalArgumentException("cannot enqueue null item"); }
        if (size == queue.length) { resize(size * 2); }
        queue[size] = item;
        size++;
    }

    private void resize(int newSize)
    {
        Item[] newArray;
        newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++)
        {
            newArray[i] = queue[i];
        }
        queue = newArray;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (size == 0) { throw new java.util.NoSuchElementException("tried to dequeue from empty queue"); }
        int i;
        i = StdRandom.uniformInt(size);
        Item selected;
        selected = queue[i];
        size--;
        queue[i] = queue[size];
        queue[size] = null;
        if (size < queue.length / 4) { resize(queue.length / 2); }
        return selected;
    }

    // return a random item (but do not remove it)
    public Item sample() 
    {
        if (size == 0) { throw new java.util.NoSuchElementException("tried to sample from empty queue"); }
        return queue[StdRandom.uniformInt(size)]; 
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new RandQueueIterator(); }

    private class RandQueueIterator implements Iterator<Item>
    {
        private int[] order;
        private int current = 0;

        private RandQueueIterator()
        {
            order = new int[size];
            for (int i = 0; i < size; i++)
            {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext()
        {
            return current < size;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        public Item next()
        {
            if (current == size) { throw new java.util.NoSuchElementException(); }
            Item item = queue[order[current]];
            current++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<String> testQueue = new RandomizedQueue<String>();
        String s = "";

        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            testQueue.enqueue(s);
        }

        StdOut.println("-----------------------------");
        StdOut.println("Iterating randomly over all contents");
        StdOut.print("There are " + testQueue.size() + " items\n");

        for (String str: testQueue)
        {
            StdOut.println(str);
        }

        StdOut.println("-----------------------------");
        StdOut.println("Sampling N components randomly (might repeat)");
        StdOut.print("There are " + testQueue.size() + " items\n");

        for (int i = 0; i < testQueue.size(); i++)
        {
            StdOut.println(testQueue.sample());
        }
        StdOut.println("-----------------------------");
        StdOut.println("Removing components randomly");

        while (!testQueue.isEmpty())
        {
            StdOut.println(testQueue.dequeue()); 
        }

        if (testQueue.isEmpty()) { StdOut.println("successfully emptied queue"); }
    }
}