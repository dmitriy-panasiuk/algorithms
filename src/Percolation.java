public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] sites;
    private int N;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        sites = new boolean[N * N];
        this.N = N;
    }

    public void open(int i, int j) {
        isValidCoords(i, j);
        sites[coordsToIndex(i, j)] = true;

        if (i > 1 && sites[coordsToIndex(i - 1, j)]) {
            uf.union(coordsToIndex(i, j), coordsToIndex(i - 1, j));
        }
        if (i < N && sites[coordsToIndex(i + 1, j)]) {
            uf.union(coordsToIndex(i, j), coordsToIndex(i + 1, j));
        }
        if (j > 1 && sites[coordsToIndex(i, j - 1)]) {
            uf.union(coordsToIndex(i, j), coordsToIndex(i, j - 1));
        }
        if (j < N && sites[coordsToIndex(i, j + 1)]) {
            uf.union(coordsToIndex(i, j), coordsToIndex(i, j + 1));
        }
        if (i == 1) {
            uf.union(coordsToIndex(i, j), N * N);
        }
        if (i == N) {
            uf.union(coordsToIndex(i, j), N * N + 1);
        }
    }

    public boolean isOpen(int i, int j) {
        isValidCoords(i, j);

        return sites[coordsToIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        isValidCoords(i, j);

        return isOpen(i, j) && uf.connected(coordsToIndex(i, j), N * N);
    }

    public boolean percolates() {
        return uf.connected(N * N, N * N + 1);
    }

    private int coordsToIndex(int i, int j) {
        return N * (i - 1) + (j - 1);
    }

    private void isValidCoords(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {

    }
}
