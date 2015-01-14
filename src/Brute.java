import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        int num = n;

        while (num > 0) {
            num--;
            points[num] = new Point(in.readInt(), in.readInt());
        }
        Arrays.sort(points);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        double slopeij = points[i].slopeTo(points[j]);
                        double slopeik = points[i].slopeTo(points[k]);
                        double slopeil = points[i].slopeTo(points[l]);
                        boolean b1 = slopeij == slopeik;
                        boolean b2 = slopeij == slopeil;
                        if (b1 && b2) {
                            StdOut.printf("%s -> %s -> %s -> %s", points[i], points[j], points[k], points[l]);
                            System.out.println();
                            points[i].draw();
                            points[j].draw();
                            points[k].draw();
                            points[l].draw();
                            points[i].drawTo(points[l]);
                        }
                    }
                }
            }
        }
    }
}
