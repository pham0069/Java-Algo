package geometric.line;

import geometric.base.Point;

public class CollinearPoints {
    public static boolean arePointsCollinear(Point p1, Point p2, Point p3){
        return (p1.getX()-p2.getX())*(p2.getY()-p3.getY()) ==
                (p1.getY()-p2.getY())*(p2.getX()-p3.getX());
    }
}
