import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] samplePs;
    private int numTrials;
    private double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }
        Percolation currentPercolation;
        samplePs = new double[trials];
        numTrials = trials;

        for (int i = 0; i < trials; i++)
        {
            currentPercolation = new Percolation(n);
            while (!currentPercolation.percolates())
            {
                currentPercolation.open(StdRandom.uniformInt(n)+1, StdRandom.uniformInt(n)+1);
            }
            samplePs[i] = (double) currentPercolation.numberOfOpenSites() / (n * n);
        }
        
    }
    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(samplePs);
    }
 
    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(samplePs);
    }
 
    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - (confidence95 * stddev() / Math.sqrt(numTrials));
    }
 
    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + (confidence95 * stddev() / Math.sqrt(numTrials));
    }
 
   // test client (see below)
   public static void main(String[] args)
   {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
   }

}