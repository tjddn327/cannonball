package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class BoundedWorldTest {

    private BoundedWorld world;

    @BeforeEach
    public void setUp() {
        world = new BoundedWorld(800, 600);
    }

    @Test
    public void testBoundedWorldCreation() {
        assertEquals(800, world.getWidth(), "World 너비가 올바르지 않습니다");
        assertEquals(600, world.getHeight(), "World 높이가 올바르지 않습니다");
    }

    @Test
    public void testWallCollisionHandling() {
        BoundedBall ball = new BoundedBall(new Point(10, 300), 20, Color.RED);
        ball.setVelocity(new Vector2D(-50, 0)); // 왼쪽으로 이동 (벽 충돌 예정)
        world.add(ball);

        world.update(0.1);

        // 벽 충돌 후 속도가 반대 방향으로 바뀌어야 함
        assertTrue(ball.getVelocity().getX() > 0, "벽 충돌 후 X 속도가 양수가 되어야 합니다");

        // 공이 벽 안쪽에 위치해야 함
        assertTrue(ball.getCenter().getX() >= ball.getRadius(), "공이 왼쪽 벽을 벗어났습니다");
    }

    @Test
    public void testBallBallCollisionHandling() {
        BoundedBall ball1 = new BoundedBall(new Point(100, 300), 20, Color.RED);
        BoundedBall ball2 = new BoundedBall(new Point(200, 300), 20, Color.BLUE);

        ball1.setVelocity(new Vector2D(100, 0)); // 오른쪽으로 이동
        ball2.setVelocity(new Vector2D(-50, 0)); // 왼쪽으로 이동 (충돌 예정)

        world.add(ball1);
        world.add(ball2);

        // 충돌 전 총 운동량
        double initialMomentum = ball1.getVelocity().getX() + ball2.getVelocity().getX();

        world.update(0.5); // 충분한 시간으로 충돌 발생

        // 충돌 후 운동량 보존 확인
        double finalMomentum = ball1.getVelocity().getX() + ball2.getVelocity().getX();
        assertEquals(initialMomentum, finalMomentum, 1.0, "운동량이 보존되지 않았습니다");

        // 공들이 분리되어야 함
        assertFalse(BallCollision.areColliding(ball1, ball2),
                   "충돌 처리 후에도 공들이 겹쳐있습니다");
    }

    @Test
    public void testMultipleBallCollisions() {
        // 여러 공이 한 번에 충돌하는 상황
        BoundedBall ball1 = new BoundedBall(new Point(100, 300), 15, Color.RED);
        BoundedBall ball2 = new BoundedBall(new Point(200, 300), 15, Color.BLUE);
        BoundedBall ball3 = new BoundedBall(new Point(300, 300), 15, Color.GREEN);

        ball1.setVelocity(new Vector2D(150, 0));
        ball2.setVelocity(new Vector2D(0, 0));
        ball3.setVelocity(new Vector2D(-100, 0));

        world.add(ball1);
        world.add(ball2);
        world.add(ball3);

        // 여러 번 업데이트하여 모든 충돌 처리
        for (int i = 0; i < 10; i++) {
            world.update(0.01);
        }

        // 모든 공이 분리되어야 함
        assertFalse(BallCollision.areColliding(ball1, ball2), "공 1과 2가 겹쳐있습니다");
        assertFalse(BallCollision.areColliding(ball2, ball3), "공 2와 3이 겹쳐있습니다");
        assertFalse(BallCollision.areColliding(ball1, ball3), "공 1과 3이 겹쳐있습니다");
    }

    @Test
    public void testCornerBounce() {
        // 모서리에서의 반사 테스트
        BoundedBall ball = new BoundedBall(new Point(25, 25), 20, Color.YELLOW);
        ball.setVelocity(new Vector2D(-50, -50));
        world.add(ball);

        world.update(0.1);

        // 모서리 충돌 후 두 방향 모두 반사되어야 함
        assertTrue(ball.getVelocity().getX() > 0, "모서리 충돌 후 X 속도가 양수가 되어야 합니다");
        assertTrue(ball.getVelocity().getY() > 0, "모서리 충돌 후 Y 속도가 양수가 되어야 합니다");
    }

    @Test
    public void testInheritance() {
        // BoundedWorld가 MovableWorld를 상속받는지 확인
        assertTrue(world instanceof MovableWorld, "BoundedWorld는 MovableWorld를 상속받아야 합니다");
        assertTrue(world instanceof World, "BoundedWorld는 World를 상속받아야 합니다");

        // 부모 클래스 메서드 사용 가능한지 확인
        BoundedBall ball = new BoundedBall(new Point(100, 100), 20, Color.CYAN);
        world.add(ball);
        assertEquals(1, world.getBallCount(), "상속받은 add 메서드가 작동하지 않습니다");
    }

    @Test
    public void testRender() {
        GraphicsContext gc = Mockito.mock(GraphicsContext.class);
        BoundedBall ball = new BoundedBall(new Point(100, 100), 20, Color.MAGENTA);
        world.add(ball);

        assertDoesNotThrow(() -> {
            world.draw(gc);
        }, "BoundedWorld 렌더링 중 예외가 발생했습니다");
    }
}