import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lines = new ArrayList<>();
    private final Point[] points;

    public BruteCollinearPoints(Point[] points) {
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
        addLines();

    }    // finds all line segments containing 4 points

    private void checkMultiple() {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void addLines() {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 3; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length - 2; j++) {
                Point q = points[j];
                double slopeQ = p.slopeTo(q);
                for (int k = j + 1; k < points.length - 1; k++) {
                    Point r = points[k];
                    double slopeR = p.slopeTo(r);
                    if (slopeR != slopeQ) {
                        continue;
                    }
                    for (int m = k + 1; m < points.length; m++) {
                        Point s = points[m];
                        if (slopeR != p.slopeTo(s)) {
                            continue;
                        }
                        lines.add(new LineSegment(p, s));
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lines.size();
    }       // the number of line segments

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

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        for (LineSegment ls:bcp.segments()) {
            System.out.println(ls);
        }
    }
}
