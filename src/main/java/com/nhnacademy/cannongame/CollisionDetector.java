package com.nhnacademy.cannongame;

public class CollisionDetector {
    public static class WallCollision{
        public enum Wall {
            TOP,
            BOTTOM,
            LEFT,
            RIGHT
        }
        public Wall wall;
        public double penetration;

        public WallCollision(Wall wall, double penetration){
            this.wall = wall;
            this.penetration = penetration;
        }

    }

    /**
     * 충돌했는가?
     * @param ball
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     * return 각 방향 없으면 null
     */
    public static WallCollision checkWallCollision(BoundedBall ball, double minX, double minY, double maxX, double maxY){
        double ballX = ball.getCenter().getX();
        double ballY = ball.getCenter().getY();
        double ballR = ball.getRadius();

        double bL = minX - (ballX - ballR);
        double bR = maxX - (ballX - ballR);
        double bT = minY - (ballY - ballR);
        double bB = maxY - (ballY - ballR);

        if(bL > 0){
            return new WallCollision(WallCollision.Wall.LEFT, bL);
        }

        if(bR > 0){
            return new WallCollision(WallCollision.Wall.RIGHT, bR);
        }
        if(bT > 0){
            return new WallCollision(WallCollision.Wall.TOP, bT);
        }
        if(bB > 0){
            return new WallCollision(WallCollision.Wall.BOTTOM, bB);
        }

        return null;
    }


    /**
     * 상하좌우 반응
     * @param ball
     * @param collision
     */
    public static void resolveWallCollision(BoundedBall ball, WallCollision collision){
        if(collision == null){
            return;
        }

        switch (collision.wall) {
            case LEFT:
                ball.getVelocity().setX(-ball.getVelocity().getX()); //반전
                ball.setCenter(new Point(ball.getCenter().getX() + collision.penetration, ball.getCenter().getY())); //위치 보정 오른쪽밀기
                break;

            case RIGHT:
                ball.getVelocity().setX(-ball.getVelocity().getX());
                ball.setCenter(new Point(ball.getCenter().getX() - collision.penetration, ball.getCenter().getY())); //위치 보정 왼쪽 밀기
                break;

            case TOP:
                ball.getVelocity().setY(-ball.getVelocity().getY());
                ball.setCenter(new Point(ball.getCenter().getY() + collision.penetration, ball.getCenter().getY()));
                break;

            case BOTTOM:
                ball.getVelocity().setY(-ball.getVelocity().getY());
                ball.setCenter(new Point(ball.getCenter().getY() - collision.penetration, ball.getCenter().getY()));
                break;

        }
    }
}

