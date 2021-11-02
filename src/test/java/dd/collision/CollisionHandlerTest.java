package dd.collision;

import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CollisionHandlerTest {

    private Collider staticCollider1 = mock(Collider.class);
    private Collider staticCollider2 = mock(Collider.class);
    private Collider dynamicCollider1 = mock(Collider.class);
    private Collider dynamicCollider2 = mock(Collider.class);

    private final CollisionHandler collisionHandler = new CollisionHandler(List.of(staticCollider1, staticCollider2), List.of(dynamicCollider1, dynamicCollider2));

    @Before
    public void setUp() {
        when(staticCollider1.getPosition()).thenReturn(new Point2D(11, 11));
        when(staticCollider2.getPosition()).thenReturn(new Point2D(22, 22));
        when(dynamicCollider1.getPosition()).thenReturn(new Point2D(33, 33));
        when(dynamicCollider2.getPosition()).thenReturn(new Point2D(44, 44));
    }

    @Test
    public void shouldDetectCollisionBetweenDynamicAndStaticCollidersWhenTheirPositionsAreTheSame() {
        when(dynamicCollider1.getPosition()).thenReturn(new Point2D(1, 1));
        when(staticCollider1.getPosition()).thenReturn(new Point2D(1, 1));
        collisionHandler.handleCollisions();
        verify(dynamicCollider1).handleCollision(any());
        verify(staticCollider1).handleCollision(any());
    }

    @Test
    public void shouldNotDetectCollisionBetweenDynamicAndStaticCollidersWhenTheirPositionsAreDifferent() {
        collisionHandler.handleCollisions();
        verify(dynamicCollider1, times(0)).handleCollision(any());
        verify(staticCollider1, times(0)).handleCollision(any());
    }

    @Test
    public void shouldDetectCollisionBetweenDynamicCollidersWhichPositionIsTheSame() {
        when(dynamicCollider1.getPosition()).thenReturn(new Point2D(1, 1));
        when(dynamicCollider2.getPosition()).thenReturn(new Point2D(1, 1));
        collisionHandler.handleCollisions();
        verify(dynamicCollider1).handleCollision(any());
        verify(dynamicCollider2).handleCollision(any());
    }

    @Test
    public void shouldNotDetectCollisionBetweenDynamicCollidersWhenTheirPositionsAreDifferent() {
        collisionHandler.handleCollisions();
        verify(dynamicCollider1, times(0)).handleCollision(any());
        verify(dynamicCollider2, times(0)).handleCollision(any());
    }

    @Test
    public void shouldNotDetectCollisionBetweenStaticCollidersEvenWhenTheirPositionIsTheSame() {
        when(staticCollider1.getPosition()).thenReturn(new Point2D(1, 1));
        when(staticCollider2.getPosition()).thenReturn(new Point2D(1, 1));
        collisionHandler.handleCollisions();
        verify(staticCollider1, times(0)).handleCollision(any());
        verify(staticCollider1, times(0)).handleCollision(any());
    }

    @Test
    public void shouldNotDetectCollisionWhenItIsOnlyOneCollider() {
        CollisionHandler handler = new CollisionHandler(List.of(dynamicCollider1), List.of());
        handler.handleCollisions();
        verify(dynamicCollider1, times(0)).handleCollision(any());
    }
}