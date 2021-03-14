package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

/**
 * testing for Point3D class
 *
 * @author Noam Shushan 314717588
 */
class Point3DTest {

    Point3D p1 = new Point3D(0.0d, 0.5d, 1.0d);
    Point3D p2 = new Point3D(0.00000000000000001d, 0.49999999999999999d, 1d);

    @Test
    void add() {
        Point3D p3 = new Point3D(1, 2, 3);
        assertTrue(!Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))),
            "ERROR: Point + Vector does not work correctly");
    }

    @Test
    void subtract() {
        assertTrue(!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1))
            ,"ERROR: Point - Point does not work correctly");
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