import common.Point2D;
import common.SimpleDirection;

public class TurnPoint {
    private SimpleDirection direction;
    private Point2D point;

    public TurnPoint(SimpleDirection direction, Point2D point) {
        this.direction = direction;
        this.point = point;
    }

    public SimpleDirection getDirection() {
        return direction;
    }

    public Point2D getPoint() {
        return point;
    }

}
