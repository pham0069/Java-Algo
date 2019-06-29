package design.design_pattern.structural;

/**
 * Given the following problem:
 * 1. The subsystem has implemented 2 types of point presentations: cartesian (with coordinator x, y) and polar (with angle
 * and radius)
 * 2. A client community needs to implement Line class, which is defined by 2 points, origin and end.
 * Line class needs a simple point interface that allows move and rotate operations
 *
 * How can we provide this Point implementation which is ready to use for the Line implementation?
 */
public class FacadeProblem {
    static class PointCartesian {
        private double x, y;
        public PointCartesian(double x, double y ) {
            this.x = x;
            this.y = y;
        }

        public void  move( int x, int y ) {
            this.x += x;
            this.y += y;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    // 1. Subsystem
    static class PointPolar {
        private double radius, angle;

        public PointPolar(double radius, double angle) {
            this.radius = radius;
            this.angle = angle;
        }

        public void  rotate(int angle) {
            this.angle += angle % 360;
        }

        public String toString() {
            return "[" + radius + "@" + angle + "]";
        }
    }

    interface Point {
        void move(int x, int y);
        void rotate(double angle, Point origin);
    }

    static class Line {
        private final FacadeDemo.Point origin, end;
        public Line(FacadeDemo.Point origin, FacadeDemo.Point end) {
            this.origin = origin;
            this.end = end;
        }

        public void  move(int x, int y) {
            origin.move(x, y);
            end.move(x, y);
        }

        public void  rotate(int angle) {
            end.rotate(angle, origin);
        }

        public String toString() {
            return "Origin is " + origin + ". End is " + end;
        }
    }
    public static void main(String[] args) {}
}
