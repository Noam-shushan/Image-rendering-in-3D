package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this is a test class to the Geometries class
 * we test the intersections of ray with group of geometries
 * @author Noam Shushan
 */
class GeometriesTest {
    Geometries goes = new Geometries(
            new Sphere(2, new Point3D(1, 0.5, 1)),
            new Plane(
                    new Point3D(-2,0,0),
                    new Point3D(0,0,4),
                    new Point3D(0,-2,0)),
            new Triangle(
                    new Point3D(1,0,0),
                    new Point3D(0.1, 0.5, 2.5),
                    new Point3D(-2, 0,0)));

    /**
     *tests for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    //TC01 ray intersect all the geometries
    @Test
    void findIntersections1() {
        Ray ray = new Ray(new Point3D(0,-10,5), new Vector(0,10,-4));
        assertEquals(4, goes.findIntersections(ray).size());
    }

    //TC02 ray intersect some of the geometries
    @Test
    void findIntersections2() {
        Ray ray = new Ray(new Point3D(0,-10,5), new Vector(1,10.5,-4));
        assertEquals(3, goes.findIntersections(ray).size());
    }

    //TC03 ray intersect only one of the geometries
    @Test
    void findIntersections3() {
        Ray ray = new Ray(new Point3D(0,-10,5), new Vector(0,10,-1));
        assertEquals(1, goes.findIntersections(ray).size());
    }

    //TC04 ray not intersect the geometries
    @Test
    void findIntersections4() {
        Ray ray = new Ray(new Point3D(6,2,2), new Vector(0,3,1));
        assertNull(goes.findIntersections(ray));
    }
}