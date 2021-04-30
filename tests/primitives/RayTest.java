package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    Ray ray = new Ray(new Point3D(2,-2,3), new Vector(-2,-2,-2));
    /**
     * {@link primitives.Ray#findClosestPoint(List<primitives.Point3D)}
     */
    // ============ Equivalence Partitions Tests ==============
    //TC01: Closest point is in the middle of the list
    @Test
    void findClosestPoint() {
        var points = List.of(
                new Point3D(0,0,1),
                new Point3D(0,-1,0),
                new Point3D(1,-2,3),
                new Point3D(1,1,1),
                new Point3D(1,2,3));

        assertEquals(points.get(points.size() / 2), ray.findClosestPoint(points));
    }

    // =============== Boundary Values Tests ==================
    //TC02: gets empty list
    @Test
    void findClosestPoint2() {
        List<Point3D> points = null;
        assertNull(ray.findClosestPoint(points));
    }

    //TC03: Closest point is in the beginning of the list
    @Test
    void findClosestPoint3() {
        var points = List.of(
                new Point3D(1,-2,3),
                new Point3D(0,0,1),
                new Point3D(0,-1,0),
                new Point3D(1,1,1),
                new Point3D(1,2,3));
        assertEquals(points.get(0), ray.findClosestPoint(points));
    }

    //TC04: Closest point is in the end of the list
    @Test
    void findClosestPoint4() {
        var points = List.of(
                new Point3D(0,0,1),
                new Point3D(0,-1,0),
                new Point3D(1,1,1),
                new Point3D(1,2,3),
                new Point3D(1,-2,3));
        assertEquals(points.get(points.size()-1), ray.findClosestPoint(points));
    }
}