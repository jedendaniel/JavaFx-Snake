package dd.food;

import dd.GameObject;
import dd.collision.Collider;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.Random;

import static dd.config.WindowProperties.*;

public class FoodSpawner implements GameObject {

    private Random random = new Random();
    private final Food food;
    private List<Collider> colliders;

    public FoodSpawner(Food food, List<Collider> colliders) {
        this.food = food;
        this.colliders = colliders;
    }

    private Point2D getRandomPosition() {
        Point2D point2D;
        do {
            point2D = calculateRandomPosition();
        } while (isPositionOccupied(point2D, colliders));
        return point2D;
    }

    private Point2D calculateRandomPosition() {
        return new Point2D(random.nextInt(WIDTH / UNIT), (random.nextInt(HEIGHT / UNIT)));
    }

    private boolean isPositionOccupied(Point2D position, List<Collider> colliders) {
        return colliders.stream()
                .anyMatch(gameObject -> positionsEquals(position, gameObject.getPosition()));
    }

    private boolean positionsEquals(Point2D firstPosition, Point2D secondPosition) {
        return firstPosition.equals(secondPosition);
    }

    @Override
    public void update() {
        if (food.getState() == FoodState.SPAWNING) {
            Point2D randomPosition = getRandomPosition();
            food.setPosition(randomPosition);
            food.setStateToIdle();
        }
    }
}
