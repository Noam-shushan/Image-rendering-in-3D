package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testing for sphere class
 *
 * @author Noam Shushan
 * @author Yonatan Chohen
 */
class SphereTest {
    Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

    /**
     *test for {@link geometries.Plane#getNormal(Point3D)}
     */
    @Test
    void getNormal() {
        Sphere sp = new Sphere(1 , new Point3D(0, 0, 1));
        var normal = sp.getNormal(new Point3D(1, 0, 1));
        assertEquals(new Vector(1,0,0), normal);
    }

    /**
     *tests for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    // ============ Equivalence Partitions Tests ==============//
    // TC01: Ray's line is outside the sphere (0 points)
    @Test
    void findIntersections1() {
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
    }

    // TC02: Ray starts before and crosses the sphere (2 points)
    @Test
    void findIntersections2(){
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0),
                p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);

        Ray ray = new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0));
        var result = sphere.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
    }

    // TC03: Ray starts inside the sphere (1 point)
    @Test
    void findIntersections3(){
        Ray ray = new Ray(new Point3D(0.5,0,0), new Vector(1.5,0,0));
        assertEquals(List.of(new Point3D(2,0,0)), sphere.findIntersections(ray));
    }

    // TC04: Ray starts after the sphere (0 points)
    @Test
    void findIntersections4(){
        Ray ray = new Ray(new Point3D(3,0,0), new Vector(1,0,0));
        assertNull(sphere.findIntersections(ray));
    }

    // =============== Boundary Values Tests ==================//

    // **** Group: Ray's line crosses the sphere (but not the center)
    // TC05: Ray starts at sphere and goes inside (1 points)
    @Test
    void findIntersections5(){
        Ray ray = new Ray(new Point3D(0,0,0), new Vector(2,2,0));
        assertEquals(List.of(new Point3D(1,1,0)) ,sphere.findIntersections(ray));
    }

    // TC06: Ray starts at sphere and goes outside (0 points)
    @Test
    void findIntersections6(){
        Ray ray = new Ray(new Point3D(1,1,0), new Vector(2,2,0));
        assertNull(sphere.findIntersections(ray));
    }

    // **** Group: Ray's line goes through the center
    // TC07: Ray starts before the sphere (2 points)
    @Test
    void findIntersections7(){
        Ray ray = new Ray(new Point3D(0,-2,0), new Vector(2,2,0));
        var result = sphere.findIntersections(ray);
        assertEquals(2, result.size());
    }

    // TC08: Ray starts at sphere and goes inside (1 points)
    @Test
    void findIntersections8() {
        Ray ray = new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 0));
        assertEquals(List.of(new Point3D(0, 0, 0)), sphere.findIntersections(ray));
    }

    // TC09: Ray starts inside (1 points)
    @Test
    void findIntersections9() {
        Ray ray = new Ray(new Point3D(0.59, 0, 0), new Vector(-0.59, 0, 0));
        assertEquals(List.of(new Point3D(0, 0, 0)), sphere.findIntersections(ray));
    }

    // TC10: Ray starts at the center (1 points)
    @Test
    void findIntersections10(){
        Ray ray = new Ray(new Point3D(1,0,0), new Vector(2,2,0));
        assertThrows(IllegalArgumentException.class, ()-> sphere.findIntersections(ray));
    }


    // TC11: Ray starts at sphere and goes outside (0 points)
    @Test
    void findIntersections11(){
        Ray ray = new Ray(new Point3D(2,0,0), new Vector(2,0,0));
        assertNull(sphere.findIntersections(ray));
    }

    // TC12: Ray starts after sphere (0 points)
    @Test
    void findIntersections12(){
        Ray ray = new Ray(new Point3D(3,0,0), new Vector(1,0,0));
        assertNull(sphere.findIntersections(ray));
    }

    // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
    // TC13: Ray starts before the tangent point
    @Test
    void findIntersections13(){
        Ray ray = new Ray(new Point3D(2,1,1), new Vector(-1,-1,0));
        assertNull(sphere.findIntersections(ray));
    }

    // TC14: Ray starts at the tangent point
    @Test
    void findIntersections14(){
        Ray ray = new Ray(new Point3D(1,0,1), new Vector(1,1,0));
        assertNull(sphere.findIntersections(ray));
    }

    // TC15: Ray starts after the tangent point
    @Test
    void findIntersections15(){
        Ray ray = new Ray(new Point3D(2,1,1), new Vector(4,4,0));
        assertNull(sphere.findIntersections(ray));
    }

    // **** Group: Special cases
    // TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
    @Test
    void findIntersections16(){
        Ray ray = new Ray(new Point3D(3,0,0), new Vector(0,0,1));
        assertNull(sphere.findIntersections(ray));
    }
}