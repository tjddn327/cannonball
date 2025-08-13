package com.nhnacademy.cannongame;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MovableWorldApp extends Application {

    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;

    private MovableWorld world;
    private Canvas canvas;
    private GraphicsContext gc;
    private Label fpsLabel; // FPS를 표시할 라벨

    @Override
    public void start(Stage primaryStage) {
        // 월드와 캔버스 생성
        world = new MovableWorld(WORLD_WIDTH, WORLD_HEIGHT);
        canvas = new Canvas(WORLD_WIDTH, WORLD_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // FPS 라벨 설정
        fpsLabel = new Label("FPS: 0");
        fpsLabel.setTextFill(Color.WHITE);
        fpsLabel.setStyle("-fx-background-color: black; -fx-padding: 5;");

        // Pane에 캔버스와 라벨을 함께 추가
        Pane root = new Pane();
        root.getChildren().addAll(canvas, fpsLabel);

        // 초기 랜덤 공 10개 생성
        createMovingBalls(10);

        // Scene 구성 및 Stage 설정
        primaryStage.setTitle("Movable Ball World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        // GameLoop 생성 및 시작
        GameLoop gameLoop = new GameLoop(world, gc, fpsLabel);
        gameLoop.start();
    }


    private void createMovingBalls(int count) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            try {
                double radius = random.nextDouble() * 20 + 10;
                double x = random.nextDouble() * (WORLD_WIDTH - 2 * radius) + radius;
                double y = random.nextDouble() * (WORLD_HEIGHT - 2 * radius) + radius;

                Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

                // -100 ~ 100 사이의 랜덤 속도 설정
                double dx = (random.nextDouble() - 0.5) * 200;
                double dy = (random.nextDouble() - 0.5) * 200;
                Vector2D velocity = new Vector2D(dx, dy);


                MovableBallV2 ball = new MovableBallV2(new Point(x, y), radius, color, velocity);

                ball.setVelocity(velocity);

                world.add(ball);
            } catch (IllegalArgumentException ignore) {
                i--;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}