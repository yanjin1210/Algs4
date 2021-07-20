import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String res = "";
        String tmp;
        int count = 0;
        while (!StdIn.isEmpty()) {
            count++;
            tmp = StdIn.readString();
            if (StdRandom.bernoulli(1 / (double) count)) {
                res = tmp;
            }
        }

        System.out.println(res);
    }
}
