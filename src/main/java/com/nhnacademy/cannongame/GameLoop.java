package com.nhnacademy.cannongame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class GameLoop extends AnimationTimer {

    private final MovableWorld world;
    private final GraphicsContext gc;
    private final Label fpsLabel;

    private long lastUpdate;
    private long lastFpsTime;
    private int frameCount;

    public GameLoop(MovableWorld world, GraphicsContext gc, Label fpsLabel) {
        this.world = world;
        this.gc = gc;
        this.fpsLabel = fpsLabel;
        this.lastUpdate = 0;
    }

    @Override
    public void handle(long now) {
        if (lastUpdate == 0) {
            lastUpdate = now;
            lastFpsTime = now;
            return;
        }

        double deltaTime = (now - lastUpdate) / 1_000_000_000.0;

        world.update(deltaTime); // 월드 상태 업데이트
        world.draw(gc); // 월드 그리기

        // FPS 계산 및 표시
        frameCount++;
        if (now - lastFpsTime >= 1_000_000_000) { // 1초마다
            int fps = frameCount;
            frameCount = 0;
            lastFpsTime = now;
            // JavaFX UI 스레드에서 라벨 텍스트 업데이트
            Platform.runLater(() -> fpsLabel.setText("FPS: " + fps));
        }

        lastUpdate = now;
    }
}