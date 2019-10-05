public enum SimpleDirection {
    UP(0, 1),
    RIGHT(-1, 0),
    DOWN(0, -1),
    LEFT(1, 0);

    private int x;
    private int y;

    SimpleDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SimpleDirection getNext(SimpleDirection direction) {
        int next = direction.ordinal() + 1 != SimpleDirection.values().length ? direction.ordinal() + 1 : 0;
        return SimpleDirection.values()[next];
    }

    public SimpleDirection getPrevious(SimpleDirection direction) {
        int previous = direction.ordinal() - 1 < 0 ? SimpleDirection.values().length - 1 : direction.ordinal() - 1;
        return SimpleDirection.values()[previous];
    }
}
