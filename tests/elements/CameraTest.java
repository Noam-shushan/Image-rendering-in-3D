package elements;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this is a test class for the Camera class
 * @author Dan
 * @author Noam Shushan
 */
class CameraTest {
    Camera camera = new Camera(
            Point3D.ZERO,
            new Vector(0, 0, 1),
            new Vector(0, -1, 0)).setDistance(10);

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
     */
    @Test
    public void testConstructRayThroughPixel() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-2, -2, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 0));

        // TC02: 4X4 Corner (0,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-3, -3, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 0, 0));

        // TC03: 4X4 Side (0,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -3, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 0));

        // TC04: 4X4 Inside (1,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -1, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 1));

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(0, 0, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 1));

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(0, -2, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 0));

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-2, 0, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 1));
    }

    /**
     * Test method for
     * {@link elements.Camera#moveCamera(double, double, double)}.
     */
    @Test
    void moveCamera() {
        var temp = new Camera(camera.getP0(), camera.get_vTo(), camera.get_vUp())
                .setDistance(10);
        temp.moveCamera(2,2,2);
        assertNotEquals(temp, camera);
    }
}