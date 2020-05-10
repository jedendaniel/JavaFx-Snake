package dd.snake;

import dd.GameObject;
import dd.GraphicObject;
import dd.collision.Collider;
import dd.collision.GameObjectType;
import dd.common.Direction;
import dd.util.Procedure;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static dd.Main.collisionHandler;
import static dd.collision.GameObjectType.FOOD;
import static dd.collision.GameObjectType.SNAKE;
import static dd.snake.SnakeState.*;

public class Snake implements GameObject, GraphicObject, Collider {

    private static final int DEFAULT_MAX_SIZE = 12;

    private final Map<SnakeState, Procedure> procedureForSnakeState = Map.of(
            MOVING, this::move,
            EATING, this::grow
    );

    private final Map<GameObjectType, SnakeState> snakeStateAfterCollision = Map.of(
            SNAKE, LOST,
            FOOD, EATING
    );

    private Group group = new Group();
    private SnakePart head;
    private List<SnakePart> body = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private List<TurnPoint> turnPointsQueue = new LinkedList<>();
    private SnakeState snakeState = MOVING;
    private int maxSize = DEFAULT_MAX_SIZE;

    public Snake(Point2D headPosition, int initialSize) {
        addHead(headPosition);
        IntStream.range(1, initialSize)
                .forEach(i -> addBody(new SnakePart(headPosition.add(new Point2D(-i, 0)))));
        collisionHandler.addDynamicCollider(this);
    }

    private void addHead(Point2D headPosition) {
        head = new SnakePart(headPosition);
        head.setColor(Color.CORAL);
        group.getChildren().add(head.getNode());
        turnPointsQueue.add(0, new TurnPoint(direction, head.getPosition()));
    }

    private void addBody(SnakePart part) {
        body.add(part);
        group.getChildren().add(part.getNode());
        turnPointsQueue.add(new TurnPoint(direction, part.getPosition()));
        collisionHandler.addStaticCollider(part);
    }

    public void update() {
        Optional.ofNullable(procedureForSnakeState.get(snakeState)).ifPresent(Procedure::execute);
    }

    private void move() {
        turnPointsQueue.add(0, new TurnPoint(direction, head.getPosition()));
        turnPointsQueue.remove(turnPointsQueue.size() - 1);
        head.move(turnPointsQueue.get(0).getDirection());
        IntStream.range(0, body.size())
                .forEach(i -> {
                    body.get(i).move(turnPointsQueue.get(i + 1).getDirection());
                    body.get(i).draw();
                });
    }

    private void grow() {
        Point2D newPartPosition = body.get(body.size() - 1).getPosition();
        move();
        addBody(new SnakePart(newPartPosition));
        if (body.size() == maxSize - 1) {
            snakeState = WIN;
        } else {
            snakeState = MOVING;
        }
    }

    @Override
    public void draw() {
        head.draw();
        body.forEach(GraphicObject::draw);
    }

    public Node getNode() {
        return group;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Point2D getPosition() {
        return head.getPosition();
    }

    @Override
    public void handleCollision(Collider collider) {
        snakeState = snakeStateAfterCollision.get(collider.getGameObjectType());
    }

    @Override
    public GameObjectType getGameObjectType() {
        return SNAKE;
    }

    public SnakeState getSnakeState() {
        return snakeState;
    }

    void setSnakeState(SnakeState snakeState) {
        this.snakeState = snakeState;
    }

    int getSize() {
        return body.size() + 1;
    }

    List<SnakePart> getBody() {
        return body;
    }

    void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
