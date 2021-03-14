package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    Point3D p1 = new Point3D(0.0d, 0.5d, 1.0d);
    Point3D p2 = new Point3D(0.00000000000000001d, 0.49999999999999999d, 1d);
    @Test
    void add() {
    }

    @Test
    void subtract() {
    }

    @Test
    void distance() {
        Point3D p3 = new Point3D(0, 0.5, 2.5);
        assertEquals(1.5, p3.distance(p2));
    }

    @Test
    void distanceSquared() {
        assertEquals(0, p1.distanceSquared(p2));
    }

    @Test
    void testEquals() {
        assertEquals(p1, p2);
    }

    @Test
    void testToString() {
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
    }
}