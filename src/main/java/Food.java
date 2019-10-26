import collision.Collider;
import common.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle implements Collider {

    public Food() {
        super(100,100, Color.RED);
        this.setX(100);
        this.setY(100);
        this.setHeight(Main.UNIT);
        this.setWidth(Main.UNIT);
    }

    public void setPosition(Point2D position) {
        this.setX(position.getX());
        this.setY(position.getY());
    }

    @Override
    public boolean isCollision(Collider collider) {
        return this.getPosition().isTheSame(collider.getPosition());
    }

    @Override
    public Point2D getPosition() {
        return new Point2D((int)this.getX(), (int)this.getY());
    }

    @Override
    public void handleCollision(Collider collider) {
    }
}
