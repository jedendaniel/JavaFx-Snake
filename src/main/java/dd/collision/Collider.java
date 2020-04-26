package dd.collision;


import javafx.geometry.Point2D;

public interface Collider {
    Point2D getPosition();
    void handleCollision(Collider collider);
    GameObjectType getGameObjectType();
}
