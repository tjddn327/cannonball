package com.nhnacademy.cannongame;

public class BoundedWorld extends MovableWorld {

    public BoundedWorld(int width, int height) {
        super(width, height);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        // 1. 공 이동
        for(Ball ball : getBalls()){
            if(ball instanceof MovableBall){
                ((MovableBall) ball).move(deltaTime);
            }
        }

        // 2. 벽과 충돌 처리
        for(Ball ball : getBalls()){
            if(ball instanceof BoundedBall){
                CollisionDetector.checkWallCollision((BoundedBall)ball, 0, 0, getWidth(), getHeight());
            }
        }

        // 3. 공과 공의 충돌 처리
        for (int i = 0; i < getBalls().size(); i++) {
            for (int j = i + 1; j < getBalls().size(); j++) {
                // 각 쌍을 한 번만 검사

            }
        }
    }

    @Override
    public void add(Ball ball) {

        if(ball instanceof BoundedBall){
            ((BoundedBall) ball).setBounds(0,0,getWidth(),getHeight());
        }
        super.add(ball);
    }
}
