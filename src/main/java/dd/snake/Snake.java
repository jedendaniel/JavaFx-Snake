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
    private static final int DEFAULT_MAX_SIZE = 10;
    private final Map<SnakeState, Procedure> snakeStateExpectedBehaviour = Map.of(
            MOVING, this::move,
            EATING, this::grow
    );

    private final Map<GameObjectType, SnakeState> snakeStateAfterCollision = Map.of(
            SNAKE, LOST,
            FOOD, EATING
    );
    private final GameObjectType gameObjectType = SNAKE;

    private Group group = new Group();
    private SnakePart head;
    private List<SnakePart> body = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private List<TurnPoint> turnPointsQueue = new LinkedList<>();
    private SnakeState state = MOVING;

    public Snake(Point2D headPosition, int initialSize) {
        addHead(headPosition);
        IntStream.range(1, initialSize)
                .forEach(i -> addBody(i, new SnakePart(headPosition.add(new Point2D(-i, 0)))));
        collisionHandler.addDynamicCollider(this);
    }

    private void addHead(Point2D headPosition) {
        head = new SnakePart(headPosition);
        head.setColor(Color.CORAL);
        group.getChildren().add(head.getNode());
        turnPointsQueue.add(0, new TurnPoint(direction, head.getPosition()));
    }

    private void addBody(int index, SnakePart part) {
        body.add(index - 1, part);
        group.getChildren().add(part.getNode());
        turnPointsQueue.add(index, new TurnPoint(direction, part.getPosition()));
        collisionHandler.addStaticCollider(part);
    }

    public void update() {
        Optional.ofNullable(snakeStateExpectedBehaviour.get(state)).ifPresent(behaviour -> {
            behaviour.execute();
            state = MOVING;
        });
    }

    private void move() {
        head.move(turnPointsQueue.get(0).getDirection());
        IntStream.range(0, body.size())
                .forEach(i -> {
                    body.get(i).move(turnPointsQueue.get(i + 1).getDirection());
                    body.get(i).draw();
                });
        turnPointsQueue.remove(turnPointsQueue.size() - 1);
        turnPointsQueue.add(0, new TurnPoint(direction, head.getPosition()));
    }

    private void grow() {
        addBody(1, new SnakePart(head.getPosition()));
        head.move(direction);
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
        state = snakeStateAfterCollision.get(collider.getGameObjectType());
    }

    public List<SnakePart> getBody() {
        return body;
    }

    public SnakePart getHead() {
        return head;
    }

    @Override
    public GameObjectType getGameObjectType() {
        return SNAKE;
    }

    public SnakeState getState() {
        return state;
    }
}
