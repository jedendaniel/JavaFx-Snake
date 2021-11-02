package dd.snake;

import dd.GameObject;
import dd.GraphicObject;
import dd.collision.GameObjectType;
import dd.common.Direction;
import dd.util.Procedure;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static dd.Main.collisionHandler;
import static dd.collision.GameObjectType.FOOD;
import static dd.collision.GameObjectType.SNAKE;
import static dd.snake.SnakeState.EATING;
import static dd.snake.SnakeState.LOST;
import static dd.snake.SnakeState.MOVING;
import static dd.snake.SnakeState.WIN;

public class Snake implements GameObject, GraphicObject {

    private static final int DEFAULT_MAX_SIZE = 12;

    private final Map<SnakeState, Procedure> stateProcedure = Map.of(
            MOVING, this::move,
            EATING, this::grow
    );

    private static final Map<GameObjectType, SnakeState> COLLISION_STATE = Map.of(
            SNAKE, LOST,
            FOOD, EATING
    );

    private Group group = new Group();
    private LinkedList<SnakePart> body = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private SnakeState snakeState = MOVING;
    private int maxSize = DEFAULT_MAX_SIZE;

    public Snake(Point2D headPosition, int initialSize) {
        IntStream.range(0, initialSize)
                .forEach(i -> addBody(new SnakePart(headPosition.add(new Point2D(-i, 0)))));
    }

    private void addBody(SnakePart part) {
        body.addLast(part);
        group.getChildren().add(part.getNode());
        collisionHandler.addDynamicCollider(part);
    }

    public void update() {
        Optional<SnakePart> lastCollider = body.stream()
                .filter(snakePart -> snakePart.getLastCollider().isPresent())
                .findAny();
        if (lastCollider.isPresent() && lastCollider.get().getLastCollider().isPresent()) {
            snakeState = COLLISION_STATE.get(lastCollider.get().getLastCollider().get());
            lastCollider.get().resetLastCollider();
        }
        Optional.ofNullable(stateProcedure.get(snakeState)).ifPresent(Procedure::execute);
    }

    private void move() {
        SnakePart newHead = body.pollLast();
        SnakePart oldHead = body.getFirst();
        assert newHead != null;
        newHead.setPosition(oldHead.getPosition().add(direction.get2D()));
        newHead.draw();
        body.addFirst(newHead);
    }

    private void grow() {
        SnakePart newHead = new SnakePart(body.getLast().getPosition());
        SnakePart oldHead = body.getFirst();
        newHead.setPosition(oldHead.getPosition().add(direction.get2D()));
        group.getChildren().add(newHead.getNode());
        collisionHandler.addDynamicCollider(newHead);
        body.addFirst(newHead);
        if (body.size() >= maxSize - 1) {
            snakeState = WIN;
        } else {
            snakeState = MOVING;
        }
    }

    @Override
    public void draw() {
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

    public SnakeState getSnakeState() {
        return snakeState;
    }

    void setSnakeState(SnakeState snakeState) {
        this.snakeState = snakeState;
    }

    int getSize() {
        return body.size();
    }

    List<SnakePart> getBody() {
        return body;
    }

    void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public Point2D getPosition() {
        return body.getFirst().getPosition();
    }
}
