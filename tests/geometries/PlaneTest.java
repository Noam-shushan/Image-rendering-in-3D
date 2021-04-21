package geometries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * testing for Plane class
 *
 * @author Noam Shushan
 * @author Yonatan Chohen
 */
class PlaneTest {
    Plane plane = new Plane(
            new Point3D(0, 0, 1),
            new Point3D(0, 2, 0),
            new Point3D(1, 0, 0));
    /**
     *test for {@link geometries.Plane#Plane(Point3D, Point3D, Point3D)}.
     */
    @Test
    void testPlane() {
        try {
             new Plane(
                    new Point3D(1, 0, 0),
                    new Point3D(2, 0, 0),
                    new Point3D(4, 0, 0));
            fail("constructor crate plane with 3 point on the same line");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     *tests for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal1() {
        assertEquals(1, plane.getNormal(null).length());
    }

    @Test
    void getNormal2() {
        Plane p1 = new Plane(
                new Point3D(0,1,0),
                new Point3D(1,0,0),
                new Point3D(0,0,1));

        Plane p2 = new Plane(
                new Point3D(1,0,0),
                new Point3D(0,0,1),
                new Point3D(0,1,0));

        assertEquals(p1.getNormal(null), p2.getNormal(null));
    }

    /**
     *tests for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    // ============ Equivalence Partitions Tests ==============//
    // The Ray's here ar not orthogonal and not parallels to the plane

    // TC01: Ray intersect the plane (1 point)
    @Test
    void findIntersections1() {
        Ray ray = new Ray(new Point3D(0,-2,0), new Vector(1, 4,-1));
        assertEquals(List.of(new Point3D(1,2,-1)), plane.findIntersections(ray));
    }

    // TC02: Ray does not intersect the plane (0 point)
    @Test
    void findIntersections2() {
        Ray ray = new Ray(new Point3D(0, 5, 0), new Vector(6,-5,0));
        assertNull(plane.findIntersections(ray));
    }

    // =============== Boundary Values Tests ==================

    // **** Group: Ray is orthogonal to the plane
    // TC03: Ray start outside of the plane and goes inside the plane (1 point)
    @Test
    void findIntersections3() {
        Ray ray = new Ray(new Point3D(-2, 0, 0), new Vector(2,1,2));
        assertEquals(List.of(new Point3D(-0.6666666666666667, 0.6666666666666666, 1.3333333333333333)),
                plane.findIntersections(ray));
    }

    // TC04: Ray start outside of the plane (0 point)
    @Test
    void findIntersections4() {
        Ray ray = new Ray(new Point3D(-2, 0, 0), new Vector(-1.53,-0.77,-1.53));
        assertNull(plane.findIntersections(ray));
    }

    // TC05: Ray start inside the plane (0 point)
    @Test
    void findIntersections5() {
        Ray ray = new Ray(new Point3D(0, 0, 1), new Vector(1,0.5,2));
        assertNull(plane.findIntersections(ray));
    }

    // **** Group: Ray is parallel to the plane
    // TC06: Ray start inside (0 point)
    @Test
    void findIntersections6() {
        Ray ray = new Ray(new Point3D(0.67, -2.16, 1.41), new Vector(-1.8,-0.56,2.08));
        assertNull(plane.findIntersections(ray));
    }

    // TC07: Ray start outside (0 point)
    @Test
    void findIntersections7() {
        Ray ray = new Ray(new Point3D(5, 0, 0), new Vector(-0.64,-0.2,0.74));
        assertNull(plane.findIntersections(ray));
    }
}