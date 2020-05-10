package dd.common;

import javafx.geometry.Point2D;

import java.util.Random;

public class RandomPoint2D {

    private Random random = new Random();

    public RandomPoint2D() {
    }

    RandomPoint2D(Random random) {
        this.random = random;
    }

    public Point2D nextPoint2D(int bound) {
        return new Point2D(random.nextInt(bound), random.nextInt(bound));
    }

    public Point2D nextPoint2D(int boundX, int boundY) {
        return new Point2D(random.nextInt(boundX), random.nextInt(boundY));
    }
}
