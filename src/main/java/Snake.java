
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.IntStream;

public class Snake {
    private static final Color COLOR = Color.GREEN;
    private static final int UNIT = 20;
    private static final int HEIGHT = UNIT;
    private static final int WIDTH = UNIT;

    private final int INITIAL_SIZE = 4;

    private Group snakeGroup = new Group();
    private List<SnakePart> snakeParts = new ArrayList<>();
    private SimpleDirection direction = SimpleDirection.LEFT;
    private Queue<TurnPoint> turnPointsQueue = new LinkedList<>();

    public Snake(Point2D headPosition) {
        IntStream.range(0, INITIAL_SIZE)
                .forEach(i -> addPart(headPosition.add(new Point2D(WIDTH * i, 0))));
        turnPointsQueue.add(new TurnPoint(direction, snakeParts.get(0).getPosition(), INITIAL_SIZE - 1));
    }

    public Group getGroup() {
        return snakeGroup;
    }

    private void addPart(Point2D position) {
         SnakePart part = new SnakePart(position, direction);
         snakeParts.add(part);
         snakeGroup.getChildren().add(part);
    }

    public void move() {
        snakeParts.forEach(part ->
        {
            turnPointsQueue.forEach(point ->{
                if (part.getPosition().isEqual(point.getPoint())) {
                    if (point.use() == 0) {
                        turnPointsQueue.remove();
                    }
                    part.setDirection(point.getDirection());
                }
            });
            part.move();
        });
    }

    public void turn(boolean right) {
        direction = right ? direction.getNext(direction) : direction.getPrevious(direction);
        turnPointsQueue.add(new TurnPoint(direction, snakeParts.get(0).getPosition(), snakeParts.size() - 1));
    }
}
