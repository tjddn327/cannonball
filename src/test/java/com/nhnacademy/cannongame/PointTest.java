package com.nhnacademy.cannongame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    private Point point;
    @BeforeEach
    public void setUp() {
        point = new Point(1,2);
    }
    @Test
    public void testConstructor() {
        assertEquals(1, point.getX());
        assertEquals(2, point.getY());
    }

    @Test
    public void testDistanceTo() {
        Point other = new Point(6,6);
        assertEquals(Math.sqrt(41), point.distanceTo(other));
    }

    @Test
    public void testInvalidGetBall() {
        assertThrows(IllegalArgumentException.class, () -> new Point(-1,-1));
        assertThrows(IllegalArgumentException.class, () -> new Point(1,-1));
        assertThrows(IllegalArgumentException.class, () -> new Point(-1,1));
    }

    @Test
    public void testDistanceToWithNull(
    ) {
        assertThrows(NullPointerException.class, () -> point.distanceTo(null));
    }

}
