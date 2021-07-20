import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

public class Board {
    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }


    // string representation of this board
    public String toString() {
        String res = n + "\n";
        for (int[] row:tiles) {
            for (int i:row ) {
                res += i + " ";
            }
            res += "\n";
        }
        return res;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                } else if (tiles[i][j] != i * n + j + 1) {
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                } else {
                    int row = tiles[i][j] % n == 0? tiles[i][j] / n - 1:tiles[i][j]/n;
                    int col = tiles[i][j] % n == 0? n-1 : tiles[i][j] % n - 1;
                    manhattanDistance += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return toString().equals(y.toString());
    }

    private Board newBoard (int row1, int col1, int row2, int col2) {
        int[][] twinTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j< n; j++) {
                twinTiles[i][j] = tiles[i][j];
            }
        }
        twinTiles[row1][col1] = tiles[row2][col2];
        twinTiles[row2][col2] = tiles[row1][col1];

        return new Board(twinTiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int rowOfZero = 0;
        int colOfZero = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    rowOfZero = i;
                    colOfZero = j;
                    break;
                }
            }
        }

        List<Board> neighbor = new ArrayList<>();
        int curPos = 0;
        if (rowOfZero < n - 1) {
            neighbor.add(newBoard(rowOfZero, colOfZero, rowOfZero + 1, colOfZero));
        }
        if (rowOfZero > 0) {
            neighbor.add(newBoard(rowOfZero, colOfZero, rowOfZero - 1, colOfZero));
        }
        if (colOfZero < n - 1) {
            neighbor.add(newBoard(rowOfZero, colOfZero, rowOfZero, colOfZero + 1));
        }
        if (colOfZero > 0) {
            neighbor.add(newBoard(rowOfZero, colOfZero, rowOfZero, colOfZero - 1));
        }

        return neighbor;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int row1 = StdRandom.uniform(n);
        int col1 = StdRandom.uniform(n);
        while(tiles[row1][col1] == 0) {
            row1 = StdRandom.uniform(n);
            col1 = StdRandom.uniform(n);
        }
        int row2 = StdRandom.uniform(n);
        int col2 = StdRandom.uniform(n);
        while((row1 == row2 && col1 == col2) || tiles[row2][col2] == 0) {
            row2 = StdRandom.uniform(n);
            col2 = StdRandom.uniform(n);
        }
        return newBoard(row1, col1, row2, col2);
    }



    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][]{{8,1,3},{4,0,2},{7,6,5}};
        Board board = new Board(tiles);
        System.out.println("The size of the board is: " + board.dimension());
        System.out.print(board.toString());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        Object y = new Board(tiles);
        System.out.println("Two boards are equal: " + board.equals(y));
        System.out.println("Neighbors:");
        for (Board b:board.neighbors()) {
            System.out.print(b.toString());
        }
    }

}