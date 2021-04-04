package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void findIntersections() {
        Triangle t = new Triangle(
                new Point3D(2, 0, 0),
                new Point3D(0, 3, 0),
                new Point3D(0, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Inside polygon/triangle(1 Point)
        Ray ray1 = new Ray(new Point3D(0, 0, -1), new Vector(new Point3D(1, 1, 1)));
        List<Point3D> result = t.findIntersections(ray1);
        Point3D p1 = new Point3D(1, 1, 0);
        assertEquals(List.of(p1), result, "Inside polygon/triangle(1 Point)");

        //TC02: Outside against edge(0 Point)
        Ray ray2 = new Ray(new Point3D(0, 0, -1), new Vector(new Point3D(2, 1, 1)));
        assertNull(t.findIntersections(ray2), "Outside against edge");

        //TC03: Outside against vertex(0 Point)
        Ray ray3 = new Ray(new Point3D(0, 0, -1), new Vector(new Point3D(3, -0.5, 1)));
        assertNull(t.findIntersections(ray3), "Outside against vertex");

        // =============== Boundary Values Tests ==================
        //****Three cases (the ray begins "before" the plane)**
        //TC04: On edge(0 Point)
        Ray ray4 = new Ray(new Point3D(0, 0, -1), new Vector(new Point3D(0, 1, 1)));
        assertNull(t.findIntersections(ray4), "On edge");

        //TC05: In vertex(0 Point)
        Ray ray5 = new Ray(new Point3D(0, 0, -1), new Vector(new Point3D(0, 3, 1)));
        assertNull(t.findIntersections(ray5), "In vertex");

        //TC06: On edge's continuation(0 Point)
        Ray ray6 = new Ray(new Point3D(0, 0, -1), new Vector(new Point3D(0, 4, 1)));
        assertNull(t.findIntersections(ray6), "On edge's continuation");
    }
}