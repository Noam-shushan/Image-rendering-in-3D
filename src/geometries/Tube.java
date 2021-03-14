package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * this is a class represent a Tube
 * @author Noam Shushan
 */
public class Tube implements Geometry {
    final Ray _axisRay;
    final double _radius;

    /**
     * Constructor for Tube
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    @Override
    public Vector getNormal(Point3D point) {
        var p_p0 = point.subtract(_axisRay.getP0());
        double t = _axisRay.getDir().dotProduct(p_p0);

        Point3D O = _axisRay.getP0().add(p_p0.scale(t));

        Vector N = point.subtract(O);

        return N.normalize();
    }
}
