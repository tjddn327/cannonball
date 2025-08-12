package com.nhnacademy.cannongame;

import javafx.scene.paint.Color;

public class MovableBall extends PaintableBall {
    private Vector2D velocity;


    public MovableBall(Point center, double radius, Color color, Vector2D velocity) {
        super(center, radius, color);
        if (velocity == null) {
            throw new IllegalArgumentException("속도 벡터는 null일 수 없습니다.");
        }
        this.velocity = velocity;
    }


    public MovableBall(Point center, double radius, Color color) {
        this(center, radius, color, new Vector2D(0, 0));
    }


    public MovableBall(Point center, double radius) {
        super(center, radius);
        this.velocity = new Vector2D(0, 0);
    }


    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        if (velocity == null) {
            throw new IllegalArgumentException("속도 벡터는 null일 수 없습니다.");
        }
        this.velocity = velocity;
    }


    public void move(double deltaTime) {
        Vector2D displacement = getVelocity().multiply(deltaTime);

        moveTo(getCenter().add(displacement));

    }


    public void move() {
        move(1.0 / 60.0);
    }
}