package dd.food;

import dd.GraphicObject;
import dd.collision.Collider;
import dd.collision.GameObjectType;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static dd.Main.collisionHandler;
import static dd.collision.GameObjectType.FOOD;
import static dd.config.Properties.UNIT;
import static dd.food.FoodState.SPAWNING;

public class Food implements GraphicObject, Collider {

    private static final Color COLOR = Color.RED;
    private final Rectangle graphic = new Rectangle();
    private Point2D position;
    private FoodState state = SPAWNING;
    private final GameObjectType gameObjectType = FOOD;

    public Food() {
        graphic.setWidth(UNIT);
        graphic.setHeight(UNIT);
        graphic.setFill(COLOR);
        collisionHandler.addStaticCollider(this);
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

    void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public void handleCollision(Collider collider) {
        state = SPAWNING;
    }

    void setStateToIdle() {
        this.state = FoodState.IDLE;
    }

    FoodState getState() {
        return state;
    }

    @Override
    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }
}
