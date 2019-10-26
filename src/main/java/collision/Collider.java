package collision;

import common.Point2D;

public interface Collider {
    boolean isCollision(Collider collider);
    Point2D getPosition();
    void handleCollision(Collider collider);
}
