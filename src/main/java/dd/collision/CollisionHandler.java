package dd.collision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static dd.util.CollectionsUtil.mergeLists;

public class CollisionHandler {

    private List<Collider> staticColliders = new ArrayList<>();
    private List<Collider> dynamicColliders = new ArrayList<>();
    private List<Collider> allColliders = new ArrayList<>();

    public CollisionHandler() {
    }

    public CollisionHandler(List<Collider> staticColliders, List<Collider> dynamicColliders) {
        this.staticColliders = staticColliders;
        this.dynamicColliders = dynamicColliders;
        this.allColliders.addAll(mergeLists(staticColliders, dynamicColliders));
    }

    public void handleCollisions() {
        getDetectedCollisions().forEach(entry -> handleCollision(entry.getKey(), entry.getValue()));
    }

    private List<Map.Entry<Collider, Collider>> getDetectedCollisions() {
        List<Collider> allColliders = new ArrayList<>(dynamicColliders);
        allColliders.addAll(staticColliders);
        return IntStream.range(0, dynamicColliders.size())
                .mapToObj(i -> IntStream.range(i+1, allColliders.size())
                        .mapToObj(j -> Map.entry(dynamicColliders.get(i), allColliders.get(j)))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .filter(entry -> !entry.getKey().equals(entry.getValue()))
                .filter(entry -> isCollision(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private boolean isCollision(Collider collider1, Collider collider2) {
        return Double.compare(collider1.getPosition().distance(collider2.getPosition()), 0d) == 0;
    }

    private void handleCollision(Collider collider1, Collider collider2) {
        collider1.handleCollision(collider2);
        collider2.handleCollision(collider1);
    }

    public List<Collider> getAllColliders() {
        return allColliders;
    }

    public List<Collider> getStaticColliders() {
        return staticColliders;
    }

    public void setStaticColliders(List<Collider> staticColliders) {
        this.staticColliders = staticColliders;
    }

    public List<Collider> getDynamicColliders() {
        return dynamicColliders;
    }

    public void setDynamicColliders(List<Collider> dynamicColliders) {
        this.dynamicColliders = dynamicColliders;
    }

    public void addStaticCollider(Collider collider) {
        staticColliders.add(collider);
        allColliders.add(collider);
    }

    public void addDynamicCollider(Collider collider) {
        dynamicColliders.add(collider);
        allColliders.add(collider);
    }
}
