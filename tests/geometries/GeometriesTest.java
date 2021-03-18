package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntersections() {
        Geometries goes = new Geometries();
        goes.add(new Sphere(5, new Point3D(1, 1, 1)));
    }
}