public class PercolationStats {
    private int N;
    private int T;
    private double sampleMean;
    private double stdDev;

    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.T = T;
        init();
    }

    public double mean() {
        return this.sampleMean;
    }

    public double stddev() {
        return this.stdDev;
    }

    public double confidenceLo() {
        return this.mean() - ((1.96 * this.stddev() / Math.sqrt(this.T)));
    }

    public double confidenceHi() {
        return this.mean() + ((1.96 * this.stddev() / Math.sqrt(this.T)));
    }

    private void init() {
        double[] means = new double[T];

        for (int index = 0; index < T; index++) {
            Percolation percolation = new Percolation(N);
            int i, j;
            int n = 0;

            while (!percolation.percolates()) {
                i = StdRandom.uniform(1, N + 1);
                j = StdRandom.uniform(1, N + 1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    ++n;
                }
            }
            means[index] = (double) n / (N * N);
        }

        this.sampleMean = StdStats.mean(means);
        this.stdDev = StdStats.stddev(means);
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        StdOut.printf("%-23s = %.16f\n", "mean", stats.mean());
        StdOut.printf("%-23s = %.16f\n", "stddev", stats.stddev());
        StdOut.printf("%-23s = %.16f, %.16f\n", "95% confidence interval",
                stats.confidenceLo(), stats.confidenceHi());

        byte c = 3;
        c += 128;
    }
}
