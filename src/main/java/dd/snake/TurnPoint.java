package dd.snake;

import dd.common.Direction;
import javafx.geometry.Point2D;

public class TurnPoint {
    private Direction direction;
    private Point2D point;

    TurnPoint(Direction direction, Point2D point) {
        this.direction = direction;
        this.point = point;
    }

    Direction getDirection() {
        return direction;
    }

    public Point2D getPoint() {
        return point;
    }

}
