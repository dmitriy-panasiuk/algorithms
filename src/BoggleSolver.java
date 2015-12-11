import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieST;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class BoggleSolver {
    private TrieST<Integer> dict = new TrieST<>();
    private int boardDimension;

    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) {
            dict.put(word, score(word.length()));
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Queue<String> queue = new Queue<>();
        String s = "";
        this.boardDimension = board.cols();
        boolean[] marked = new boolean[boardDimension * boardDimension];

        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                dfs(board, marked, coordsToInt(i, j), s + board.getLetter(i, j), queue);
            }
        }

        return null;
    }

    private void dfs(BoggleBoard board, boolean[] marked, int v, String s, Queue<String> queue) {
        marked[v] = true;
        if (!iterableIsEmpty(dict.keysWithPrefix(s))) {
            for (Integer neighbour : neighbours(v)) {
                if (!marked[neighbour]) {
                    if (dict.contains(s)) {
                        queue.enqueue(s);
                    }
                    dfs(board, marked, neighbour, s + board.getLetter(neighbour / boardDimension, neighbour % boardDimension), queue);
                }
            }
        }

        marked[v] = false;
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

    private int coordsToInt(int i, int j) {
        return i * boardDimension + j;
    }

    private Iterable<Integer> neighbours(int index) {
        List<Integer> result = new ArrayList<>();

        int i = index / boardDimension, j = index % boardDimension;
        if (i > 0) {
            if (j > 0) {
                result.add(coordsToInt(i - 1, j - 1));
            }
            result.add(coordsToInt(i - 1, j));
            if (j < boardDimension - 1) {
                result.add(coordsToInt(i - 1, j + 1));
            }
        }
        if (j > 0) {
            result.add(coordsToInt(i, j - 1));
        }
        if (j < boardDimension - 1) {
            result.add(coordsToInt(i, j + 1));
        }
        if (i < boardDimension - 1) {
            if (j > 0) {
                result.add(coordsToInt(i + 1, j - 1));
            }
            result.add(coordsToInt(i + 1, j));
            if (j < boardDimension - 1) {
                result.add(coordsToInt(i + 1, j + 1));
            }
        }

        return result;
    }

    private boolean iterableIsEmpty(Iterable iterable) {
        for (Object i : iterable) return false;

        return true;
    }

    public static void main(String[] args) {
        In in = new In("test.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        //solver.getAllValidWords(new BoggleBoard());
        BoggleBoard b = new BoggleBoard();
        solver.getAllValidWords(b);
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
