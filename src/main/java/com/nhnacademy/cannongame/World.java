package com.nhnacademy.cannongame;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javafx.scene.canvas.GraphicsContext;

public class World {
    private final int width;
    private final int height;
    private final List<Ball> balls = new ArrayList<>();

    public World(int width, int height) {
        if (width*height <= 0){
            throw new IllegalArgumentException("width*height <= 0");
        }
        this.width = width;
        this.height = height;
    }

    public void add(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException("ball == null");
        }

        if (!isInBounds(ball)) {
            throw new IllegalArgumentException("공이 월드 경계를 벗어납니다.");
        }

        this.balls.add(ball);
    }

    public void remove(Ball ball){
        if (!this.balls.contains(ball)) {
            throw new NoSuchElementException("제거할 공이 월드에 존재하지 않습니다.");
        }

        this.balls.remove(ball);
    }

    public void clear(){
        balls.clear();
    }

    public ArrayList<Ball> getBalls(){
        return new ArrayList<>(balls);
    }

    public int getBallCount(){
        return balls.size();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void draw(GraphicsContext gc){
        gc.clearRect(0, 0, getWidth(), getHeight());

        for(Ball ball : balls){
            if (ball instanceof PaintableBall){
                ((PaintableBall) ball).draw(gc);
            }
        }
    }

    private boolean isInBounds(Ball ball){
        Point center = ball.getCenter();
        double radius = ball.getRadius();

        return (center.getX() - radius >= 0) && (center.getX() + radius <= this.width) &&
                (center.getY() - radius >= 0) && (center.getY() + radius <= this.height);
    }
}
