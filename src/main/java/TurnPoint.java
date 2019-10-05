public class TurnPoint {
    private SimpleDirection direction;
    private Point2D point;
    private int retention;

    public TurnPoint(SimpleDirection direction, Point2D point, int retention) {
        this.direction = direction;
        this.point = point;
        this.retention = retention;
    }

    public SimpleDirection getDirection() {
        return direction;
    }

    public Point2D getPoint() {
        return point;
    }

    public int use() {
        return retention--;
    }
}
