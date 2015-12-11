import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieST;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BoggleSolver {
    TrieST<Integer> dict = new TrieST<>();

    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) {
            dict.put(word, score(word.length()));
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        throw new NotImplementedException();
    }

    public int scoreOf(String word) {
        Integer score = dict.get(word);
        if (score == null) return 0;
        return score;
    }

    private int score(int wordLength) {
        assert wordLength > 0;
        if (wordLength < 3) return 0;
        if (wordLength == 3 || wordLength == 4) return 1;
        if (wordLength == 5) return 2;
        if (wordLength == 6) return 3;
        if (wordLength == 7) return 5;
        return 11;
    }

    public static void main(String[] args) {
        In in = new In("test.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        System.out.println(solver.scoreOf("ABL"));
        /*BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);*/
    }
}
