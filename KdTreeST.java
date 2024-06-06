import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
 
public class KdTreeST<Value> {
    private Node root; // Creates variable for root node
    private int pointCounter; // Tracks size/number of points
    private double inf = Double.POSITIVE_INFINITY; // infinity
 
    // Node representation adapted from K-d trees assignment
    // checklist (course material)
    private class Node {
        private Point2D p;      // the point
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private Value val;      // value
        private RectHV rect;    // rectangle
 
        // constructor for a node object
        public Node(Point2D p, Node lb, Node rt, Value val, RectHV rect) {
            this.p = p;
            this.lb = lb;
            this.rt = rt;
            this.rect = rect;
            this.val = val;
        }
    }
 
    // construct an empty symbol table of points
    public KdTreeST() {
        pointCounter = 0;
        root = null;
    }
 
    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }
 
    // number of points
    public int size() {
        return pointCounter;
    }
 
    // associate the value val with point p, exception with null point
    public void put(Point2D p, Value val) {
        RectHV rect = new RectHV(-inf, -inf, inf, inf);
        if (p == null || val == null)
            throw new IllegalArgumentException("Calls put() with a null point");
        root = put(root, p, true, val, rect);
    }
 
    // private helper method for put 
    private Node put(Node node, Point2D p, boolean vertical, Value val, RectHV rect) {
        // conditional if node is null, corner case
        if (node == null) {
            pointCounter++;
            return new Node(p, null, null, val, rect);
        }
        // variables to represent x y values for point and node
        RectHV rectangle;
        double nodex = node.p.x();
        double nodey = node.p.y();
        double px = p.x();
        double py = p.y();
 
        if (nodex == px && nodey == py) {
            node.val = val;
            return node;
        }
 
        // conditional put if node is vertical
        if (vertical) {
            if (px >= nodex) {
                rectangle = new RectHV(nodex, -inf, inf, inf);
                node.rt = put(node.rt, p, false, val, rectangle);
            }
            else {
                rectangle = new RectHV(-inf, -inf, nodex, inf);
                node.lb = put(node.lb, p, false, val, rectangle);
            }
        }
        // conditional put if node is horizontal
        else {
            if (py >= nodey) {
                rectangle = new RectHV(-inf, nodey, inf, inf);
                node.rt = put(node.rt, p, true, val, rectangle);
            }
            else {
                rectangle = new RectHV(-inf, -inf, inf, nodey);
                node.lb = put(node.lb, p, true, val, rectangle);
            }
        }
        return node;
    }
 
    // value associated with point p, exception w/null point
    public Value get(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point is null");
        return get(root, p, true);
    }
 
    // private helper method for get
    private Value get(Node node, Point2D p, boolean vert) {
        // conditional get if node is null or point equal to node point
        if (node == null)
            return null;
        if (p.equals(node.p))
            return node.val;
        // conditional for vertical line
        if (vert) {
            if (p.x() < node.p.x())
                return get(node.lb, p, false);
            else
                return get(node.rt, p, false);
        }
        // conditional for horizontal line
        else {
            if (p.y() < node.p.y())
                return get(node.lb, p, true);
            else
                return get(node.rt, p, true);
        }
    }
 
    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return get(p) != null;
    }
 
    // all points in the symbol table
    public Iterable<Point2D> points() {
        // initialize queues to track nodes and points
        Queue<Node> queue = new Queue<>();
        Queue<Point2D> keyQueue = new Queue<>();
        queue.enqueue(root);
        // dequeue nodes from node queue and enqueue back to key and Q respectively
        while (!queue.isEmpty()) {
            Node node = queue.dequeue();
            if (node == null)
                continue;
            queue.enqueue(node.lb);
            queue.enqueue(node.rt);
            keyQueue.enqueue(node.p);
        }
        return keyQueue;
    }
 
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle is null");
        Stack<Point2D> rangeStack = new Stack<Point2D>();
        // uses helper method to find and add to the range
        insert(root, rect, rangeStack);
        return rangeStack;
    }
 
    // private helper method to add to range of points inside
    private void insert(Node node, RectHV rect, Stack<Point2D> rangeStack) {
        // conditional that adds points to range stack if boundary or in rectangle
        if (node != null && rect.intersects(node.rect)) {
            if (rect.contains(node.p)) {
                rangeStack.push(node.p);
            }
            insert(node.lb, rect, rangeStack);
            insert(node.rt, rect, rangeStack);
        }
    }
 
    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Rectangle is null");
        if (isEmpty())
            return null;
        else {
            return nearest(root, p, null, true);
        }
    }
 
    // returns nearest neighbor to query point
    private Point2D nearest(Node node, Point2D p, Point2D near, boolean vert) {
        // when code reaches null node, this is pruning
        if (node == null)
            return near;
        if (near == null)
            near = node.p;
        else {
            if (near.distanceSquaredTo(p) < node.rect.distanceSquaredTo(p)) {
                return near;
            }
            if (node.p.distanceSquaredTo(p) < near.distanceSquaredTo(p))
                near = node.p;
        }
 
        double compare = compare(p, node.p, vert);
        // recursion to test left/top and right/bottom subtrees
        if (compare < 0) {
            // added if statements before each recursion per lab TA reccomendation 
            if (near.distanceSquaredTo(p) < node.rect.distanceSquaredTo(p)) {
                return near;
            }
            if (node.p.distanceSquaredTo(p) < near.distanceSquaredTo(p))
                near = node.p;
 
            near = nearest(node.lb, p, near, !vert);
            if (near.distanceSquaredTo(p) < node.rect.distanceSquaredTo(p)) {
                return near;
            }
            if (node.p.distanceSquaredTo(p) < near.distanceSquaredTo(p))
                near = node.p;
            near = nearest(node.rt, p, near, !vert);
        }
        else {
            if (near.distanceSquaredTo(p) < node.rect.distanceSquaredTo(p)) {
                return near;
            }
            if (node.p.distanceSquaredTo(p) < near.distanceSquaredTo(p))
                near = node.p;
            near = nearest(node.rt, p, near, !vert);
            if (near.distanceSquaredTo(p) < node.rect.distanceSquaredTo(p)) {
                return near;
            }
            if (node.p.distanceSquaredTo(p) < near.distanceSquaredTo(p))
                near = node.p;
            near = nearest(node.lb, p, near, !vert);
        }
        return near;
    }
 
    // private helper method to compare x or y value of query and node point
    private double compare(Point2D p, Point2D nodeP, boolean vert) {
        if (vert) {
            return p.x() - nodeP.x();
        }
        else {
            return p.y() - nodeP.y();
        }
    }
 
    // unit testing (required)
    public static void main(String[] args) {
        KdTreeST<String> testing = new KdTreeST<>();
        if (testing.isEmpty())
            StdOut.println("PointST is Empty");
        else
            StdOut.println("isEmpty() is bugged");
        Point2D point1 = new Point2D(0.5, 0.5);
        Point2D point2 = new Point2D(0.75, 0.75);
        testing.put(point1, "value1");
        StdOut.println("Points after one put: " + testing.points());
        StdOut.println("Size after one put: " + testing.size());
        StdOut.println("Emptiness after one put: " + testing.isEmpty());
        StdOut.println("nearest after one put: " + testing.nearest(point1));
        testing.put(point2, "value2");
        StdOut.println("--------- after adding another point ---------");
        if (!testing.isEmpty())
            StdOut.println("PointST is not Empty");
        else
            StdOut.println("isEmpty() and/or put() is bugged");
        StdOut.println("Size is " + testing.size());
        if (testing.contains(point2))
            StdOut.println("Second point is " + testing.get(point2));
        StdOut.println("All points: " + testing.points());
        RectHV rect1 = new RectHV(0.6, 0.6, 0.9, 0.9);
        StdOut.println("test rectangle contains " + testing.range(rect1));
 
        Point2D point3 = new Point2D(0.8, 0.8);
        testing.put(point3, "value3");
        StdOut.println("nearest point to (0.8, 0.8) is " + testing.nearest(point3));
 
        StdOut.println("--------- after adding another point 3 ---------");
        StdOut.println("Points after one put: " + testing.points());
        StdOut.println("Size after one put: " + testing.size());
        StdOut.println("Emptiness after one put: " + testing.isEmpty());
 
        StdOut.println("--------- now we insert general pts ---------");
        KdTreeST<String> testing2 = new KdTreeST<>();
        Point2D genPoint1 = new Point2D(0.5, 0.5);
        testing2.put(genPoint1, "value1");
        Point2D genPoint2 = new Point2D(0.5, 0.5);
        testing2.put(genPoint2, "value2");
        StdOut.println("Points after general put: " + testing2.points());
        StdOut.println("Size after general put: " + testing2.size());
        StdOut.println("Emptiness after general put: " + testing2.isEmpty());
        StdOut.println("Value after general put: " + testing2.root.val);
 
        StdOut.println("--------- now we test sequence of nodes called in tree -----");
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);
        KdTreeST<String> testing3 = new KdTreeST<>();
        testing3.put(p1, "A");
        testing3.put(p2, "B");
        testing3.put(p3, "C");
        testing3.put(p4, "D");
        testing3.put(p5, "E");
        Point2D query = new Point2D(0.69, 0.96);
        StdOut.println("Nearest point:" + testing3.nearest(query));
 
        Point2D pp1 = new Point2D(0.372, 0.497);
        Point2D pp2 = new Point2D(0.564, 0.413);
        Point2D pp3 = new Point2D(0.226, 0.577);
        Point2D pp4 = new Point2D(0.144, 0.179);
        Point2D pp5 = new Point2D(0.083, 0.51);
        Point2D pp6 = new Point2D(0.32, 0.708);
        Point2D pp7 = new Point2D(0.417, 0.362);
        Point2D pp8 = new Point2D(0.862, 0.825);
        Point2D pp9 = new Point2D(0.785, 0.725);
        Point2D pp10 = new Point2D(0.499, 0.208);
 
        StdOut.println("------another sequence test-----");
        KdTreeST<String> test4 = new KdTreeST<>();
        test4.put(pp1, "A");
        test4.put(pp2, "B");
        test4.put(pp3, "C");
        test4.put(pp4, "D");
        test4.put(pp5, "E");
        test4.put(pp6, "F");
        test4.put(pp7, "G");
        test4.put(pp8, "H");
        test4.put(pp9, "I");
        test4.put(pp10, "J");
        Point2D qu = new Point2D(0.6, 0.53);
        StdOut.println("Nearest point:" + test4.nearest(qu));
    } 
}
