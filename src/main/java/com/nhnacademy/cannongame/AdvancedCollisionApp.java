package com.nhnacademy.cannongame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdvancedCollisionApp extends Application {

    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;

    private BoundedWorld world;
    private final Random random = new Random();

    @Override
    public void start(Stage stage) {
        // --- UI 컨트롤 및 레이아웃 설정 ---
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(WORLD_WIDTH, WORLD_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 컨트롤 패널
        CheckBox gravityCheck = new CheckBox("중력 활성화");
        Label countLabel = new Label("공 개수:");
        Slider ballCountSlider = new Slider(5, 50, 15); // 최소 5, 최대 50, 기본 15
        ballCountSlider.setShowTickMarks(true);
        ballCountSlider.setShowTickLabels(true);
        ballCountSlider.setMajorTickUnit(5);

        HBox controlBox = new HBox(20, gravityCheck, countLabel, ballCountSlider);
        controlBox.setPadding(new Insets(10));
        controlBox.setAlignment(Pos.CENTER);

        // FPS 표시 라벨
        Label fpsLabel = new Label("FPS: 0");
        VBox topPanel = new VBox(controlBox, fpsLabel);
        topPanel.setAlignment(Pos.CENTER);

        root.setCenter(canvas);
        root.setTop(topPanel);

        // --- 월드 및 게임 루프 초기화 ---
        world = new BoundedWorld(WORLD_WIDTH, WORLD_HEIGHT);
        createBalls((int) ballCountSlider.getValue()); // 초기 공 생성

        // 슬라이더 값 변경 시 공 재생성
        ballCountSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            createBalls(newVal.intValue());
        });

        // 애니메이션 타이머 (게임 루프)
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private int frameCount = 0;
            private long lastFpsTime = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    lastFpsTime = now;
                    return;
                }

                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;

                // 중력 적용 (체크박스 확인)
                if (gravityCheck.isSelected()) {
                    world.applyGravity(deltaTime);
                }

                // 월드 업데이트 (이동 및 충돌 처리)
                world.update(deltaTime);

                // 그리기
                render(gc);

                // FPS 계산
                frameCount++;
                if (now - lastFpsTime >= 1_000_000_000) {
                    fpsLabel.setText("FPS: " + frameCount);
                    frameCount = 0;
                    lastFpsTime = now;
                }

                lastUpdate = now;
            }
        };

        // --- 창 설정 및 표시 ---
        stage.setTitle("Advanced Collision Simulation");
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();

        timer.start();
    }

    /**
     * 지정된 개수만큼 랜덤한 공을 생성하여 월드에 추가합니다.
     */
    private void createBalls(int count) {
        world.clear();
        for (int i = 0; i < count; i++) {
            double radius = 10 + random.nextInt(15);
            Point center = new Point(
                    radius + random.nextInt((int) (WORLD_WIDTH - 2 * radius)),
                    radius + random.nextInt((int) (WORLD_HEIGHT - 2 * radius))
            );
            Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            Vector2D velocity = new Vector2D(random.nextInt(200) - 100, random.nextInt(200) - 100);

            world.add(new BoundedBall(center, radius, color, velocity));
        }
    }

    /**
     * 캔버스를 지우고 월드의 모든 공을 그립니다.
     */
    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        world.draw(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}