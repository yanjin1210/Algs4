import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver {
    private Board initialBoard;
    private Board twinBoard;
    private int moves = 0;
    private MinPQ<Dist> distMinPQ = new MinPQ<>();
    private MinPQ<Dist> distMinPQTwin = new MinPQ<>();
    private HashSet<String> visited = new HashSet<>();
    private HashSet<String> twinVisited = new HashSet<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        initialBoard = initial;
        twinBoard = initial.twin();
    }

    private class Dist implements Comparable<Dist> {
        private int distance;
        private int steps;
        private Board bd;

        public Dist(Board bd, int steps, String solver) {
            this.bd = bd;
            this.steps = steps;
            if (solver.equals("h")) {
                distance = this.bd.hamming() + steps;
            } else if (solver.equals("m")) {
                distance = this.bd.manhattan() + steps;
            } else {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public int compareTo(Dist that) {
            return this.distance - that.distance;
        }

        public Board getBoard() {
            return bd;
        }

        public int getSteps() {
            return steps;
        }
    }

    public int addNeighbors(MinPQ<Dist> pq, HashSet<String> visited, String solver) {
        Dist hd = pq.delMin();
        Board curBoard = hd.getBoard();
        int st = hd.getSteps();
        if (curBoard.isGoal()) {
            System.out.print("Solved board " + st + "\n" + curBoard.toString());
            return st;
        }
        for (Board bd:hd.getBoard().neighbors()) {
            String bdString = bd.toString();
            System.out.print(st + ", " + bdString);
            if (!visited.contains(bdString)) {
                visited.add(bdString);
                pq.insert(new Dist(bd, st + 1, solver));
            }
        }
        return 0;
    }

    public int pqSolver(String solver) {
        int res1 = 0;
        int res2 = 0;
        while (res1 == 0 && res2 ==0) {
            res1 = addNeighbors(distMinPQ, visited, solver);
            res2 = addNeighbors(distMinPQTwin, twinVisited, solver);
        }
        System.out.println(res1 + ", " + res2);
        System.out.println(res1 > 0? res1:-1);
        return res1 > 0? res1:-1;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        moves = pqSolver("h");
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return new ArrayList<Board>();
    }

    // test client (see below)
    public static void main(String[] args){
        int[][] tiles = new int[][]{{8,1,3},{4,0,2},{7,6,5}};
        Board initial = new Board(tiles);
        Solver hmSolver = new Solver(initial);
        if (hmSolver.isSolvable()) {
            System.out.println("It takes minimal " + hmSolver.moves() + " steps to solve");
        } else {
            System.out.println("No solution.");
        }
    }
}