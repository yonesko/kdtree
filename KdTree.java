/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size = 0;

    private static class Node {
        private final Point2D point;
        /**
         * | 0 | | 1 -----
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
                }
                else {
                    if (i.right == null) {
                        i.right = new Node(p, 1);
                        size++;
                        return;
                    }
                    i = i.right;
                }
            }
            else {
                if (p.y() < i.point.y()) {
                    if (i.left == null) {
                        i.left = new Node(p, 0);
                        size++;
                        return;
                    }
                    i = i.left;
                }
                else {
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
        if (root == null) return false;

        for (Node i = root; ; ) {
            if (i.point.equals(p)) {
                return true;
            }
            if (i.d == 0) {
                if (p.x() < i.point.x()) {
                    if (i.left == null) {
                        return false;
                    }
                    i = i.left;
                }
                else {
                    if (i.right == null) {
                        return false;
                    }
                    i = i.right;
                }
            }
            else {
                if (p.y() < i.point.y()) {
                    if (i.left == null) {
                        return false;
                    }
                    i = i.left;
                }
                else {
                    if (i.right == null) {
                        return false;
                    }
                    i = i.right;
                }
            }
        }
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

        if (n.d == 0) {
            if (rect.xmax() < n.point.x()) {
                _range(n.left, rect, points);
            }
            else if (rect.xmin() >= n.point.x()) {
                _range(n.right, rect, points);
            }
            else {
                _range(n.left, rect, points);
                _range(n.right, rect, points);
            }
        }
        else {
            if (rect.ymax() < n.point.y()) {
                _range(n.left, rect, points);
            }
            else if (rect.ymin() >= n.point.y()) {
                _range(n.right, rect, points);
            }
            else {
                _range(n.left, rect, points);
                _range(n.right, rect, points);
            }
        }
    }

    private class Context {
        private Point2D champ;
        private double dist;
        private final Point2D p;

        Context(Point2D champ, double dist, Point2D p) {
            this.champ = champ;
            this.dist = dist;
            this.p = p;
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        Context context = new Context(null, Double.MAX_VALUE, p);
        _nearest(root, context);
        return context.champ;
    }

    private void _nearest(Node root, Context context) {
        if (root == null) return;
        double curDist = root.point.distanceSquaredTo(context.p);
        if (curDist < context.dist) {
            context.champ = root.point;
            context.dist = curDist;
        }

        boolean goLeft = root.d == 0
                         ? context.p.x() < root.point.x()
                         : context.p.y() < root.point.y();
        _nearest(goLeft ? root.left : root.right, context);
        if (context.p.distanceSquaredTo(new Point2D(
                root.d == 0 ? root.point.x() : context.p.x(),
                root.d == 0 ? context.p.y() : root.point.y()
        )) < context.dist) _nearest(goLeft ? root.right : root.left, context);
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(1, 1));
        if (!kdTree.contains(new Point2D(1, 1))) {
            throw new AssertionError();
        }

        kdTree.range(new RectHV(1, 1, 3, 3));
    }
}
