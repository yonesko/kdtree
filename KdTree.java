/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size = 0;

    private static class Node {
        private final Point2D point;
        /**
         * |
         * 0 |
         * |
         * 1 -----
         */
        private final int d;
        private Node left, right;

        Node(Point2D point, int d) {
            this.point = point;
            this.d = d;
        }
    }

    public KdTree() {
    }                             // construct an empty set of points

    public boolean isEmpty() {
        return size == 0;
    }                 // is the set empty?

    public int size() {
        return size;
    }                      // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            root = new Node(p, 0);
            size++;
            return;
        }

        for (Node i = root; ; ) {
            if (i.point.equals(p)) {
                return;
            }
            if (i.d == 0) {
                if (p.x() < i.point.x()) {
                    if (i.left == null) {
                        i.left = new Node(p, 1);
                        size++;
                        return;
                    }
                    i = i.left;
                } else {
                    if (i.right == null) {
                        i.right = new Node(p, 1);
                        size++;
                        return;
                    }
                    i = i.right;
                }
            } else {
                if (p.y() < i.point.y()) {
                    if (i.left == null) {
                        i.left = new Node(p, 0);
                        size++;
                        return;
                    }
                    i = i.left;
                } else {
                    if (i.right == null) {
                        i.right = new Node(p, 0);
                        size++;
                        return;
                    }
                    i = i.right;
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root==null) return false;
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            Node node = queue.dequeue();
            if (node.point.equals(p)) {
                return true;
            }
            if (node.left != null) queue.enqueue(node.left);
            if (node.right != null) queue.enqueue(node.right);
        }

        return false;
    }         // does the set contain point p?

    public void draw() {
        _draw(root);
    }                // draw all points to standard draw

    private void _draw(Node node) {
        if (node == null) return;
        node.point.draw();
        _draw(node.left);
        _draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> result = new LinkedList<>();
        _range(root, rect, result);
        return result;
    }

    private void _range(Node n, RectHV rect, List<Point2D> points) {
        if (n == null) return;
        if (rect.contains(n.point)) {
            points.add(n.point);
        }

        _range(n.left, rect, points);
        _range(n.right, rect, points);

        /*
        if (n.d == 0) {
            if (rect.xmax() < n.point.x()) {
                _range(n.left, rect, points);
            } else {
                _range(n.right, rect, points);
            }
        } else {
            if (rect.ymax() < n.point.y()) {
                _range(n.left, rect, points);
            } else {
                _range(n.right, rect, points);
            }
        }
        */
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        throw new UnsupportedOperationException("nearest");
    }       // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
//empty
    }              // unit testing of the methods (optional)
}
