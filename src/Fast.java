import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        Point[] temp = new Point[n];
        int num = n;

        while (num > 0) {
            num--;
            Point tPoint = new Point(in.readInt(), in.readInt());
            points[num] = tPoint;
            temp[num] = tPoint;
        }
        Arrays.sort(points);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < n; i++) {
            Point current = points[i];
            Arrays.sort(temp, current.SLOPE_ORDER);
            double slope = points[i].slopeTo(temp[0]);
            int pointN = 1;
            for (int j = 1; j < n; j++) {
                double nextSlope = points[i].slopeTo(temp[j]);
                if (slope == nextSlope && j != (n - 1)) {
                    pointN++;
                    continue;
                }
                if (slope == nextSlope && j == (n - 1)) {
                    pointN++;
                    j++;
                }
                if (pointN >= 3) {
                    Arrays.sort(temp, j - pointN, j);
                    if (points[i].compareTo(temp[j - pointN]) < 0) {
                        StdOut.print(points[i]);
                        points[i].draw();
                        for (int k = j - pointN; k < j; k++) {
                            StdOut.print(" -> " + temp[k]);
                            temp[k].draw();
                        }
                        StdOut.println();
                        points[i].drawTo(temp[j - 1]);
                    }
                }
                pointN = 1;
                slope = nextSlope;
            }
        }
    }
}
