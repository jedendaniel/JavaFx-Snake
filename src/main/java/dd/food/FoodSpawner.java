package dd.food;

import dd.GameObject;
import dd.collision.Collider;
import dd.common.RandomPoint2D;
import javafx.geometry.Point2D;

import java.util.List;

import static dd.config.WindowProperties.UNIT;
import static dd.config.WindowProperties.WIDTH;

public class FoodSpawner implements GameObject {

    private RandomPoint2D random = new RandomPoint2D();
    private final Food food;
    private List<Collider> colliders;

    public FoodSpawner(Food food, List<Collider> colliders) {
        this.food = food;
        this.colliders = colliders;
    }

    FoodSpawner(RandomPoint2D random, Food food, List<Collider> colliders) {
        this.random = random;
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
        return random.nextPoint2D(WIDTH / UNIT);
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
