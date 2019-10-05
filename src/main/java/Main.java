import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    public static final int UNIT = 20;
    private final static int RATE_IN_NANOS = 200 * 1000000;

    private Snake snake;

    public void init() {
        snake = new Snake(new Point2D(200, 200));
    }

    public void start(Stage stage) throws Exception {

        Group root = new Group();
        root.getChildren().add(snake.getGroup());

        Scene scene = new Scene(root, 500, 500);
        scene.setFill(Color.BLACK);
        mapInput(scene);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();

        final long startTime = System.nanoTime();
        new AnimationTimer() {
            long lastTime = startTime;
            public void handle(long currentNanoTime) {
                if(currentNanoTime - lastTime > RATE_IN_NANOS) {
                    snake.move();
                    lastTime = currentNanoTime;
                }
            }
        }.start();
    }

    public void stop() {
    }

    private void mapInput(Scene scene) {
        scene.setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode().getCode()) {
                        case 37:
                            snake.turn(false);
                            break;
                        case 39:
                            snake.turn(true);
                            break;
                    }
                }
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
