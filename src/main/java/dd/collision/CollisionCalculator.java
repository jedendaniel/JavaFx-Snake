package dd.collision;

public class CollisionCalculator {
    private final Collider colliderA;
    private final Collider colliderB;

    public CollisionCalculator(Collider colliderA, Collider colliderB) {
        this.colliderA = colliderA;
        this.colliderB = colliderB;
    }

    public boolean isCollision() {
        return Double.compare(colliderA.getPosition().distance(colliderB.getPosition()), 0d) == 0;
    }

    public Collider getColliderA() {
        return colliderA;
    }

    public Collider getColliderB() {
        return colliderB;
    }
}
