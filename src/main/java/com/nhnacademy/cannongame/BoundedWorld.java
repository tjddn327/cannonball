package com.nhnacademy.cannongame;

import java.util.List;

public class BoundedWorld extends MovableWorld {
    private static final double GRAVITY = 500.0;

    public BoundedWorld(int width, int height) {
        super(width, height);
    }

    @Override
    public void update(double deltaTime) {
        List<Ball> balls = getBalls();
        // 1. 공 이동
        for(Ball ball : balls){
            if(ball instanceof MovableBall){
                ((MovableBall) ball).move(deltaTime);
            }
        }

        // 2. 벽과 충돌 처리
        for (Ball ball : balls) {
            if (ball instanceof BoundedBall boundedBall) {

                CollisionDetector.WallCollision collision =
                        CollisionDetector.checkWallCollision(boundedBall, 0, 0, getWidth(), getHeight());
                if (collision != null) {
                    CollisionDetector.resolveWallCollision(boundedBall, collision);


                    CollisionDetector.WallCollision secondCollision =
                            CollisionDetector.checkWallCollision(boundedBall, 0, 0, getWidth(), getHeight());
                    if (secondCollision != null) {
                        CollisionDetector.resolveWallCollision(boundedBall, secondCollision);
                    }
                }
            }
        }

        // 3. 공과 공의 충돌 처리

        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                // 각 쌍을 한 번만 검사
                Ball ball1 = balls.get(i);
                Ball ball2 = balls.get(j);

                // 1. 두 공이 충돌했는지 확인
                if (BallCollision.areColliding(ball1, ball2)) {
                    // 2. 충돌했다면 처리 요청
                    BallCollision.resolveElasticCollision(ball1, ball2);
                }
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


    public void applyGravity(double deltaTime) {
        for (Ball ball : getBalls()) {
            if (ball instanceof MovableBall) {
                MovableBall mBall = (MovableBall) ball;
                Vector2D currentVelocity = mBall.getVelocity();
                mBall.setVelocity(new Vector2D(currentVelocity.getX(), currentVelocity.getY() + GRAVITY * deltaTime));
            }
        }
    }
}
