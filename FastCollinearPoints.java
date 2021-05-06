/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] points;
    private final LineSegment[] lineSegments;
    private ArrayList<LineSegment> lines = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // check if points is null
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
        // check the collinear points
        if (localPoints.length > 3) {
            for (Point p : localPoints) {
                Point[] temp = points.clone();
                Arrays.sort(temp, p.slopeOrder());
                findLines(temp, p);
            }
        }
        lineSegments = lines.toArray(new LineSegment[lines.size()]);
    } // finds all line segments containing 4 or more points

    private void findLines(Point[] temp, Point p) {
        // get the first slope
        double currSlope = p.slopeTo(temp[1]);
        int start = 1;
        // go through each point that has the same slope as the start point
        for (int i = 2; i < temp.length; i++) {
            // check if the total number of the points is >= 4
            if (p.slopeTo(temp[i]) != currSlope) {
                int numberOfPoints = i - start;
                if (numberOfPoints >= 3) {
                    // now we have a line
                    Point[] linePoints = getLinepoints(temp, p, start, i);
                    if (linePoints[0].compareTo(p) == 0) {
                        LineSegment line = new LineSegment(linePoints[0], linePoints[1]);
                        lines.add(line);
                    }
                }
                // start from a new point
                start = i;
                currSlope = p.slopeTo(temp[i]);
            }
        }
        // check the last few points
        int numberOfPoints = temp.length - start;
        if (numberOfPoints >= 3) {
            Point[] lastPoints = getLinepoints(temp, p, start, temp.length);
            if (lastPoints[0].compareTo(p) == 0) {
                LineSegment line = new LineSegment(lastPoints[0], lastPoints[1]);
                lines.add(line);
            }
        }
    }

    // sort the points from lowest to highest
    private Point[] getLinepoints(Point[] points, Point p, int start, int end) {
        ArrayList<Point> res = new ArrayList<Point>();
        res.add(p);
        for (int i = start; i < end; i++) {
            res.add(points[i]);
        }
        Point[] linePoints = res.toArray(new Point[res.size()]);
        Arrays.sort(linePoints);
        return new Point[] { linePoints[0], linePoints[res.size() - 1] };
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }        // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.clone();
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

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

