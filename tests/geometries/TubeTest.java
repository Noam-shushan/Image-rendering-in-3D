package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * testing for Tube class
 *
 * @author Noam Shushan
 * @author Yonatan Chohen
 */
class TubeTest {

    @Test
    void getNormal() {
        Ray ray = new Ray(new Point3D(0,1,0), new Vector(0,1,0));
        Tube tb = new Tube(2, ray);

        assertEquals(tb.getNormal(new Point3D(0,0,0)) ,new Vector(0,-1,0));
    }
}