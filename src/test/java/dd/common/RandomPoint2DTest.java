package dd.common;

import javafx.geometry.Point2D;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RandomPoint2DTest {

    @InjectMocks
    private RandomPoint2D randomPoint2D;

    @Mock
    private Random random;

    @Test
    public void shouldReturnRandomPointContainingInOneBoundary() {
        when(random.nextInt(anyInt())).thenReturn(1);
        Point2D point2D = randomPoint2D.nextPoint2D(2);
        Assert.assertTrue(Double.compare(point2D.getX(), 2.0) < 0);
        Assert.assertTrue(Double.compare(point2D.getX(), 2.0) < 0);
    }

    @Test
    public void shouldReturnRandomPointContainingInTwoBoundary() {
        when(random.nextInt(anyInt())).thenReturn(1).thenReturn(2);
        Point2D point2D = randomPoint2D.nextPoint2D(2, 3);
        Assert.assertTrue(Double.compare(point2D.getX(), 2.0) < 0);
        Assert.assertTrue(Double.compare(point2D.getX(), 3.0) < 0);
    }
}