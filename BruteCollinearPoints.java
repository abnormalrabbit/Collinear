/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    //private Point[] points;
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        // null check
        if (points == null) {
            throw new IllegalArgumentException("input is null");
        }
        // check the null point
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("some point is null");
            }
        }
        Point[] localPoints = points.clone();
        // sort the points
        Arrays.sort(localPoints);
        // check the duplicates
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("duplicated point");
            }
        }
        // collinear
        ArrayList<LineSegment> res = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int m = j + 1; m < points.length - 1; m++) {
                    for (int z = m + 1; z < points.length; z++) {
                        Point I = localPoints[i];
                        Point J = localPoints[j];
                        Point M = localPoints[m];
                        Point Z = localPoints[z];
                        double slopeOfIJ = I.slopeTo(J);
                        double slopeOfIM = I.slopeTo(M);
                        double slopeOFIZ = I.slopeTo(Z);
                        if (slopeOfIJ == slopeOfIM && slopeOfIJ == slopeOFIZ) {
                            res.add(new LineSegment(I, Z));
                        }
                    }
                }
            }
        }
        segments = res.toArray(new LineSegment[res.size()]);
    }

    public int numberOfSegments() { // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() { // the line segments
        return segments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    }
}
