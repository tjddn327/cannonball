package com.nhnacademy.cannongame;

public class Ball {
    private Point center;
    private double radius;

    // 생성자 - 위치 지정 필수
    public Ball(Point center, double radius) {
        if(radius <= 0) {
            throw new IllegalArgumentException("반지름은 음수가 안됩니다.");
        }
        if(center == null) {
            throw new NullPointerException("팍씨 눌쓰지말라고");
        }
        this.center = center;
        this.radius = radius;
        // TODO: null 체크, 유효성 검사
    }

    public Ball(double x, double y, double radius) {
        // TODO: Point 생성하여 다른 생성자 호출
        this(new Point(x, y), radius); //이걸로 스껄하기
    }

    // Getter 메서드
    public Point getCenter() {
        // TODO: center 반환
        return center; // 반환해준다메
    }

    public double getRadius() {
        // TODO: radius 반환
        return radius;
    }

    // 위치 이동 메서드
    public void moveTo(Point newCenter) {
        // TODO: null 체크 후 center 업데이트
        if(newCenter == null) {
            throw new NullPointerException("팍씨 눌쓰지말라고");
        }
        this.center = newCenter;

    }

    /**
     * 반지름안에 있어야함
     * @param p
     * @return 안에있으면 true, 없으면 false
     */
    // contains 메서드
    public boolean contains(Point p) {
        // TODO: center.distanceTo(p) 활용
        return center.distanceTo(p) <= radius;
    }

    /**
     * 위에랑 똑같음
     * @param x
     * @param y
     * @return true or false
     */
    // contains 메서드
    public boolean contains(double x, double y) {
        return contains(new Point(x, y)); // 위에꺼 호출해서 하는거
    }

    public double getArea(){
        return Math.PI * Math.pow(this.radius, 2);
    }

    public double getCir(){
        return 2 * Math.PI * this.radius;
    }

    @Override
    public String toString(){
        return String.format("Ball(center=%s, radius=%.1f)", center.toString(), radius);
    }

    public boolean isColliding(Ball other){
        if(other == null){
            return false;
        }

        double dis = this.getCenter().distanceTo(other.getCenter());

        double sum = this.getRadius() + other.getRadius();

        return dis < sum;
    }

}
