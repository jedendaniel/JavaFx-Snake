import collision.Collider;
import common.Point2D;
import common.SimpleDirection;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SnakePart extends Rectangle implements Collider {
    private static final Color COLOR = Color.GREEN;
    private static final int HEIGHT = Main.UNIT;
    private static final int WIDTH = Main.UNIT;

    public SnakePart(Point2D position) {
        super(position.getX(), position.getY(), COLOR);
        this.setX(position.getX());
        this.setY(position.getY());
        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
    }

    public void move(SimpleDirection direction) {
        this.setX(this.getX() + Main.UNIT * direction.getX());
        this.setY(this.getY() + Main.UNIT * direction.getY());
    }

    @Override
    public Point2D getPosition() {
        return new Point2D((int) this.getX(), (int) this.getY());
    }

    @Override
    public boolean isCollision(Collider collider) {
        return this.getPosition().isTheSame(collider.getPosition());
    }

    @Override
    public void handleCollision(Collider collider) {
    }
}
