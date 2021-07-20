import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final int n;
    private int openSites = 0;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        grid = new boolean[n+1][n+1];
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // System.out.print("(" + row + ", " + col + ")");
        if (row < 1 || row > n || col < 1 || col > n) {
            // System.out.println("Index out of range: " + row + ", " + col);
            throw new IllegalArgumentException();
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites++;
            connect(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        if (!grid[row][col]) {
            return false;
        }
        return uf.find(0) == uf.find(row * n + col - n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(n * n + 1) == uf.find(0);
    }

    private void connect(int row, int col) {
        int idx = row * n + col - n;
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                uf.union(idx - n, idx);
            }
        } else {
            uf.union(idx, 0);
        }
        if (row < n) {
            if (isOpen(row+1, col)) {
                uf.union(idx, idx + n);
            }
        } else {
            uf.union(idx, n * n + 1);
        }
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                uf.union(idx - 1, idx);
            }
        }
        if (col < n) {
            if (isOpen(row, col + 1)) {
                uf.union(idx, idx + 1);
            }
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation pcl = new Percolation(5);
        int[] idx = new int[pcl.n * pcl.n];

        for (int i = 0; i < pcl.n * pcl.n; i++) {
            idx[i] = i + 1;
        }

        for (int i = idx.length; i > 0; i--) {
            int nextIdx = StdRandom.uniform(i);
            int temp = idx[i - 1];
            idx[i - 1] = idx[nextIdx];
            idx[nextIdx] = temp;
        }

        for (int i = 0; i < idx.length; i++) {
            int row = (idx[i] - 1) / pcl.n + 1;
            int col = idx[i] % pcl.n;
            if (col == 0) {
                col = pcl.n;
            }
            pcl.open(row, col);
            if (pcl.percolates()) {
                break;
            }
        }
    }
}