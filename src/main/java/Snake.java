
import collision.Collider;
import common.Point2D;
import common.SimpleDirection;
import javafx.scene.Group;

import java.util.*;
import java.util.stream.IntStream;

public class Snake implements Collider {
    private static final int DEFAULT_MAX_SIZE = 10;

    private Group snakeGroup = new Group();
    private List<SnakePart> snakeParts = new LinkedList<>();
    private SimpleDirection direction = SimpleDirection.LEFT;
    private List<TurnPoint> turnPointsQueue = new LinkedList<>();
    private SnakeState state = SnakeState.NORMAL;

    public Snake(Point2D headPosition, int initialSize) {
        IntStream.range(0, initialSize)
                .forEach(i -> addPart(i, headPosition.add(new Point2D(Main.UNIT * i, 0))));
    }

    private void addPart(int index, Point2D position) {
        SnakePart part = new SnakePart(position);
        snakeParts.add(index, part);
        snakeGroup.getChildren().add(part);
        turnPointsQueue.add(index, new TurnPoint(direction, position));
    }

    private void addPart(int index, SnakePart part) {
        snakeParts.add(index, part);
        snakeGroup.getChildren().add(part);
        turnPointsQueue.add(index, new TurnPoint(direction, part.getPosition()));
    }

    public void update() {
        if(snakeParts.size() == DEFAULT_MAX_SIZE) {
            state = SnakeState.WIN;
        }
        if (state.equals(SnakeState.NORMAL)) {
            move();
        }
        if (state.equals(SnakeState.EATING)) {
            grow();
        }
    }

    private void move() {
        IntStream.range(0, snakeParts.size())
                .forEach(i -> snakeParts.get(i).move(turnPointsQueue.get(i).getDirection()));
        turnPointsQueue.remove(turnPointsQueue.size() - 1);
        turnPointsQueue.add(0, new TurnPoint(direction, snakeParts.get(0).getPosition()));
    }

    private void grow() {
        addPart(0, new SnakePart(snakeParts.get(0).getPosition()));
        snakeParts.get(0).move(direction);
        state = SnakeState.NORMAL;
    }

    public void turn(boolean right) {
        direction = right ? direction.getNext(direction) : direction.getPrevious(direction);
    }

    public void eat() {
        state = SnakeState.EATING;
    }

    @Override
    public boolean isCollision(Collider collider) {
        return snakeParts.get(0).isCollision(collider);
    }

    @Override
    public Point2D getPosition() {
        return snakeParts.get(0).getPosition();
    }

    @Override
    public void handleCollision(Collider collider) {
    }

    public Group getGroup() {
        return snakeGroup;
    }

    public SnakePart getHead() {
        return snakeParts.get(0);
    }

    public List<SnakePart> getCorp() {
        return snakeParts.subList(1, snakeParts.size());
    }

    public SnakeState getState() {
        return state;
    }
}
