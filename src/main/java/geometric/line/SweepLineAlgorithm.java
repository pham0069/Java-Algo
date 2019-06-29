package geometric.line;

/**
 * Given n line segments. Find out if any two line segments intersect or not.
 *
 * Naive Algorithm: check every pair of lines if they intersects or not.
 * We can check two line segments in O(1) time. Therefore, this approach takes O(n^2).
 *
 * Sweep Line Algorithm: implement the check in O(n*logn) time.
 *
 * Given 4 points p0, p1, p2, p3
 * If p0.x <= p1.x < p2.x <= p3.x, then the segment p0-p1 do not intersect with p2-p3
 * Counterproof: If these 2 segments intersect at a point p*, then p* must be between p0 and p1, and between p2 and p3
 * This means p0.x <= p*.x <= p1.x and p2.x <= p*.x <= p3.x. But these two intervals are exclusive, hence no such p*.x
 */
public class SweepLineAlgorithm {
}
