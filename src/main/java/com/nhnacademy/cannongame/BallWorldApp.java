package com.nhnacademy.cannongame;

import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BallWorldApp extends Application {

    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;

    private World world;
    private Canvas canvas;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        world = new World(WORLD_WIDTH, WORLD_HEIGHT);
        canvas = new Canvas(WORLD_WIDTH, WORLD_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Pane root = new Pane();
        root.getChildren().add(canvas);

        createRandomBalls(5);

        canvas.setOnMouseClicked(event -> handleMouseClick(event));

        primaryStage.setTitle("Ball World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        draw();
    }


    private void createRandomBalls(int count) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            try {
                double radius = random.nextDouble() * 20 + 10;
                double x = random.nextDouble() * (WORLD_WIDTH - 2 * radius) + radius;
                double y = random.nextDouble() * (WORLD_HEIGHT - 2 * radius) + radius;

                Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

                world.add(new PaintableBall(x, y, radius, color));
            } catch (IllegalArgumentException ignore) {
                i--;
            }
        }
    }


    private void handleMouseClick(MouseEvent event) {
        try {
            PaintableBall newBall = new PaintableBall(event.getX(), event.getY(), 15, Color.BLUE);
            world.add(newBall);
            draw();
        } catch (IllegalArgumentException e) {
            System.err.println("경계선에 너무 가깝게 클릭했습니다: " + e.getMessage());
        }
    }


    private void draw() {
        world.draw(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}