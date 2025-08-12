package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaintableBallTest {

    @Test
    public void testPaintableBallCreation() {
        PaintableBall ball = new PaintableBall(new Point(100, 100), 20, Color.RED);

        // 상속받은 Ball의 속성들 확인
        Point center = ball.getCenter();
        assertEquals(100, center.getX(), 0.001, "X 좌표가 올바르게 설정되지 않았습니다");
        assertEquals(100, center.getY(), 0.001, "Y 좌표가 올바르게 설정되지 않았습니다");
        assertEquals(20, ball.getRadius(), 0.001, "반지름이 올바르게 설정되지 않았습니다");

        // PaintableBall의 고유 속성 확인
        assertEquals(Color.RED, ball.getColor(), "색상이 올바르게 설정되지 않았습니다");
    }

    @Test
    public void testPaintableBallInheritance() {
        PaintableBall paintableBall = new PaintableBall(new Point(100, 100), 20, Color.BLUE);

        // Ball의 메서드들이 작동하는지 확인 (상속 확인)
        assertTrue(paintableBall instanceof Ball, "PaintableBall은 Ball을 상속받아야 합니다");

        // Ball의 메서드 사용 가능한지 확인
        Point newCenter = new Point(200, 300);
        paintableBall.moveTo(newCenter);
        Point center = paintableBall.getCenter();
        assertEquals(200, center.getX(), 0.001, "상속받은 moveTo가 작동하지 않습니다");
        assertEquals(300, center.getY(), 0.001, "상속받은 moveTo가 작동하지 않습니다");

        // contains 메서드도 사용 가능한지 확인
        assertTrue(paintableBall.contains(200, 300), "상속받은 contains가 작동하지 않습니다");
    }

    @Test
    public void testColorHandling() {
        PaintableBall ball = new PaintableBall(new Point(0, 0), 10, Color.GREEN);

        // 색상 변경
        ball.setColor(Color.YELLOW);
        assertEquals(Color.YELLOW, ball.getColor(), "색상 변경이 올바르게 작동하지 않습니다");

        // null 색상 처리 (구현에 따라 기본 색상으로 설정하거나 예외 발생)
        ball.setColor(null);
        assertNotNull(ball.getColor(), "null 색상 설정 후에도 색상이 있어야 합니다");
    }

    @Test
    public void testDefaultColor() {
        // 색상 없이 생성하는 생성자 테스트
        PaintableBall ball = new PaintableBall(new Point(100, 100), 10);
        assertNotNull(ball.getColor(), "기본 색상이 설정되어야 합니다");
        assertEquals(Color.RED, ball.getColor(), "기본 색상은 빨간색이어야 합니다");
    }

    @Test
    public void testDraw() {
        // Mock GraphicsContext 생성 (mockito-inline 필요)
        GraphicsContext gc = Mockito.mock(GraphicsContext.class);
        PaintableBall ball = new PaintableBall(new Point(100, 100), 25, Color.BLUE);

        // draw 메서드 호출
        ball.draw(gc);

        // 적절한 메서드들이 호출되었는지 확인
        verify(gc).setFill(Color.BLUE);
        verify(gc).fillOval(ball.getCenter().getX() - ball.getRadius(), ball.getCenter().getY() - ball.getRadius(), ball.getRadius() * 2, ball.getRadius() * 2);
    }
}