package geometric.line;

import geometric.base.Point;

/**
 * https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
 * Given 2 line segments. Check if they intersect with each other.
 *
 * Given 3 points A, B, C. Orientation of triplet (A, B, C) can be counterclockwise,
 * clockwise or collinear. Imagine B as the center of the clock, the orientation of
 * the triplet is the direction to go from C to A.
 *
 * 2 line segments P and Q, respectively defined by (p1, p2) and (q1, q2), intersect
 * with each other if BOTH two following conditions are met:
 * 1. q1, q2 lie on different sides defined by the line (p1, p2)
 * 2. p1, p2 lie on different sides defined by the line (q1, q2)
 *
 * These conditions can be converted in terms of orientation as follows:
 * (1) <--> (p1, p2, q1) and (p1, p2, q2) has different orientations
 * (2) <--> (q1, q2, p1) and (q1, q2, p2) has different orientations
 *
 * The special case is 3 points are collinear, for example, p1, p2, q1
 * In this case, 2 line segments only intersect with each other if q1 lies on the segment
 * defined by p1, p2
 */
public class LineSegmentIntersect {
    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(10, 1);
        Point q1 = new Point(1, 2);
        Point q2 = new Point(10, 2);

        System.out.println(doSegmentsIntersect(p1, p2, q1, q2));

        p1 = new Point (10, 0);
        p2 = new Point (0, 10);
        q1 = new Point (0, 0);
        q2 = new Point (10, 10);
        System.out.println(doSegmentsIntersect(p1, p2, q1, q2));

        p1 = new Point (-5, -5);
        p2 = new Point (0, 0);
        q1 = new Point (1, 1);
        q2 = new Point (10, 10);
        System.out.println(doSegmentsIntersect(p1, p2, q1, q2));
    }
    public static boolean doSegmentsIntersect(Point p1, Point p2, Point q1, Point q2){
        return doesLineIntersectWithSegment(p1, p2, q1, q2)
                && doesLineIntersectWithSegment(q1, q2, p1, p2);

    }

    /**
     * Check if the line segment defined by (s1, s2) intersects with the line that passes
     * through 2 points l1 and l2
     */
    public static boolean doesLineIntersectWithSegment (Point l1, Point l2, Point s1, Point s2){
        Orientation o1 = getOrientation(l1, l2, s1);
        Orientation o2 = getOrientation(l1, l2, s2);
        if (o1 != Orientation.COLLINEAR && o2 != Orientation.COLLINEAR)
            return o1 != o2;
        return (o1 == Orientation.COLLINEAR && isPointOnSegment(s1, l1, l2))
                || (o2 == Orientation.COLLINEAR && isPointOnSegment(s2, l1, l2));
    }

    /**
     * Get the orientation of the points triplet (a, b, c)
     */
    public static Orientation getOrientation (Point a, Point b, Point c){
        int delta = (b.getY()-a.getY())*(b.getX()-c.getX())-
                    (b.getY()-c.getY())*(b.getX()-a.getX());
        if (delta == 0)
            return Orientation.COLLINEAR;
        return delta < 0 ? Orientation.CLOCKWISE : Orientation.COUNTERCLOCKWISE;
    }

    /**
     * Given that a, b and c are collinear
     * Check if the point a lies on the line segment defined by two points b and c
     */
    public static boolean isPointOnSegment (Point a, Point b, Point c){
        return getOrientation(a, b, c) == Orientation.COLLINEAR
                && isBetween(a.getX(), b.getX(), c.getX())
                && isBetween(a.getY(), b.getY(), c.getY());
    }
    /**
     * Check if a is between b and c
     */
    private static boolean isBetween(int a, int b, int c){
        return a <= Math.max(b, c) && a >= Math.min(b, c);
    }

    public static enum Orientation{
        COLLINEAR,
        CLOCKWISE,
        COUNTERCLOCKWISE;
    }
}
