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
        // creates a few 2D points
        Point2D pt1 = new Point2D(0, 0);
        Point2D pt2 = new Point2D(1, 0);
        Point2D pt3 = new Point2D(0, 1);
        Point2D pt4 = new Point2D(2, 2);
 
        // creates two symbol tables
        PointST<String> st1 = new PointST<String>();
        PointST<Integer> st2 = new PointST<Integer>();
 
        // creates two rectangles
        RectHV rect1 = new RectHV(-1, -1, 4, 5);
        RectHV rect2 = new RectHV(1, 1, 1, 1);
 
        // puts points and keys into the ST
        st1.put(pt1, "value1");
        st1.put(pt2, "value2");
        st1.put(pt3, "value3");
        st1.put(pt4, "value4");
 
        // tests methods by calling them, should match inquiry
        StdOut.println("SHOULD RETURN FALSE: " + st1.isEmpty());
        StdOut.println("SHOULD RETURN TRUE: " + st2.isEmpty());
 
        StdOut.println("SHOULD RETURN 4: " + st1.size());
        StdOut.println("SHOULD RETURN 0: " + st2.size());
 
        StdOut.println("SHOULD RETURN value1: " + st1.get(pt1));
        StdOut.println("SHOULD RETURN value4: " + st1.get(pt4));
 
        Point2D pt10 = new Point2D(100, 100);
 
        StdOut.println("SHOULD RETURN TRUE: " + st1.contains(pt1));
        StdOut.println("SHOULD RETURN FALSE: " + st1.contains(pt10));
 
        StdOut.println("SHOULD RETURN [(0.0, 0.0), (1.0, 0.0), (0.0, 1.0), "
                               + "(2.0, 2.0)]: " + st1.points());
 
        StdOut.println(
                "SHOULD RETURN (2.0, 2.0) (0.0, 1.0) (1.0, 0.0) (0.0, 0.0): "
                        + st1.range(rect1));
 
        StdOut.println(
                "SHOULD RETURN (2.0, 2.0) (0.0, 1.0) (1.0, 0.0) (0.0, 0.0) : "
                        + st1.range(rect1));
        StdOut.println("SHOULD RETURN NOTHING: " + st2.range(rect2));
 
        StdOut.println("SHOULD RETURN (1.0, 0.0): " + st1.nearest(pt1));
    } 
}
