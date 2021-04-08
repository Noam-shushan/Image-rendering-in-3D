package geometries;

import primitives.*;

import java.util.List;

/**
 * this class represent a Tube
 *
 * @author Noam Shushan
 */
public class Tube extends RadialGeometry implements Geometry {
    final Ray _axisRay;

    /**
     * Constructor for Tube
     * @param axisRay the ray of the tube
     * @param radius the radius of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        _axisRay = axisRay;
    }

    /**
     * normal of Tube
     * calculation of the normal:
     * t = v*(p - p0)
     * O = p0 + t*v
     * N = normalize(P - O)
     *
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector p_p0 = point.subtract(_axisRay.getP0());
        double t = _axisRay.getDir().dotProduct(p_p0);

        Point3D O = _axisRay.getP0().add(p_p0.scale(t));
        if (O.equals(point))
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
//        Point3D p0 = ray.getP0();
//        Vector v = ray.getDir();
//        Point3D dirP = v.getHead();
//
//        double a = dirP.getX() * dirP.getX() + dirP.getZ() * dirP.getZ();
//        double b = 2 * (p0.getX() * dirP.getX() + p0.getZ() * dirP.getZ());
//        double c = p0.getX() * p0.getX() + p0.getZ() * p0.getZ() - _radius * _radius;
//
//        double discr = b * b -4 * (a * c);
//        if(discr < 0){
//            return null;
//        }
//
//        double x1 = (-b + Math.sqrt(discr)) / (2 * a);
//        double x2 = (-b - Math.sqrt(discr)) / (2 * a);
//
//        double t = x1 > x2 ? x2 : x1;
//
//        if(t < 0){
//            return null;
//        }
//
//        Point3D point = ray.getPoint(t);
//        if(point.getY() < 0){
//            return null;
//        }
//
//        Vector normal = getNormal(point);
//
//        if(dirP.getY() != 0){
//
//        }

        return null;
    }
}
