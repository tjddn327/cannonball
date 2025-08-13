package com.nhnacademy.cannongame;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        if (x < 0 || y <0){
            throw new IllegalArgumentException("음수쓰지마셈");
        }
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Point other) {
        // TODO: 피타고라스 정리를 사용하여 거리 계산
        // sqrt((x2-x1)² + (y2-y1)²)
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
    }

    public Point add(Vector2D vector) {
        return new Point(x + vector.getX(), y + vector.getY());
    }

    public Point subtract(Vector2D vector) {return new Point(x - vector.getX(), y - vector.getY());}

    public Vector2D subtract(Point other) {
        return new Vector2D(x - other.x, y - other.y);
    }

}