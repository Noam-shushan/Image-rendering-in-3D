package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * this is a class represent a Tube
 * @author Noam Shushan
 */
public class Tube extends RadialGeometry implements Geometry {
    final Ray _axisRay;

    /**
     * Constructor for Tube
     * @param axisRay
     * @param radius
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        _axisRay = axisRay;
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
        if(O.equals(point))
            throw new IllegalArgumentException("point cannot be equal to O");

        Vector N = point.subtract(O);

        return N.normalize();
    }

    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
