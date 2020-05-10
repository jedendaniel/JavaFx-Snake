package dd.food;

import dd.collision.Collider;
import dd.common.RandomPoint2D;
import javafx.geometry.Point2D;
import org.junit.Test;

import java.util.List;

import static dd.food.FoodState.SPAWNING;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class FoodSpawnerTest {

    private static final int FIRST_RANDOM_CORD = 1;
    private static final int SECOND_RANDOM_CORD = 2;
    private static final Point2D FIRST_RANDOM_POINT = new Point2D(FIRST_RANDOM_CORD, FIRST_RANDOM_CORD);
    private static final Point2D SECOND_RANDOM_POINT = new Point2D(SECOND_RANDOM_CORD, SECOND_RANDOM_CORD);

    private RandomPoint2D random = mock(RandomPoint2D.class);
    private Food food = mock(Food.class);
    private Collider collider = mock(Collider.class);
    private FoodSpawner foodSpawner = new FoodSpawner(random, food, List.of(collider));

    @Test
    public void shouldSpawnFoodWhenItsStateIsSpawning() {
        when(random.nextPoint2D(anyInt())).thenReturn(FIRST_RANDOM_POINT);
        when(food.getState()).thenReturn(SPAWNING);
        when(collider.getPosition()).thenReturn(new Point2D(123, 123));
        foodSpawner.update();
        verify(food).setPosition(FIRST_RANDOM_POINT);
    }

    @Test
    public void shouldSpawnFoodOnAnotherPositionWhenTheFirstOneIsOccupied() {
        when(random.nextPoint2D(anyInt()))
                .thenReturn(FIRST_RANDOM_POINT)
                .thenReturn(SECOND_RANDOM_POINT);
        when(food.getState()).thenReturn(SPAWNING);
        when(collider.getPosition()).thenReturn(FIRST_RANDOM_POINT);
        foodSpawner.update();
        verify(food).setPosition(SECOND_RANDOM_POINT);
    }
}