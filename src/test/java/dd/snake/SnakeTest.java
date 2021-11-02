package dd.snake;

import dd.common.Direction;
import javafx.geometry.Point2D;
import org.junit.Assert;
import org.junit.Test;

import static dd.snake.SnakeState.EATING;
import static dd.snake.SnakeState.MOVING;
import static dd.snake.SnakeState.WIN;

public class SnakeTest {

    private static final Point2D HEAD_POSITION = new Point2D(1, 1);
    private static final int INITIAL_SIZE = 5;

    private final Snake snake = new Snake(HEAD_POSITION, INITIAL_SIZE);

    @Test
    public void shouldCreateSnake() {
        Assert.assertEquals(HEAD_POSITION, snake.getPosition());
        Assert.assertEquals(INITIAL_SIZE, snake.getSize());
        Assert.assertEquals(MOVING, snake.getSnakeState());
    }

    @Test
    public void shouldMoveProperlyWhileTurning() {
        Point2D firstCheckPoint = snake.getPosition().add(Direction.RIGHT.get2D());
        Point2D secondCheckPoint = firstCheckPoint.add(Direction.UP.get2D());
        Point2D thirdCheckPoint = secondCheckPoint.add(Direction.RIGHT.get2D());

        snake.setSnakeState(MOVING);
        // first frame
        snake.setDirection(Direction.RIGHT);
        snake.update();
        Assert.assertEquals(firstCheckPoint, snake.getPosition());
        // second frame
        snake.setDirection(Direction.UP);
        snake.update();
        Assert.assertEquals(secondCheckPoint, snake.getPosition());
        Assert.assertEquals(firstCheckPoint, snake.getBody().get(1).getPosition());
        // third frame
        snake.setDirection(Direction.RIGHT);
        snake.update();
        Assert.assertEquals(thirdCheckPoint, snake.getPosition());
        Assert.assertEquals(secondCheckPoint, snake.getBody().get(1).getPosition());
        Assert.assertEquals(firstCheckPoint, snake.getBody().get(2).getPosition());
    }

    @Test
    public void shouldGrowWhenInStateEating() {
        snake.setSnakeState(EATING);
        snake.update();
        Assert.assertEquals(INITIAL_SIZE + 1, snake.getSize());
        Assert.assertEquals(HEAD_POSITION.add(snake.getDirection().get2D()), snake.getPosition());
    }

//    @Test
//    public void shouldBeEatingWhenCollidingWithFood() {
//        Collider foodCollider = mock(Collider.class);
//        when(foodCollider.getGameObjectType()).thenReturn(GameObjectType.FOOD);
//        snake.handleCollision(foodCollider);
//        Assert.assertEquals(EATING, snake.getSnakeState());
//    }
//
//    @Test
//    public void shouldLoseWhenCollidingWithItself() {
//        Collider snakeCollider = mock(Collider.class);
//        when(snakeCollider.getGameObjectType()).thenReturn(GameObjectType.SNAKE);
//        snake.handleCollision(snakeCollider);
//        Assert.assertEquals(LOST, snake.getSnakeState());
//    }

    @Test
    public void shouldWinWhenReachedMaxSize() {
        snake.setMaxSize(snake.getSize());
        snake.setSnakeState(EATING);
        snake.update();
        Assert.assertEquals(WIN, snake.getSnakeState());
    }
}