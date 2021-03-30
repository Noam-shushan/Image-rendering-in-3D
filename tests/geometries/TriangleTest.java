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
        Triangle t1 = new Triangle(
                new Point3D(4,-4,4),
                new Point3D(-3,3,5),
                new Point3D(5,2,5));
        Ray ray = new Ray(new Point3D(0,0,0), new Vector(0.9, 1.9, 4.9));

        assertEquals(List.of(new Point3D(0.27, 0.94, 0.53)), t1.findIntersections(ray));
    }
}