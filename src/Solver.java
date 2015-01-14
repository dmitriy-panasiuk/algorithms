import java.util.Comparator;

public class Solver {
    private Stack<Board> solution = new Stack<Board>();
    private boolean isSolvable = false;

    private class BoardContainer {
        private Board board;
        private int moves;
        private BoardContainer prev;

        BoardContainer(Board board, int moves, BoardContainer prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }
    }

    private class BoardComparator implements Comparator<BoardContainer> {
        @Override
        public int compare(BoardContainer o1, BoardContainer o2) {
            int priority1 = o1.board.manhattan() + o1.moves;
            int priority2 = o2.board.manhattan() + o2.moves;

            if (priority1 < priority2) {
                return -1;
            }
            if (priority1 > priority2) {
                return 1;
            }
            return 0;
        }
    }

    public Solver(Board initial) {
        MinPQ<BoardContainer> initQueue = new MinPQ<BoardContainer>(new BoardComparator());
        MinPQ<BoardContainer> twinQueue = new MinPQ<BoardContainer>(new BoardComparator());

        initQueue.insert(new BoardContainer(initial, 0, null));
        twinQueue.insert(new BoardContainer(initial.twin(), 0, null));
        while (true) {
            BoardContainer init = initQueue.delMin();
            BoardContainer initTwin = twinQueue.delMin();

            if (init.board.isGoal()) {
                isSolvable = true;
                BoardContainer curr = init;
                while (curr != null) {
                    solution.push(curr.board);
                    curr = curr.prev;
                }
                break;
            }
            if (initTwin.board.isGoal()) {
                break;
            }
            for (Board board : init.board.neighbors()) {
                if (init.prev != null && board.equals(init.prev.board)) {
                    continue;
                }
                initQueue.insert(new BoardContainer(board, init.moves + 1, init));
            }
            for (Board board : initTwin.board.neighbors()) {
                if (initTwin.prev != null && board.equals(initTwin.prev.board)) {
                    continue;
                }
                twinQueue.insert(new BoardContainer(board, initTwin.moves + 1, initTwin));
            }
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return solution.size() - 1;
    }

    public Iterable<Board> solution() {
        if (isSolvable) {
            return solution;
        }
        return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
