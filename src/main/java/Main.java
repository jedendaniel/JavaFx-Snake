import collision.Collider;
import common.Point2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main extends Application {
    public static final int UNIT = 20;
    private final static int RATE_IN_NANOS = 150 * 1000000;
    public static final int WIDTH = 300;
    public static final int HEIGHT = 300;

    private Snake snake;
    private Food food;
    private List<Collider> passiveColliders;
    private KeyInput keyInput = KeyInput.FORWARD;

    public void init() {
        snake = new Snake(new Point2D(200, 200), 8);
        food = new Food();
    }

    public void start(Stage stage) throws Exception {

        initScene(stage);
        initColliders(Stream.concat(snake.getCorp().stream(), List.of(food).stream()).collect(Collectors.toList()));
        spawnFood();

        final long startTime = System.nanoTime();
        new AnimationTimer() {
            long lastTime = startTime;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastTime > RATE_IN_NANOS) {
                    if (keyInput.equals(KeyInput.RIGHT)) {
                        snake.turn(true);
                    }
                    if (keyInput.equals(KeyInput.LEFT)) {
                        snake.turn(false);
                    }
                    handleCollisions();
                    snake.update();
                    lastTime = currentNanoTime;
                    keyInput = KeyInput.FORWARD;
                }
            }
        }.start();
    }

    private void spawnFood() {
        boolean spawned = false;
        Random random = new Random();
        while (!spawned) {
            food.setPosition(new Point2D(random.nextInt(WIDTH / UNIT) * 20, (random.nextInt(HEIGHT / UNIT) * 20)));
            spawned = passiveColliders.stream().noneMatch(collider -> !collider.equals(food) && collider.isCollision(food));
        }
    }

    private void handleCollisions() {
        passiveColliders.stream()
                .filter(collider -> collider.isCollision(snake.getHead())).findFirst()
                .map(Object::getClass)
                .ifPresent(this::checkCollisions);
    }

    private void checkCollisions(Class c) {
        if (c.equals(Food.class)) {
            snake.eat();
            spawnFood();
        }
        if (c.equals(SnakePart.class)) {
            endGame();
        }
    }

    private void endGame() {
        System.out.println("The end, you lost");
    }

    private void initColliders(List<Collider> passiveColliders) {
        this.passiveColliders = passiveColliders;
    }

    private void initScene(Stage stage) {
        Group root = new Group();
        root.getChildren().add(snake.getGroup());
        root.getChildren().add(food);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        mapInput(scene);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
    }

    public void stop() {
    }

    private void mapInput(Scene scene) {
        scene.setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode().getCode()) {
                        case 37:
                            keyInput = KeyInput.LEFT;
                            break;
                        case 39:
                            keyInput = KeyInput.RIGHT;
                            break;
                    }
                }
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
