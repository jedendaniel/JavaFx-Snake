package dd.snake;

import dd.GraphicObject;
import dd.collision.Collider;
import dd.collision.GameObjectType;
import dd.common.Direction;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static dd.collision.GameObjectType.SNAKE;
import static dd.config.WindowProperties.UNIT;

public class SnakePart implements GraphicObject, Collider {
    private static final Color COLOR = Color.GREEN;
    private final Rectangle graphic = new Rectangle();
    private Point2D position;
    private final GameObjectType gameObjectType = SNAKE;

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

    @Override
    public void handleCollision(Collider collider) {
    }

    @Override
    public GameObjectType getGameObjectType() {
        return SNAKE;
    }

    void setColor(Color color) {
        graphic.setFill(color);
    }
}
