package primitives;

import static geometries.Intersectable.GeoPoint;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    Ray ray = new Ray(new Point3D(2,-2,3), new Vector(-2,-2,-2));
    /**
     * {@link primitives.Ray#findClosestGeoPoint(List<GeoPoint>)}
     */
    // ============ Equivalence Partitions Tests ==============
    //TC01: Closest point is in the middle of the list
    @Test
    void findClosestGeoPoint() {
        var points = List.of(
                new GeoPoint(null, new Point3D(0,0,1)),
                new GeoPoint(null, new Point3D(0,-1,0)),
                new GeoPoint(null, new Point3D(1,-2,3)),
                new GeoPoint(null, new Point3D(1,1,1)),
                new GeoPoint(null, new Point3D(1,2,3)));

        assertEquals(points.get(points.size() / 2).point, ray.findClosestGeoPoint(points).point);
    }

    // =============== Boundary Values Tests ==================
    //TC02: gets empty list
    @Test
    void findClosestGeoPoint2() {
        List<GeoPoint> points = null;
        assertNull(ray.findClosestGeoPoint(points));
    }

    //TC03: Closest point is in the beginning of the list
    @Test
    void findClosestGeoPoint3() {
        var points = List.of(
                new GeoPoint(null, new Point3D(1,-2,3)),
                new GeoPoint(null, new Point3D(0,-1,0)),
                new GeoPoint(null, new Point3D(1,-2,3)),
                new GeoPoint(null, new Point3D(1,1,1)),
                new GeoPoint(null, new Point3D(1,2,3)));
        assertEquals(points.get(0).point, ray.findClosestGeoPoint(points).point);
    }

    //TC04: Closest point is in the end of the list
    @Test
    void findClosestGeoPoint4() {
        var points = List.of(
                new GeoPoint(null, new Point3D(1,-2,3)),
                new GeoPoint(null, new Point3D(0,-1,0)),
                new GeoPoint(null, new Point3D(1,-2,3)),
                new GeoPoint(null, new Point3D(1,1,1)),
                new GeoPoint(null, new Point3D(1,-2,3)));
        assertEquals(points.get(points.size()-1).point, ray.findClosestGeoPoint(points).point);
    }
}