package dd.snake;

import dd.common.Direction;
import javafx.geometry.Point2D;
import org.junit.Test;

import static dd.config.WindowProperties.UNIT;
import static org.junit.Assert.assertEquals;

public class SnakePartTest {

    @Test
    public void shouldChangePositionAfterMovingOneUnitRight() {
        SnakePart snakePart = new SnakePart(new Point2D(0,0));
        snakePart.move(Direction.RIGHT);
        assertEquals(0, Double.compare(snakePart.getPosition().getX(), -UNIT));
        assertEquals(0, Double.compare(snakePart.getPosition().getY(), 0));
    }
}