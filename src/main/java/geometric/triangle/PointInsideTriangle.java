package geometric.triangle;

import geometric.line.LineSegmentIntersect;
import geometric.base.Point;

import java.util.Arrays;

import static geometric.line.LineSegmentIntersect.Orientation;
import static geometric.line.LineSegmentIntersect.getOrientation;

/**
 * https://www.geeksforgeeks.org/check-whether-a-given-point-lies-inside-a-triangle-or-not/
 *
 * Given three corner points A, B, C of a triangle, and one more point P.
 * Check whether P lies within the triangle ABC or not.
 *
 * Sample input
 * A(0, 0), B(10, 30), C(20, 0)
 * Sample output
 * Return true for P(10, 15)
 * return false for P'(30, 15)
 *
 * One solution is checking orientation. P is inside triangle ABC if the three following conditions are met:
 * 1. P, A lie on same side of line BC
 * 2. P, B lie on same side of line CA
 * 3. P, C lie on same side of line AB
 *
 * Another solution is checking area. P is inside triangle ABC if the sum of areas of PAB, PAC and PBC are
 * equal to that of ABC
 */
public class PointInsideTriangle {
    public static void main(String[] args){
        test();
    }
    private static void test(){
        Point a = new Point(0, 0);
        Point b = new Point(10, 30);
        Point c = new Point(20, 0);
        Point[] points = {
                new Point(10, 15),
                new Point(30, 15),
                new Point(10, 0),
                new Point(50, 0)
        };
        Arrays.stream(points).forEach(p -> System.out.println(isPointInsideTriangle(p, a, b, c)));
    }
    public static boolean isPointInsideTriangle(Point p, Point t1, Point t2, Point t3){
        return arePointsOnSameSideOfLine(p, t1, t2, t3) &&
                arePointsOnSameSideOfLine(p, t2, t3, t1) &&
                arePointsOnSameSideOfLine(p, t3, t1, t2);
    }

    /**
     * Check if the two points p1, p2 lie on the same side fo the line that passes through
     * 2 points l1 and l2. If either p1 or p2 or both of them are collinear with l1 and l2,
     * they are considered as on the same side.
     */
    public static boolean arePointsOnSameSideOfLine (Point p1, Point p2, Point l1, Point l2){
        Orientation o1 = getOrientation(l1, l2, p1);
        Orientation o2 = getOrientation(l1, l2, p2);
        if (o1 == LineSegmentIntersect.Orientation.COLLINEAR ||
            o2 == LineSegmentIntersect.Orientation.COLLINEAR)
            return true;
        return o1==o2;
    }
}
