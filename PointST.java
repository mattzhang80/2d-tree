import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeMap;

public class PointST<Value> {
    // creates Tree Map to represent a symbol table whose keys are 2D points
    private TreeMap<Point2D, Value> treeMap;

    // construct an empty symbol table of points
    public PointST() {
        treeMap = new TreeMap<>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return (treeMap.size() == 0);
    }

    // number of points
    public int size() {
        return treeMap.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null)
            throw new IllegalArgumentException("Point or value is null");
        treeMap.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point is null");
        return treeMap.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point is null");
        return treeMap.containsKey(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return treeMap.keySet();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle is null");
        Stack<Point2D> ptStack = new Stack<>();
        // loop to push points into stack if in rectangle
        for (Point2D pt : points()) {
            if (rect.contains(pt))
                ptStack.push(pt);
        }
        return ptStack;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point is null or ST is empty");
        if (isEmpty())
            return null;
        Point2D nearest = null;
        double distance = Double.POSITIVE_INFINITY;
        // loop to find nearest neighbor based on distance between two
        for (Point2D pt : points()) {
            double ptDist = pt.distanceSquaredTo(p);
            if (ptDist < distance) {
                nearest = pt;
                distance = ptDist;
            }
        }
        return nearest;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Point2D> testing = new PointST<Point2D>();
        if (testing.isEmpty())
            StdOut.println("isEmpty() working as intended");
        else
            StdOut.println("isEmpty() is bugged");
        Point2D point1 = new Point2D(0.5, 0.5);
        Point2D point2 = new Point2D(0.75, 0.75);
        testing.put(point1, point1);
        testing.put(point2, point2);
        if (!testing.isEmpty())
            StdOut.println("put() and isEmpty() working as intended");
        else
            StdOut.println("put() and/or isEmpty() is bugged");
        if (testing.size() == 2)
            StdOut.println("size() working as intended");
        else
            StdOut.println("size() is bugged");
        if (testing.contains(point2))
            StdOut.println("get() should output (0.75, 0.75): " + testing.get(point2));
        else
            StdOut.println("contains() is bugged");
        StdOut.println("points() should output [(0.5, 0.5), (0.75, 0.75)]: "
                               + testing.points());
        RectHV rect1 = new RectHV(0.6, 0.6, 0.9, 0.9);
        StdOut.println("rect() should output (0.75, 0.75): " + testing.range(rect1));
        Point2D point3 = new Point2D(0.8, 0.8);
        StdOut.println("nearest() should output (0.75, 0.75): "
                               + testing.nearest(point3));
    }
}
