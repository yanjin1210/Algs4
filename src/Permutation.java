import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        int count = 0;
        for (String s : rq) {
            if (count < k) {
                count++;
                System.out.println(s);
            } else {
                break;
            }
        }
    }
}
