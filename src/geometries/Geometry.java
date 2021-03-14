package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all the geometries that have a normal from them
 * @author Noam Shushan
 */
public interface Geometry {
    /**
     *
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    Vector getNormal(Point3D point);
}
