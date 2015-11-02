import edu.princeton.cs.algs4.Point2D;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size = 0;

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (root == null) {
            root = new Node(p);
            size++;
        } else {
            if (insert(root, p, true)) {
                size++;
            }
        }
    }

    private boolean insert(Node node, Point2D p, boolean vertical) {
        double f, s;
        if (node.point.equals(p)) {
            return false;
        }
        if (vertical) {
            f = p.x();
            s = node.point.x();
        } else {
            f = p.y();
            s = node.point.y();
        }
        if (f < s) {
            if (node.right == null) {
                node.right = new Node(p);
                return true;
            }
            return insert(node.right, p, !vertical);
        } else {
            if (node.left == null) {
                node.left = new Node(p);
                return true;
            }
            return insert(node.left, p, !vertical);
        }
    }

    public boolean contains(Point2D p) {
        if (root == null) {
            return false;
        }
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean vertical) {
        double f, s;

        if (p.equals(node.point)) {
            return true;
        }
        if (vertical) {
            f = p.x();
            s = node.point.x();
        } else {
            f = p.y();
            s = node.point.y();
        }
        if (f < s) {
            if (node.right == null) {
                return false;
            }
            return contains(node.right, p, !vertical);
        } else {
            if (node.left == null) {
                return false;
            }
            return contains(node.left, p, !vertical);
        }
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) {
            return;
        }
        node.point.draw();
        draw(node.right);
        draw(node.left);
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> points = new ArrayList<Point2D>();
        range(root, rect, points, true);

        return points;
    }

    private void range(Node node, RectHV rect, List<Point2D> points, boolean vertical) {
        double f, s1, s2;

        if (node == null) {
            return;
        }
        if (vertical) {
            f = node.point.x();
            s1 = rect.xmin();
            s2 = rect.xmax();
        } else {
            f = node.point.y();
            s1 = rect.ymin();
            s2 = rect.ymax();
        }
        if (rect.contains(node.point)) {
            points.add(node.point);
            range(node.right, rect, points, !vertical);
            range(node.left, rect, points, !vertical);
            return;
        }
        if (f >= s1) {
            range(node.right, rect, points, !vertical);
        }
        if (f <= s2) {
            range(node.left, rect, points, !vertical);
        }
    }

    public Point2D nearest(Point2D p) {
        if (root == null) {
            return null;
        }
        return nearest(root, p, root.point, true);
    }

    private Point2D nearest(Node node, Point2D p, Point2D champion, boolean vertical) {
        double d1, d2, f, s;
        Point2D newChampion2 = champion;

        if (node == null) {
            return newChampion2;
        }
        d1 = newChampion2.distanceTo(p);
        d2 = node.point.distanceTo(p);
        if (vertical) {
            f = p.x();
            s = node.point.x();
        } else {
            f = p.y();
            s = node.point.y();
        }
        if (d1 > d2) {
            newChampion2 = node.point;
        }
        if (f < s) {
            Point2D newChampion = nearest(node.right, p, newChampion2, !vertical);
            double f1 = newChampion.distanceTo(p);
            double s1;
            if (vertical) s1 = Math.abs(p.x() - node.point.x());
            else s1 = Math.abs(p.y() - node.point.y());
            if (f1 > s1) {
                return nearest(node.left, p, newChampion, !vertical);
            } else {
                return newChampion;
            }
        } else {
            Point2D newChampion = nearest(node.left, p, newChampion2, !vertical);
            double f1 = newChampion.distanceTo(p);
            double s1;
            if (vertical) s1 = Math.abs(p.x() - node.point.x());
            else s1 = Math.abs(p.y() - node.point.y());
            if (f1 > s1) {
                return nearest(node.right, p, newChampion, !vertical);
            } else {
                return newChampion;
            }
        }
    }

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;

        private Node(Point2D p) {
            this.point = p;
        }

        @Override
        public String toString() {
            return point.toString();
        }
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.2, 0.6));
        tree.insert(new Point2D(0.8, 0.1));
        tree.insert(new Point2D(0.3, 0.3));
        tree.insert(new Point2D(0.6, 0.7));
        tree.insert(new Point2D(0.23, 0.71));
        tree.insert(new Point2D(0.65, 0.78));
        for (Point2D p : tree.range(new RectHV(0.2, 0.2, 0.65, 0.78))) {
            System.out.println(p);
        }
    }
}
