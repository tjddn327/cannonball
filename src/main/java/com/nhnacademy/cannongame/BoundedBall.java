package com.nhnacademy.cannongame;

import javafx.scene.paint.Color;

public class BoundedBall extends MovableBall{
    public double minX;
    public double maxX;
    public double minY;
    public double maxY;

    // 생성자에서 경계 초기화 (경계 없음 상태)
    public BoundedBall(Point center, double radius, Color color) {
        super(center, radius, color);
        // 초기값: 경계가 설정되지 않은 상태를 나타냄
        this.minX = Double.MIN_VALUE;
        this.minY = Double.MIN_VALUE;
        this.maxX = Double.MAX_VALUE;
        this.maxY = Double.MAX_VALUE;
    }

    // 경계 설정 시 공의 중심이 이동 가능한 범위
    public void setBounds(double minX, double minY, double maxX, double maxY) {
        this.minX = minX + getRadius();
        this.maxX = maxX - getRadius();

        this.minY = minY + getRadius();
        this.maxY = maxY - getRadius();
    }

    // move 메서드에서 경계 충돌 처리
    @Override
    public void move(double deltaTime) {
//        // 다음 위치 계산
//        Point nextPoint = getCenter().add(getVelocity().multiply(deltaTime));
//
//        // 경계가 설정된 경우에만 충돌 검사
//        // Double.MIN_VALUE와 Double.MAX_VALUE는 경계가 없음을 의미
//        if (minX > Double.MIN_VALUE && maxX < Double.MAX_VALUE) {
//            if (nextPoint.getX() <= minX || nextPoint.getX() >= maxX) {
//                // 1. 속도 반전
//                getVelocity().setX(-getVelocity().getX());
//                // 2. 위치 보정
//                if(getVelocity().getX() <= minX){
//                    setCenter(new Point(this.minX, nextPoint.getY()));
//                }else{
//                    setCenter(new Point(this.maxX, nextPoint.getY()));
//                }
//            }
//        }
//
//        if (minY > Double.MIN_VALUE && maxY < Double.MAX_VALUE) {
//            if (nextPoint.getY() <= minY || nextPoint.getY() >= maxY) {
//                // 1. 속도 반전
//                getVelocity().setY(-getVelocity().getY());
//                // 2. 위치 보정
//                if(getVelocity().getY() <= minY){
//                    setCenter(new Point(nextPoint.getX(), this.minY));
//                } else{
//                    setCenter(new Point(nextPoint.getX(), this.maxY));
//                }
//            }
//        }

        // 부모 클래스의 move 호출
        super.move(deltaTime);

        CollisionDetector.WallCollision collision = CollisionDetector.checkWallCollision(this, this.minX, this.minY, this.maxX, this.maxY);

        if(collision != null){
            CollisionDetector.resolveWallCollision(this, collision);
        }
    }

}
