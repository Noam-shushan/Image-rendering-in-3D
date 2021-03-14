package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * this is a sphere class
 * @author  Noam Shushan
 */
public class Sphere implements Geometry {
    final Point3D _center;
    final double _radius;

    /**
     * Constructor for the Sphere class
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * getter for center
     * @return the center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter for the radius
     * @return the radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * the normal of this
     * @param p
     * @return normal vector
     */
    @Override
    public Vector getNormal(Point3D p) {
        Vector p0_p = p.subtract(_center);
        return p0_p.normalize();
    }
}