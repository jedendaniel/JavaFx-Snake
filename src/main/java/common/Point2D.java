package common;

public class Point2D {
    private int x;
    private int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point2D add(Point2D point2D) {
        return new Point2D(x- point2D.getX(), y - point2D.getY());
    }

    public boolean isTheSame(Point2D point2D) {
        return x == point2D.getX() && y == point2D.getY();
    }
}
