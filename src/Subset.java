import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String s;

        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }
        while (k > 0) {
            StdOut.println(q.dequeue());
            k--;
        }
    }
}
