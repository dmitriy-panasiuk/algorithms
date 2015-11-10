import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            if (slopeTo(o1) < slopeTo(o2)) {
                return -1;
            }
            if (slopeTo(o1) == slopeTo(o2)) {
                return 0;
            }
            return 1;
        }
    };

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        double num = that.x - this.x;
        double denom = that.y - this.y;

        if (num == 0 && denom == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (num == 0) {
            return Double.POSITIVE_INFINITY;
        }
        if (denom == 0) {
            return 0.0;
        }

        return denom / num;
    }

    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        }
        if (this.y == that.y && this.x < that.x) {
            return -1;
        }
        if (this.y == that.y && this.x == that.x) {
            return 0;
        }
        return 1;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {

    }
}
