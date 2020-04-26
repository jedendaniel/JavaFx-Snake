package dd;

import dd.collision.Collider;
import dd.collision.CollisionHandler;
import dd.config.WindowProperties;
import dd.food.Food;
import dd.food.FoodSpawner;
import dd.snake.Snake;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static dd.config.GameLoopProperties.FRAME_RATE;
import static dd.snake.SnakeState.LOST;


public class Main extends Application {

    public static final CollisionHandler collisionHandler = new CollisionHandler();
    private Scene scene;
    private Group rootGroup = new Group();
    private Snake snake = new Snake(new Point2D(4, 4), 4);
    private Food food = new Food();
    private FoodSpawner foodSpawner = new FoodSpawner(food, List.of(snake));
    private List<GameObject> gameObjects = List.of(snake, foodSpawner);
    private List<GraphicObject> graphicObjects = List.of(snake, food);

    private InputHandler inputHandler = new InputHandler(snake);

    public void start(Stage stage) {
        initScene(stage);

        final long startTime = System.nanoTime();
        new AnimationTimer() {
            long lastTime = startTime;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastTime > FRAME_RATE) {
                    update();
                    lastTime = currentNanoTime;
                }
            }
        }.start();
    }

    private void update() {
        inputHandler.handleInputOnScene(scene);
        gameObjects.forEach(GameObject::update);
        graphicObjects.forEach(GraphicObject::draw);
        collisionHandler.handleCollisions();
        if (isGameEnded()) {
            endGame();
        }
    }

    private void initScene(Stage stage) {
        graphicObjects.forEach(graphicObject -> rootGroup.getChildren().add(graphicObject.getNode()));
        scene = new Scene(rootGroup, WindowProperties.WIDTH, WindowProperties.HEIGHT);
        scene.setFill(Color.BLACK);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
    }

    private boolean isGameEnded() {
        return snake.getState() == LOST;
    }

    private void endGame() {
        System.out.println("The end, you lost");
    }

    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
