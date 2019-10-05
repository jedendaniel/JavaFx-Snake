import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SnakePart extends Rectangle{
    private static final Color COLOR = Color.GREEN;
    private static final int HEIGHT = Main.UNIT;
    private static final int WIDTH = Main.UNIT;

    private SimpleDirection direction;

    public SnakePart(Point2D position, SimpleDirection direction) {
        super(position.getX(), position.getY(), COLOR);
        this.setX(position.getX());
        this.setY(position.getY());
        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.direction = direction;
    }

    public void move() {
        this.setX(this.getX() + Main.UNIT * direction.getX());
        this.setY(this.getY() + Main.UNIT * direction.getY());
    }

    public SimpleDirection getDirection() {
        return direction;
    }

    public void setDirection(SimpleDirection direction) {
        this.direction = direction;
    }

    public Point2D getPosition() {
        return new Point2D((int)this.getX(), (int)this.getY());
    }
}
