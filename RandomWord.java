import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {

        float i = 1;
        String chosen_word = "";

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if(StdRandom.bernoulli(1/i)) { 
                chosen_word = word;
            }
            i = i + 1;
        }
        StdOut.println(chosen_word);
    }
}