package geometries;

import primitives.*;

import java.util.List;

public interface Intersectable {
    /**
     *
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    List<Point3D> findIntersections(Ray ray);
}
