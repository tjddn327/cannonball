package com.nhnacademy.cannongame;

public class BallCollision {

    /**
     * 충돌하는지 안하는지
     * @param ball1
     * @param ball2
     * @return 충돌하면 true 안하면 false
     */
    public static boolean areColliding(Ball ball1, Ball ball2){
        double twoCenter = ball1.getCenter().distanceTo(ball2.getCenter());
        return twoCenter < ball1.getRadius() + ball2.getRadius();
    }

    /**
     * 공과 공이 부딪혀서 반씩 밀려나감
     * @param ball1
     * @param ball2
     */
    static void separateBalls(Ball ball1, Ball ball2){
        double distance = ball1.getCenter().distanceTo(ball2.getCenter());
        double overlap = (ball1.getRadius() + ball2.getRadius()) - distance; //겹침의 정도

        if(overlap <= 0) return;

        // 1번공이 2번공을 향하는 방향
        Vector2D normal = new Vector2D(ball2.getCenter().getX() - ball1.getCenter().getX(), ball2.getCenter().getY() - ball1.getCenter().getY()).normalize();

        if(ball1 instanceof MovableBall){
            Point newCenter1 = ball1.getCenter().subtract(normal.multiply(overlap / 2));
            ((MovableBall) ball1).moveTo(newCenter1); //형변환해서 움직이는 공으로 만들기 -> 계산된 새 위치로 즉시이동
        }

        if(ball2 instanceof MovableBall){
            Point newCenter2 = ball2.getCenter().add(normal.multiply(overlap / 2));
            ((MovableBall) ball2).moveTo(newCenter2); //형변환해서 움직이는 공으로 만들기 -> 계산된 새 위치로 즉시이동
        }

    }

    public static void resolveElasticCollision(Ball ball1, Ball ball2){
        if(!(ball1 instanceof MovableBall && ball2 instanceof MovableBall)) return;
        separateBalls(ball1, ball2);

        MovableBall mBall1 = (MovableBall) ball1;
        MovableBall mBall2 = (MovableBall) ball2;

        // 크기가 1인 순수한 방향 벡터(걍 이쉨은 1임)
        Vector2D normal = new Vector2D(mBall2.getCenter().getX() - mBall1.getCenter().getX(), mBall2.getCenter().getY() - mBall1.getCenter().getY()).normalize();

        //상대속도(상대적인 속도 ㅇㅇ)
        Vector2D relativeVelocity = mBall2.getVelocity().subtract(mBall1.getVelocity());

        // 이미 튕겨져 나간거임
        if(relativeVelocity.dot(normal) > 0){
            return;
        }

        // 각 공의 속도 충돌 축 방향 성분 크기계산 (충돌 축 성분 벡터 (Normal Component): 두 공의 중심을 잇는 방향의 속도. 충돌 시 서로에게 직접 영향을 주는 힘)
        double v1_dot = mBall1.getVelocity().dot(normal); // 내적(dot product) -> 그림자의 길이
        double v2_dot = mBall2.getVelocity().dot(normal);

        // 완전한 그림자 벡터
        Vector2D v1_component = normal.multiply(v1_dot); // 속도 중에서 충돌 축 방향으로만 작용하는 완전한 힘의 벡터
        Vector2D v2_component = normal.multiply(v2_dot);

        // 내 속도 중 상대방과 부딪히지 않은 부분은 그대로 유지하고, 부딪힌 부분은 상대방 것과 맞바꾼다? 뭔
        // 충돌 축 성분 (Normal Component): 충돌에 직접 관여하는 힘. 이 부분은 서로 교환
        // 접선 성분 (Tangent Component): 충돌과 무관하게 스쳐 지나가는 힘. 이 부분은 각자 그대로 유지
        // 나의 새 속도 = 나의 원래 접선 성분 + 상대방의 원래 충돌 축 성분

        // ball1의 전체속도 - ball1의 충돌 축 성분 = ball1의 접선성분 subtract 부분
        // ball1의 접선성분 + ball2의 충돌 축 성분 = ball1의 최종 새 속도
        mBall1.setVelocity(mBall1.getVelocity().subtract(v1_component).add(v2_component));
        mBall2.setVelocity(mBall2.getVelocity().subtract(v2_component).add(v1_component));

    }

}
