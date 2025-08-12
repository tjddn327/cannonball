package com.nhnacademy.cannongame;

import javafx.scene.paint.Color;

public class MovableBallV2 extends PaintableBall {
    private Vector2D velocity;
    private Vector2D acceleration;

    public MovableBallV2(Point center, double radius, Color color) {
        super(center, radius, color);
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
    }


    public MovableBallV2(Point center, double radius, Color color, Vector2D velocity) {
        super(center, radius, color);
        if (velocity == null) {
            this.velocity = new Vector2D(0, 0);
        } else {
            this.velocity = velocity;
        }
        this.acceleration = new Vector2D(0, 0);
    }

    public Vector2D getVelocity() {
        return velocity;
    }


    public void applyForce(Vector2D force) {
        // 질량(mass)을 간단히 반지름으로 가정합니다.
        double mass = getRadius();
        // 질량이 0인 경우를 방지합니다.
        if (mass == 0) return;

        Vector2D accFromForce = force.multiply(1.0 / mass);
        this.acceleration = this.acceleration.add(accFromForce);
    }


    public void update(double deltaTime) {
        this.velocity = this.velocity.add(this.acceleration.multiply(deltaTime));

        moveTo(getCenter().add(this.velocity.multiply(deltaTime)));

        this.acceleration = new Vector2D(0, 0);
    }

    public void limitSpeed(double maxSpeed) {
        if (velocity.magnitude() > maxSpeed) {
            velocity = velocity.normalize().multiply(maxSpeed);
        }
    }
}