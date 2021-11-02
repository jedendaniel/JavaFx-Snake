package dd.collision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollisionHandler {

    private List<Collider> staticColliders = new ArrayList<>();
    private List<Collider> dynamicColliders = new ArrayList<>();
    private List<Collider> allColliders = new ArrayList<>();
    private List<CollisionCalculator> potentialCollisionCalculators = new ArrayList<>();

    public CollisionHandler() {
    }

    public CollisionHandler(List<Collider> staticColliders, List<Collider> dynamicColliders) {
        potentialCollisionCalculators.addAll(getPotentialCollisionsWithinOneList(dynamicColliders));
        potentialCollisionCalculators.addAll(getPotentialCollisionsBetweenTwoLists(dynamicColliders, staticColliders));
    }

    public void handleCollisions() {
        potentialCollisionCalculators.stream()
                .filter(CollisionCalculator::isCollision)
                .forEach(cc -> handleCollision(cc.getColliderA(), cc.getColliderB()));
    }

    private List<CollisionCalculator> getPotentialCollisionsWithinOneList(List<Collider> colliders) {
        return IntStream.range(0, colliders.size())
                .mapToObj(i -> IntStream.range(i + 1, colliders.size())
                        .filter(j -> !colliders.get(i).equals(colliders.get(j)))
                        .mapToObj(j -> new CollisionCalculator(colliders.get(i), colliders.get(j)))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<CollisionCalculator> getPotentialCollisionsBetweenTwoLists(List<Collider> collidersA, List<Collider> collidersB) {
        return collidersA.stream()
                .map(colliderA -> collidersB.stream()
                        .filter(colliderB -> !colliderA.equals(colliderB))
                        .map(colliderB -> new CollisionCalculator(colliderA, colliderB))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<CollisionCalculator> getPotentialCollisions(List<Collider> colliders, Collider collider) {
        return colliders.stream()
                .filter(c -> !c.equals(collider))
                .map(c -> new CollisionCalculator(c, collider)).collect(Collectors.toList());
    }

    private void handleCollision(Collider collider1, Collider collider2) {
        collider1.handleCollision(collider2);
        collider2.handleCollision(collider1);
    }

    public List<Collider> getAllColliders() {
        return allColliders;
    }

    public void addStaticCollider(Collider collider) {
        staticColliders.add(collider);
        allColliders.add(collider);
        potentialCollisionCalculators.addAll(getPotentialCollisions(allColliders, collider));
    }

    public void addDynamicCollider(Collider collider) {
        dynamicColliders.add(collider);
        allColliders.add(collider);
        potentialCollisionCalculators.addAll(getPotentialCollisions(allColliders, collider));
    }
}
