package com.nhnacademy.cannongame;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        if(ball.getCenter().getX() - ball.getRadius() < 0 || ball.getCenter().getY() - ball.getRadius() < 0){
            throw new IllegalArgumentException("0보다 커야함");
        } else if(ball.getCenter().getX() + ball.getRadius() > width){
            throw new IllegalArgumentException("월드의 너비보다 작거나 같아야함");
        } else if(ball.getCenter().getY() + ball.getRadius() > height){
            throw new IllegalArgumentException("월드의 높이보다 작거나 같아야함");
        }

        this.balls.add(ball);
    }

    public void remove(Ball ball){
        if(ball == null){
            throw new NoSuchElementException("ball == null");
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

    private void isInBounds(Ball ball){}
}
