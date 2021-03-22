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
    @Test
    void testPlane() {
        try {
            Plane plane = new Plane(
                    new Point3D(1, 0, 0),
                    new Point3D(2, 0, 0),
                    new Point3D(4, 0, 0));
            fail("constructor crate plane with 3 point on the same line");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void getNormal() {
        Plane plane = new Plane(
                new Point3D(0, 0, 1),
                new Point3D(0, 2, 0),
                new Point3D(1, 0, 0));
        assertEquals(1, plane.getNormal(null).length());
    }

}