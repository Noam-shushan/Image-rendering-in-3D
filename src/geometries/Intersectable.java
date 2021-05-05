package geometries;

import primitives.*;

import java.util.List;

/**
 * interface to get intersection points of ray with some geometry
 * @author Noam Shushan
 */
public interface Intersectable {

    /**
     * find intersections of ray with geometry shape
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    List<Point3D> findIntersections(Ray ray);
}
