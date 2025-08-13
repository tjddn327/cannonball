package com.nhnacademy.cannongame;

public class CollisionDetector {

    public enum Wall {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public static class WallCollision {
        private final Wall wall;
        private final double penetration;

        public WallCollision(Wall wall, double penetration) {
            this.wall = wall;
            this.penetration = penetration;
        }

        public Wall getWall() {
            return this.wall;
        }

        public double getPenetration() {
            return this.penetration;
        }
    }

    public static WallCollision checkWallCollision(BoundedBall ball, double minX, double minY, double maxX, double maxY) {
        double ballX = ball.getCenter().getX();
        double ballY = ball.getCenter().getY();
        double radius = ball.getRadius();

        double penetration;

        // 왼쪽 벽
        if ((penetration = (minX + radius) - ballX) > 0) {
            return new WallCollision(Wall.LEFT, penetration);
        }
        // 3. 오른쪽 벽 침투 계산 로직 수정
        if ((penetration = (ballX + radius) - maxX) > 0) {
            return new WallCollision(Wall.RIGHT, penetration);
        }
        // 위쪽 벽
        if ((penetration = (minY + radius) - ballY) > 0) {
            return new WallCollision(Wall.TOP, penetration);
        }
        // 4. 아래쪽 벽 침투 계산 로직 수정
        if ((penetration = (ballY + radius) - maxY) > 0) {
            return new WallCollision(Wall.BOTTOM, penetration);
        }

        return null;
    }

    public static void resolveWallCollision(BoundedBall ball, WallCollision collision) {
        if (collision == null) {
            return;
        }

        Vector2D v = ball.getVelocity();
        Point p = ball.getCenter();

        switch (collision.wall) {
            case LEFT:
                v.setX(-v.getX());
                ball.moveTo(new Point(p.getX() + collision.penetration, p.getY()));
                break;
            case RIGHT:
                v.setX(-v.getX());
                ball.moveTo(new Point(p.getX() - collision.penetration, p.getY()));
                break;
            case TOP:
                v.setY(-v.getY());
                ball.moveTo(new Point(p.getX(), p.getY() + collision.penetration));
                break;
            case BOTTOM:
                v.setY(-v.getY());
                ball.moveTo(new Point(p.getX(), p.getY() - collision.penetration));
                break;
        }
    }
}