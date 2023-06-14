import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        double i = 1;
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (i <= k)
            {
                rQueue.enqueue(word);
            }
            else 
            {
                // StdOut.println("queueing with probability" + (k / (i+1)));
                if (StdRandom.bernoulli(k / i))
                {
                    rQueue.dequeue();
                    rQueue.enqueue(word);
                }
            }
            i = i + 1;
        }
        
        for (String str: rQueue) { StdOut.println(str); }

    }
}