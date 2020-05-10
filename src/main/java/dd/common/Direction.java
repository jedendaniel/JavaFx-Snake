package dd.common;

import javafx.geometry.Point2D;

public enum Direction {
    DOWN(0, 1),
    LEFT(-1, 0),
    UP(0, -1),
    RIGHT(1, 0);

    private int x;
    private int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point2D get2D() {
        return new Point2D(x, y);
    }

    public Direction turnRight() {
        int right = this.ordinal() + 1 != Direction.values().length ? this.ordinal() + 1 : 0;
        return Direction.values()[right];
    }

    public Direction turnLeft() {
        int left = this.ordinal() - 1 < 0 ? Direction.values().length - 1 : this.ordinal() - 1;
        return Direction.values()[left];
    }
}
