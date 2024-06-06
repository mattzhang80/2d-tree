K-d Trees


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */

The Node data type we used to implement the 2d-tree data structure includes Point2D,
Node, Value, and RectHV data types. The Point2D data type stores the Node's corresponding
location on the 2D plane. The 2 Node data types store the references to the subtrees to the
immediate right/top and left/bottom of the initial Node, if they exist. The Value data type stores
the Node's key for the point as a Value. The RectHV data type stores the axis-aligned
rectangle which encloses all of the points in the Node's subtree.


/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */

We implemented our range search method by starting at the root. If the root is not null,
the method checks if the query rectangle intersects with either of the root's subtree's
corresponding rectangles by using the RectHV API. If a subtree's rectangle does not
intersect with the query rectangle, the subtree is pruned. This process is repeated
recursively down the tree until a given subtree is completely pruned or returns a null value.
We implemented this recursive approach using a private helper method (insert) and public
method Iterable<Point2D> range.


/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */

Our method for nearest neighbor search utilizes a recursive approach similar to our
range method. We input the node, query point nearest neighbor point, and boolean for
if it is vertical or not. First, we test to see if the node or nearest neighbor
variable is null and if so, change variables accordingly. Next, we check if
our nearest neighbor is closer to the query than then node rectangle, which in
that case we'd just return the nearest neighbor. Next we see if the node point
distance to the query point is less than the neighbor point, which in that case we
would set the nearest neighbor to the node point.

Next, we use a private helper method called compare to compare the query point and
node point and use the orientation. If it is vertical, find distance between xs.
If horizontal, find distance between ys. Based on compare, if it is negative
we use recursion and set the nearest neighbor to the left branch and right branch
of the node respectively, and update the vertical tracker (first left then right)
Then, if compare is positive, we do vice versa. Finally, at the end of the method,
we return the nearest neighbor variable after it recurses and updates to the
correct neighbor.

We found the correct neighbor but even with help from lab TA we couldn't solve the
two tests mentioned below.

/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, rounding each value to use one digit after
 *  the decimal point. Use at least 1 second of CPU time. Do not use -Xint.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:        1,000                    92.6              10.8

KdTreeST:       5,000,000                 18.3             273,224.0

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

NA

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

Our program failed tests 8a and 8b (performs slightly incorrect traversal of k-d
tree during call to nearest(), performs the update-the-champion update before the
pruning test). We went to lab TA from 8pm-11pm on 3/29/23, but our TA was unable
to resolve these final two tests.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
 Took so long on last two tests got lab TA help and still couldn't solve.
Programming Assignment 5: K-d Trees
 
 
/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */
 
The Node data type we used to implement the 2d-tree data structure includes Point2D,
Node, Value, and RectHV data types. The Point2D data type stores the Node's corresponding
location on the 2D plane. The 2 Node data types store the references to the subtrees to the
immediate right/top and left/bottom of the initial Node, if they exist. The Value data type stores
the Node's key for the point as a Value. The RectHV data type stores the axis-aligned
rectangle which encloses all of the points in the Node's subtree.
 
 
/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */
 
We implemented our range search method by starting at the root. If the root is not null,
the method checks if the query rectangle intersects with either of the root's subtree's
corresponding rectangles by using the RectHV API. If a subtree's rectangle does not
intersect with the query rectangle, the subtree is pruned. This process is repeated
recursively down the tree until a given subtree is completely pruned or returns a null value.
We implemented this recursive approach using a private helper method (insert) and public
method Iterable<Point2D> range.
 
 
/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */
 
Our method for nearest neighbor search utilizes a recursive approach similar to our
range method. We input the node, query point nearest neighbor point, and boolean for
if it is vertical or not. First, we test to see if the node or nearest neighbor
variable is null and if so, change variables accordingly. Next, we check if
our nearest neighbor is closer to the query than then node rectangle, which in
that case we'd just return the nearest neighbor. Next we see if the node point
distance to the query point is less than the neighbor point, which in that case we
would set the nearest neighbor to the node point.
 
Next, we use a private helper method called compare to compare the query point and
node point and use the orientation. If it is vertical, find distance between xs.
If horizontal, find distance between ys. Based on compare, if it is negative
we use recursion and set the nearest neighbor to the left branch and right branch
of the node respectively, and update the vertical tracker (first left then right)
Then, if compare is positive, we do vice versa. Finally, at the end of the method,
we return the nearest neighbor variable after it recurses and updates to the
correct neighbor.
 
We found the correct neighbor but even with help from lab TA we couldn't solve the
two tests mentioned below.
 
/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, rounding each value to use one digit after
 *  the decimal point. Use at least 1 second of CPU time. Do not use -Xint.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */
 
 
                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:        1,000                    92.6              10.8
 
KdTreeST:       5,000,000                 18.3             273,224.0
 
Note: more calls per second indicates better performance.
 
 
/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
 
NA
