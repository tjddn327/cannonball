package com.nhnacademy.cannongame;

public class MovableWorld extends World{

    public MovableWorld(int width, int height) {
        super(width, height); // 부모 클래스 생성자 호출
    }

    public void update(double deltaTime) {
        for (Ball ball : getBalls()) {
            if (ball instanceof MovableBall) {
                ((MovableBall) ball).move(deltaTime);
            }
        }
    }
}
