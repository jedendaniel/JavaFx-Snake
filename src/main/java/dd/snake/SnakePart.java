package dd.snake;

import dd.GraphicObject;
import dd.collision.Collider;
import dd.collision.GameObjectType;
import dd.common.Direction;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

import static dd.collision.GameObjectType.SNAKE;
import static dd.config.Properties.UNIT;

public class SnakePart implements GraphicObject, Collider {
    private static final Color COLOR = Color.GREEN;
    private final Rectangle graphic = new Rectangle();
    private Point2D position;
    private Optional<GameObjectType> lastCollider = Optional.empty();

    SnakePart(Point2D position) {
        this.position = position;
        graphic.setX(position.getX() * UNIT);
        graphic.setY(position.getY() * UNIT);
        graphic.setHeight(UNIT);
        graphic.setWidth(UNIT);
        graphic.setFill(COLOR);
    }

    void move(Direction direction) {
        position = position.add(direction.getX(), direction.getY());
    }

    @Override
    public void draw() {
        graphic.setX(position.getX() * UNIT);
        graphic.setY(position.getY() * UNIT);
    }

    @Override
    public Node getNode() {
        return graphic;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public void handleCollision(Collider collider) {
        lastCollider = Optional.of(collider.getGameObjectType());
    }

    @Override
    public GameObjectType getGameObjectType() {
        return SNAKE;
    }

    void setColor(Color color) {
        graphic.setFill(color);
    }

    public Optional<GameObjectType> getLastCollider() {
        return lastCollider;
    }

    public void resetLastCollider() {
        this.lastCollider = Optional.empty();
    }
}
