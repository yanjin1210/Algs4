import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] percentage;
    private final double CONFIDENCE_95 = 1.96D;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        percentage = new double[trials];
        int[] idx = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            idx[i] = i + 1;
        }
        for (int i = 0; i < trials; i++) {
            Percolation pcl = new Percolation(n);
            StdRandom.shuffle(idx);
            for (int j = 0; j < idx.length; j++) {
                int row = (idx[j] - 1) / n + 1;
                int col = idx[j] % n;
                if (col == 0) {
                    col = n;
                }
                pcl.open(row, col);
                if (pcl.percolates()) {
                    percentage[i] = (double) pcl.numberOfOpenSites() / ( n * n);
                    break;
                }
            }
        }
    }

        // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percentage);
    }

        // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percentage);
    }

        // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(percentage.length);
    }

        // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(percentage.length);
    }

        // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        System.out.println("Mean\t\t\t\t\t= " + ps.mean());
        System.out.println("Stddev\t\t\t\t\t= " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
