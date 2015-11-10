import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] blocks;
    private int dimension;

    public Board(int[][] blocks) {
        this.dimension = blocks.length;
        this.blocks = arraycopy(blocks);
    }

    public int dimension() {
        return this.dimension;
    }

    public int hamming() {
        int hamming = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                if (blocks[i][j] != hammingForBlock(i, j)) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    private int hammingForBlock(int i, int j) {
        return i * dimension + j + 1;
    }

    public int manhattan() {
        int manhattan = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                manhattan += manhattanForBlock(i, j);
            }
        }

        return manhattan;
    }

    private int manhattanForBlock(int i, int j) {
        int realI = (blocks[i][j] - 1) / dimension;
        int realJ = (blocks[i][j] - 1) % dimension;

        return Math.abs(realI - i) + Math.abs(realJ - j);
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] newBlocks = arraycopy(blocks);
        outer:
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension - 1; j++) {
                if (newBlocks[i][j] == 0 || newBlocks[i][j + 1] == 0) {
                    continue;
                }
                int temp = newBlocks[i][j];
                newBlocks[i][j] = newBlocks[i][j + 1];
                newBlocks[i][j + 1] = temp;
                break outer;
            }

        return new Board(newBlocks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (dimension != board.dimension) return false;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[i][j] != board.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        int emptyI = 0;
        int emptyJ = 0;
        List<Board> neighbors = new ArrayList<Board>();

        outer:
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    emptyI = i;
                    emptyJ = j;
                    break outer;
                }
            }
        }
        if (emptyI > 0) {
            int[][] newBlocks = arraycopy(blocks);
            int temp = newBlocks[emptyI][emptyJ];
            newBlocks[emptyI][emptyJ] = newBlocks[emptyI - 1][emptyJ];
            newBlocks[emptyI - 1][emptyJ] = temp;
            neighbors.add(new Board(newBlocks));
        }
        if (emptyI < dimension - 1) {
            int[][] newBlocks = arraycopy(blocks);
            int temp = newBlocks[emptyI][emptyJ];
            newBlocks[emptyI][emptyJ] = newBlocks[emptyI + 1][emptyJ];
            newBlocks[emptyI + 1][emptyJ] = temp;
            neighbors.add(new Board(newBlocks));
        }
        if (emptyJ > 0) {
            int[][] newBlocks = arraycopy(blocks);
            int temp = newBlocks[emptyI][emptyJ];
            newBlocks[emptyI][emptyJ] = newBlocks[emptyI][emptyJ - 1];
            newBlocks[emptyI][emptyJ - 1] = temp;
            neighbors.add(new Board(newBlocks));
        }
        if (emptyJ < dimension - 1) {
            int[][] newBlocks = arraycopy(blocks);
            int temp = newBlocks[emptyI][emptyJ];
            newBlocks[emptyI][emptyJ] = newBlocks[emptyI][emptyJ + 1];
            newBlocks[emptyI][emptyJ + 1] = temp;
            neighbors.add(new Board(newBlocks));
        }

        return neighbors;
    }

    private int[][] arraycopy(int[][] array) {
        int[][] result = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                result[i][j] = array[i][j];
            }
        }

        return result;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension).append(System.lineSeparator());
        for (int i = 0; i < dimension; i++) {
            builder.append(blocks[i][0]);
            for (int j = 1; j < dimension; j++) {
                builder.append(' ').append(blocks[i][j]);
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}
