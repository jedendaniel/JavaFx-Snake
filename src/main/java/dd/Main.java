package dd;

import dd.collision.CollisionHandler;
import dd.config.Properties;
import dd.food.Food;
import dd.food.FoodSpawner;
import dd.snake.Snake;
import dd.snake.SnakeState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

import static dd.config.Properties.FRAME_RATE;


public class Main extends Application {

    public static final CollisionHandler collisionHandler = new CollisionHandler();
    private Scene scene;
    private Group rootGroup = new Group();
    private Snake snake = new Snake(new Point2D(4, 4), 4);
    private Food food = new Food();
    private FoodSpawner foodSpawner = new FoodSpawner(food, collisionHandler.getAllColliders());
    private List<GameObject> gameObjects = List.of(snake, foodSpawner);
    private List<GraphicObject> graphicObjects = List.of(snake, food);
    private GameState gameState = GameState.ONGOING;

    private InputHandler inputHandler = new InputHandler(snake);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initScene(stage);

        final long startTime = System.nanoTime();
        new AnimationTimer() {
            long lastTime = startTime;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastTime > FRAME_RATE && gameState == GameState.ONGOING) {
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
            handleGameEnd();
        }
    }

    private void initScene(Stage stage) {
        graphicObjects.forEach(graphicObject -> rootGroup.getChildren().add(graphicObject.getNode()));
        scene = new Scene(rootGroup, Properties.WIDTH, Properties.HEIGHT);
        scene.setFill(Color.BLACK);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
    }

    private boolean isGameEnded() {
        return snake.getSnakeState() == SnakeState.WIN || snake.getSnakeState() == SnakeState.LOST;
    }

    private void handleGameEnd() {
        if (snake.getSnakeState() == SnakeState.WIN) {
            handleGameWin();
        } else {
            handleGameLost();
        }
    }

    private void handleGameWin() {
        gameState = GameState.ENDED;
        System.out.println("The end, you win");
    }

    private void handleGameLost() {
        gameState = GameState.ENDED;
        System.out.println("The end, you lost");
    }
}
