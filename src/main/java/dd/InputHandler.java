package dd;

import dd.snake.Snake;
import javafx.scene.Scene;

class InputHandler {
    private final Snake snake;

    InputHandler(Snake snake) {
        this.snake = snake;
    }

    void handleInputOnScene(Scene scene) {
        scene.setOnKeyPressed(
            keyEvent -> {
                switch (keyEvent.getCode().getCode()) {
                    case 37:
                        snake.setDirection(snake.getDirection().turnLeft());
                        break;
                    case 39:
                        snake.setDirection(snake.getDirection().turnRight());
                        break;
                }
            }
        );
    }
}
