import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines = new ArrayList<>();
    private final Point[] points;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            this.points[i] = points[i];
        }
        checkMultiple();
        for (Point p:this.points) {
            addLineSegments(p);
        }
    }

    private void checkMultiple() {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
                if (points[i].compareTo(points[i+1]) == 0) {
                    throw new IllegalArgumentException();
            }
        }
    }

    private void addLineSegments(Point p) {
        Point[] points = new Point[this.points.length];
        for (int i = 0; i < points.length; i++) {
            points[i] = this.points[i];
        }
        Arrays.sort(points, p.slopeOrder());
        ArrayList<Point> temp = new ArrayList<>();
        double preSlope = Double.NEGATIVE_INFINITY;
        for (int i = 1; i < points.length; i++) {
            if (p.slopeTo(points[i]) != preSlope) {
                if (temp.size() > 2 && p.compareTo(temp.get(0)) < 0) {
                    lines.add(new LineSegment(p, points[i-1]));
                }
                preSlope = p.slopeTo(points[i]);
                temp = new ArrayList<>();
            }
            temp.add(points[i]);
        }
        if (temp.size() > 2 && p.compareTo(temp.get(0)) < 0) {
            lines.add(new LineSegment(p, temp.get(temp.size() - 1)));
        }
    }


    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        Point[] points = new Point[12];
        points[0] = new Point(0, 0);
        points[1] = new Point(-2, -2);
        points[2] = new Point(-9, -9);
        points[3] = new Point(3, 3);
        points[4] = new Point(2, 1);
        points[5] = new Point(3, 0);
        points[6] = new Point(6, -3);
        points[7] = new Point(-3, 6);
        points[8] = new Point(1, 3);
        points[9] = new Point(-2, -3);
        points[10] = new Point(-5, -9);
        points[11] = new Point(3, 7);

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-10, 10);
        StdDraw.setYscale(-10, 10);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints fcp = new FastCollinearPoints(points);
        for (LineSegment ls : fcp.segments()) {
            System.out.println(ls);
            ls.draw();
        }
        StdDraw.show();
    }
}
