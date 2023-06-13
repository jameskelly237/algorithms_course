import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] gridOpen;
    private boolean[] gridConnectedToTop;
    private boolean[] gridConnectedToBottom;
    private WeightedQuickUnionUF unionFind;
    private int numOpenSites;
    private int dimN;
    private boolean doesPercolate;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        gridOpen = new boolean[n*n];
        gridConnectedToBottom = new boolean[n*n];
        gridConnectedToTop = new boolean[n*n];
        unionFind = new WeightedQuickUnionUF(n*n);
        numOpenSites = 0;
        dimN = n;
        doesPercolate = false;

        for (int i = 0; i < n*n; i++)
        {
            gridOpen[i] = false;
            gridConnectedToBottom[i] = false;
            gridConnectedToTop[i] = false;
        }
    }
    
    private int siteIndex(int row, int col)
    {
        return (row-1) * dimN + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        boolean tmpTop = false;
        boolean tmpBottom = false;

        if (row < 1 || row > dimN || col < 1 || col > dimN)
        {
            throw new IllegalArgumentException("row and col must be between 1 and " + dimN);
        }
        if (!gridOpen[siteIndex(row, col)])
        {
            numOpenSites += 1;
            gridOpen[siteIndex(row, col)] = true;
            if (row == 1) tmpTop = true;
            if (row == dimN) tmpBottom = true;

            if (row > 1)
            {
                if (isOpen(row-1, col))
                {
                    tmpTop = tmpTop || gridConnectedToTop[unionFind.find(siteIndex(row-1, col))];
                    tmpBottom = tmpBottom || gridConnectedToBottom[unionFind.find(siteIndex(row-1, col))];
                    unionFind.union(siteIndex(row, col), siteIndex(row-1, col));
                }
            }
            if (row < dimN)
            {
                if (isOpen(row+1, col))
                {

                    tmpTop = tmpTop || gridConnectedToTop[unionFind.find(siteIndex(row+1, col))];
                    tmpBottom = tmpBottom || gridConnectedToBottom[unionFind.find(siteIndex(row+1, col))];
                    unionFind.union(siteIndex(row, col), siteIndex(row+1, col));
                }
            }
            if (col > 1)
            {
                if (isOpen(row, col-1))
                {
                    tmpTop = tmpTop || gridConnectedToTop[unionFind.find(siteIndex(row, col-1))];
                    tmpBottom = tmpBottom || gridConnectedToBottom[unionFind.find(siteIndex(row, col-1))];
                    unionFind.union(siteIndex(row, col), siteIndex(row, col-1));
                }
            }
            if (col < dimN)
            {
                if (isOpen(row, col+1))
                {
                    tmpTop = tmpTop || gridConnectedToTop[unionFind.find(siteIndex(row, col+1))];
                    tmpBottom = tmpBottom || gridConnectedToBottom[unionFind.find(siteIndex(row, col+1))];
                    unionFind.union(siteIndex(row, col), siteIndex(row, col+1));
                }
            }

            gridConnectedToBottom[unionFind.find(siteIndex(row, col))] = tmpBottom;
            gridConnectedToTop[unionFind.find(siteIndex(row, col))] = tmpTop;

            doesPercolate = doesPercolate || (tmpBottom && tmpTop);

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {        
        if (row < 1 || row > dimN || col < 1 || col > dimN)
        {
            throw new IllegalArgumentException("row and col must be between 1 and " + dimN);
        }
        return gridOpen[siteIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row < 1 || row > dimN || col < 1 || col > dimN)
        {
            throw new IllegalArgumentException("row and col must be between 1 and " + dimN);
        }
        return gridConnectedToTop[unionFind.find(siteIndex(row, col))];
    }
    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numOpenSites;
    }
    // does the system percolate?
    public boolean percolates()
    {
        return doesPercolate;
    }
    // test client (optional)
    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(Integer.parseInt(args[0]));
        for (int i = 1; i < args.length; i = i+2)
        {
            percolation.open(Integer.parseInt(args[i]), Integer.parseInt(args[i+1]));
        }
        StdOut.println(percolation.percolates());
        
    }
}