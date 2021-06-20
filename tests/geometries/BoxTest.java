package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

    @Test
    void constructorTest(){
        Box box = new Box(
                new Point3D(2, 2, 0),
                new Point3D(0,2,0),
                Point3D.ZERO,
                new Point3D(2,0,0),
                3d
        );
    }

    @Test
    void findGeoIntersections() {
        Box box = new Box(
                new Point3D(2, 2, 0),
                new Point3D(0,2,0),
                Point3D.ZERO,
                new Point3D(2,0,0),
                3d
        );
        Ray ray = new Ray(new Point3D(5,-5,3), new Vector(-3, 5.85, -0.69));
        assertEquals(2, box.findGeoIntersections(ray).size());
    }
}