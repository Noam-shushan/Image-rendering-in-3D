package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
        Cylinder cld = new Cylinder(1,
                new Ray(new Point3D(1,0,0), new Vector(0,0,3)),
                9.93);

        assertEquals(cld._axisRay.getDir(), cld.getNormal(new Point3D(0.56, 0,0)));

        assertEquals(cld._axisRay.getDir(), cld.getNormal(new Point3D(0,0.5,3)));
    }
}