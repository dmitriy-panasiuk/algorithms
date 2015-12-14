import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;

import java.util.List;
import java.util.ArrayList;

public class BoggleSolver {
    private TrieDP dict = new TrieDP();
    private List<List<Integer>> neighbours;
    private int boardDimensionRows;
    private int boardDimensionCols;

    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) {
            dict.put(word);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> queue = new SET<>();
        String s = "";
        this.boardDimensionRows = board.rows();
        this.boardDimensionCols = board.cols();
        this.neighbours = new ArrayList<>(boardDimensionRows * boardDimensionCols);
        boolean[] marked = new boolean[boardDimensionRows * boardDimensionCols];

        precomputeNeighbours();

        for (int i = 0; i < boardDimensionRows; i++) {
            for (int j = 0; j < boardDimensionCols; j++) {
                String letter = "" + board.getLetter(i, j);
                if (letter.equals("Q")) {
                    letter += "U";
                }
                dfs(board, marked, coordsToInt(i, j), s + letter, queue);
            }
        }

        return queue;
    }

    private void dfs(BoggleBoard board, boolean[] marked, int v, String s, SET<String> queue) {
        marked[v] = true;
        if (dict.hasKeysWithPrefix(s)) {
            if (s.length() > 2 && dict.contains(s)) {
                queue.add(s);
            }
            for (int neighbour : neighbours.get(v)) {
                if (!marked[neighbour]) {
                    String letter = "" + board.getLetter(neighbour / boardDimensionCols, neighbour % boardDimensionCols);
                    if (letter.equals("Q")) {
                        letter += "U";
                    }
                    dfs(board, marked, neighbour, s + letter, queue);
                }
            }
        }

        marked[v] = false;
    }

    public int scoreOf(String word) {
        if (dict.contains(word)) {
            return score(word.length());
        }
        return 0;
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
        return i * boardDimensionCols + j;
    }

    private void precomputeNeighbours() {
        for (int index = 0; index < boardDimensionRows * boardDimensionCols; index++) {
            this.neighbours.add(index, neighbours(index));
        }
    }

    private List<Integer> neighbours(int index) {
        List<Integer> result = new ArrayList<>();

        int i = index / boardDimensionCols, j = index % boardDimensionCols;
        if (i > 0) {
            if (j > 0) {
                result.add(coordsToInt(i - 1, j - 1));
            }
            result.add(coordsToInt(i - 1, j));
            if (j < boardDimensionCols - 1) {
                result.add(coordsToInt(i - 1, j + 1));
            }
        }
        if (j > 0) {
            result.add(coordsToInt(i, j - 1));
        }
        if (j < boardDimensionCols - 1) {
            result.add(coordsToInt(i, j + 1));
        }
        if (i < boardDimensionRows - 1) {
            if (j > 0) {
                result.add(coordsToInt(i + 1, j - 1));
            }
            result.add(coordsToInt(i + 1, j));
            if (j < boardDimensionCols - 1) {
                result.add(coordsToInt(i + 1, j + 1));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        In in = new In("dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("board-antidisestablishmentarianisms.txt");
        System.out.println(board);
        System.out.println(board.cols());
        System.out.println(board.rows());
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

class TrieDP {
    private static final int R = 26;
    private Node root = new Node();

    public void put(String key) {
        root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.value = true;
            return x;
        }
        char c = (char) (key.charAt(d) - 'A');
        x.next[c] = put(x.next[c], key, d + 1);

        return x;
    }

    public boolean contains(String key) {
        return get(root, key, 0);
    }

    private boolean get(Node x, String key, int d) {
        if (x == null) return false;
        if (d == key.length()) return x.value;
        char c = (char) (key.charAt(d) - 'A');
        return get(x.next[c], key, d + 1);
    }

    private static class Node {
        private boolean value;
        private Node[] next = new Node[R];
    }

    public boolean hasKeysWithPrefix(String prefix) {
        return hasKeysWithPrefix(root, prefix, 0);
    }

    private boolean hasKeysWithPrefix(Node x, String prefix, int d) {
        if (x == null) return false;
        if (d == prefix.length()) return true;
        char c = (char) (prefix.charAt(d) - 'A');
        return hasKeysWithPrefix(x.next[c], prefix, d + 1);
    }
}
