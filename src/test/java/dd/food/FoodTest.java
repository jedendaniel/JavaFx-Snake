package dd.food;

import dd.collision.Collider;
import org.junit.Test;

import static dd.food.FoodState.SPAWNING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class FoodTest {

    @Test
    public void ShouldChangeStateToSpawningAfterCollision() {
        Food food = new Food();
        Collider collider = mock(Collider.class);
        food.handleCollision(collider);
        assertEquals(SPAWNING, food.getState());
    }

}