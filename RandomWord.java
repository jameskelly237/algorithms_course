import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {

        double i = 1;
        String chosenWord = "";

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (StdRandom.bernoulli(1/i)) { 
                chosenWord = word;
            }
            i = i + 1;
        }
        StdOut.println(chosenWord);
    }
}