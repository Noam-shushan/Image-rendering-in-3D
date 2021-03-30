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

    @Test
    void getNormal() {
        assertEquals(1, plane.getNormal(null).length());
    }

    @Test
    void getNormal1() {
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


    @Test
    void findIntersections() {
        Plane plane2 = new Plane(new Point3D(10,2,-3.5), new Vector(3,4,-2));
        Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(5,2,4));
        assertEquals(List.of(new Point3D(15,6,12)), plane2.findIntersections(ray));
    }

    @Test
    void findIntersections1() {
        Ray ray = new Ray(new Point3D(0, 3, 0), new Vector(2,4,0));
        assertNull(plane.findIntersections(ray));
    }
}