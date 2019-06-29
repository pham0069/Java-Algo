package design.design_pattern.structural;

/**
 * Facade encapsulates a complex subsystem with single interface object, making it easier to use
 * Its purpose is to hide internal complexity behind a single interface that appears simple on the outside
 *
 * Facade should be used if:
 * 1. You have a complex system that you want to expose to clients in a simplified way
 * 2. You want an external communication layer over an existing system which is incompatible with the system
 *
 * Adv:
 * 1. Reduces the learning curve necessary to successfully leverage the subsystem.
 * 2. Decouple subsystem from its potentially many clients
 * 3. Provide the only access point to sub-system, i.e. any change only needs to be implemented in this single point
 *
 * Disadv:
 * As single access point, it also limits the features and flexibility that 'power user' may need
 *
 * Note that Facade and Adapter are both wrappers but they have different intents:
 * 1. Facade defines a new interface, which is simpler to use
 * Adapter uses old interface, the existing one
 * 2. Facade routinely wraps multiple objects
 * Adapter basically wraps a single object
 *
 */
public class FacadeDemo {
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

    static class PointPolar {
        private double radius, angle;

        public PointPolar(double radius, double angle) {
            this.radius = radius;
            this.angle = angle;
        }

        public void rotate(double angle) {
            this.angle += angle % 360;
        }

        public double getRadius() {
            return radius;
        }

        public double getAngle() {
            return angle;
        }

        public String toString() {
            return "[" + radius + "@" + angle + "]";
        }
    }

    static class Point {
        private PointCartesian pointCartesian;

        public Point(double x, double y) {
            pointCartesian = new PointCartesian(x, y);
        }

        public String toString() {
            return pointCartesian.toString();
        }

        public void move(int x, int y) {
            pointCartesian.move(x, y);
        }


        public void rotate(int angle, Point o) {
            double x = pointCartesian.getX() - o.pointCartesian.getX();
            double y = pointCartesian.getY() - o.pointCartesian.getY();
            PointPolar pointPolar = getPointPolar(x, y);
            pointPolar.rotate(angle);
            double r = pointPolar.getRadius();
            double a = pointPolar.getAngle();
            pointCartesian = new PointCartesian(r*Math.cos(a*Math.PI/180) + o.pointCartesian.getX(),
                    r*Math.sin(a * Math.PI / 180) + o.pointCartesian.getY());
        }

        private PointPolar getPointPolar(double x, double y) {
            return new PointPolar(Math.sqrt(x * x + y * y),
                    Math.atan2(y, x) * 180 / Math.PI);

        }

    }

    static class Line {
        private final Point origin, end;
        public Line(Point origin, Point end) {
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
    public static void main(String[] args) {
        Line lineA = new Line(new Point(2, 4), new Point(5, 7));
        lineA.move(-2, -4);
        System.out.println( "after move:  " + lineA );
        lineA.rotate(45);
        System.out.println( "after rotate: " + lineA );
        Line lineB = new Line( new Point(2, 1), new Point(2.866, 1.5));
        lineB.rotate(30);
        System.out.println("30 degrees to 60 degrees: " + lineB);
    }
}
