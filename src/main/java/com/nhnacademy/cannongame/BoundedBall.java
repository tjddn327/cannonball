package com.nhnacademy.cannongame;

import javafx.scene.paint.Color;

public class BoundedBall extends MovableBall{
    public double minX;
    public double maxX;
    public double minY;
    public double maxY;
    public BoundedBall(Point center, double radius, Color color, Vector2D velocity) { super(center, radius, color, velocity); }

    public BoundedBall(Point center, double radius, Color color) {
        super(center, radius, color);
        this.minX = Double.MIN_VALUE;
        this.minY = Double.MIN_VALUE;
        this.maxX = Double.MAX_VALUE;
        this.maxY = Double.MAX_VALUE;
    }

    public BoundedBall(Point center, double radius){
        this(center, radius, Color.RED);
    }

    public void setBounds(double minX, double minY, double maxX, double maxY) {
        this.minX = minX + getRadius();
        this.maxX = maxX - getRadius();

        this.minY = minY + getRadius();
        this.maxY = maxY - getRadius();
    }

    @Override
    public void move(double deltaTime) {
        super.move(deltaTime);
//        Point nextPoint = getCenter().add(getVelocity().multiply(deltaTime));
//
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
        Point currentCenter = getCenter();

        // X축 충돌 확인
        if (currentCenter.getX() < this.minX) {
            // 속도 반전
            getVelocity().setX(-getVelocity().getX());
            // 위치 보정: 공의 중심을 정확히 경계선에 위치시킴
            moveTo(new Point(this.minX, currentCenter.getY()));

        } else if (currentCenter.getX() > this.maxX) {
            getVelocity().setX(-getVelocity().getX());
            moveTo(new Point(this.maxX, currentCenter.getY()));
        }

        // Y축 충돌 확인
        if (currentCenter.getY() < this.minY) {
            getVelocity().setY(-getVelocity().getY());
            moveTo(new Point(currentCenter.getX(), this.minY));

        } else if (currentCenter.getY() > this.maxY) {
            getVelocity().setY(-getVelocity().getY());
            moveTo(new Point(currentCenter.getX(), this.maxY));
        }
    }

}
