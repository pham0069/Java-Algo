package geometric.base;

import lombok.Data;

@Data
public final class Point implements Comparable<Point>{
    private final int x;
    private final int y;
    public int compareTo(Point that){
        if (that == null)
            return 1;
        if (this.x != that.x)
            return this.x - that.x;
        return this.y - that.y;
    }
}
