package com.nhnacademy.cannongame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    @Test
    public void testBallCreation() {
        // x, y 좌표로 생성하는 편의 생성자 테스트
        Ball ball = new Ball(new Point(100, 200), 30);
        Point center = ball.getCenter();
        assertEquals(100, center.getX(), 0.001, "X 좌표가 올바르게 설정되지 않았습니다");
        assertEquals(200, center.getY(), 0.001, "Y 좌표가 올바르게 설정되지 않았습니다");
        assertEquals(30, ball.getRadius(), 0.001, "반지름이 올바르게 설정되지 않았습니다");
    }

    @Test
    public void testBallWithPoint() {
        // Point를 사용한 생성자 테스트
        Point originalCenter = new Point(100, 200);
        Ball ball = new Ball(originalCenter, 30);
        Point center = ball.getCenter();
        assertEquals(100, center.getX(), 0.001, "X 좌표가 올바르게 설정되지 않았습니다");
        assertEquals(200, center.getY(), 0.001, "Y 좌표가 올바르게 설정되지 않았습니다");
        assertEquals(30, ball.getRadius(), 0.001, "반지름이 올바르게 설정되지 않았습니다");
    }

    @Test
    public void testMoveTo() {
        Ball ball = new Ball(new Point(50, 75), 20);

        // moveTo 테스트
        Point newCenter = new Point(150, 175);
        ball.moveTo(newCenter);

        Point center = ball.getCenter();
        assertEquals(150, center.getX(), 0.001, "moveTo가 올바르게 작동하지 않습니다");
        assertEquals(175, center.getY(), 0.001, "moveTo가 올바르게 작동하지 않습니다");
    }

    @Test
    public void testRadiusValidation() {
        Point center = new Point(0, 0);

        // 유효하지 않은 반지름 테스트
        assertThrows(IllegalArgumentException.class, () -> {
            new Ball(center, -5); // 음수 반지름은 예외 발생해야 함
        }, "음수 반지름에 대해 예외가 발생하지 않았습니다");

        assertThrows(IllegalArgumentException.class, () -> {
            new Ball(center, 0); // 0 반지름도 예외 발생해야 함
        }, "0 반지름에 대해 예외가 발생하지 않았습니다");
    }


    @Test
    public void testContains() {
        Point center = new Point(100, 100);
        Point innerPoint = new Point(120, 120);
        Point outerPoint1 = new Point(200, 200);
        Point outerPoint2 = new Point(50, 50);
        Point linePoint = new Point(150, 100);

        Ball ball = new Ball(center, 50);

        // 공 내부의 점들
        assertTrue(ball.contains(100, 100), "중심점이 포함되지 않았습니다");
        assertTrue(ball.contains(120, 120), "공 내부 점이 포함되지 않았습니다");

        // Point를 사용한 contains 테스트
        assertTrue(ball.contains(center), "중심점이 포함되지 않았습니다");
        assertTrue(ball.contains(innerPoint), "공 내부 점이 포함되지 않았습니다");

        // 공 외부의 점들
        assertFalse(ball.contains(outerPoint1), "공 외부 점이 포함되었습니다");
        assertFalse(ball.contains(outerPoint2), "공 외부 점이 포함되었습니다");

        // 경계선상의 점 (반지름 거리)
        assertTrue(ball.contains(150, 100), "경계선상의 점이 포함되지 않았습니다");
        assertTrue(ball.contains(linePoint), "경계선상의 점이 포함되지 않았습니다");
    }

    @Test
    @DisplayName("공 생성 시 반지름이 음수면 예외 발생")
    public void testNegativeRadius() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ball(0, 0, -5),
                "반지름이 음수일 때 예외가 발생해야 합니다"
        );
    }

    @Test
    @DisplayName("World에 공 추가 테스트")
    public void testWorldAddBall() {
        World world = new World(500, 500);
        Ball ball = new Ball(new Point(250, 250), 20);

        world.add(ball);
        assertEquals(1, world.getBalls().size());
    }
}