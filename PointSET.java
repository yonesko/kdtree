/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PointSET {

    private final TreeSet<Point2D> points = new TreeSet<>();

    public PointSET() {
    }                             // construct an empty set of points

    public boolean isEmpty() {
        return points.isEmpty();
    }                 // is the set empty?

    public int size() {
        return points.size();
    }                      // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }              // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }         // does the set contain point p?

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }                // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return points.stream().filter(rect::contains).collect(Collectors.toList());
    }        // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (points.isEmpty()) return null;
        return points.stream()
                .sorted(Comparator.comparingDouble(p::distanceTo))
                .findFirst().get();
    }       // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {


//empty
    }              // unit testing of the methods (optional)
}