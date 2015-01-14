import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<Point2D>();

        for (Point2D point : points) {
            if (rect.contains(point)) {
                list.add(point);
            }
        }

        return list;
    }

    public Point2D nearest(Point2D p) {
        double minDistance = 0.0;
        Point2D nearest = null;

        for (Point2D point : points) {
            if (nearest == null || p.distanceTo(point) < minDistance) {
                minDistance = p.distanceTo(point);
                nearest = point;
            }
        }

        return nearest;
    }

    public static void main(String[] args) {

    }
}
