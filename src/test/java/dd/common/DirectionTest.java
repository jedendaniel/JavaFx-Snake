package dd.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {

    @Test
    public void shouldGetLeftDirectionAfterTurningLeftWhenBeingUpInitially() {
        Direction direction = Direction.UP;
        assertEquals(Direction.LEFT, direction.turnLeft());
    }

    @Test
    public void shouldGetRightDirectionAfterTurningRightWhenBeingUpInitially() {
        Direction direction = Direction.UP;
        assertEquals(Direction.RIGHT, direction.turnRight());
    }

    @Test
    public void shouldGetRightDirectionAfterTurningLeftWhenBeingDownInitially() {
        Direction direction = Direction.DOWN;
        assertEquals(Direction.RIGHT, direction.turnLeft());
    }

    @Test
    public void shouldGetDownDirectionAfterTurningRightWhenBeingRightInitially() {
        Direction direction = Direction.RIGHT;
        assertEquals(Direction.DOWN, direction.turnRight());
    }


}