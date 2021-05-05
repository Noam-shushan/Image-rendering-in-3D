package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all the geometries shape
 * Provides a normal vector for the geometry
 * @author Noam Shushan
 */
public interface Geometry extends Intersectable {
    /**
     * get the normal that stand on point in some geometry
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    Vector getNormal(Point3D point);
}
