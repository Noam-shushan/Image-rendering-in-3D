package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * this a class represent a final Cylinder by radius, ray and height
 *
 * @author Noam Shushan
 */
public class Cylinder extends Tube{

    /**
     * the height of the Cylinder
     * most be greater then zero
     */
    final double _height;

    /**
     * the lower base
     */
    private final Plane _base1;

    /**
     * the upper base
     */
    private final Plane _base2;

    /**
     * Constructor of the Cylinder
     * We will represent cylinder by ray, radius and height
     * @param axisRay the ray of the Tube
     * @param radius the radius of the Tube
     * @param height the height of the cylinder
     * @throws IllegalArgumentException if radius is lower then 0 or the height is lower then 0
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius ,axisRay);

        if(height < 0){
            throw new IllegalArgumentException("height most be greater then zero");
        }

        _height = height;

         Vector v = _axisRay.getDir();
        _base1 = new Plane(_axisRay.getP0(), v);
        _base2= new Plane(_axisRay.getPoint(_height), v);
    }

    /**
     * get ths normal from some point on the cylinder
     * @param point the point to get the normal from
     * @return vector normal to to the cylinder from the point
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D p0 = _axisRay.getP0();
        Vector N = _axisRay.getDir();

        // find the vector on the lower base
        Vector p0_p = point.subtract(p0);
        if(p0_p.dotProduct(N) == 0){ // the vectors is orthogonal to each other
            if(p0_p.length() <= _radius){ // the point is on the base of the cylinder
                return N;
            }
        }

        // find the vector on the upper base
        Vector vN = N.scale(N.dotProduct(p0_p));
        if(p0_p.equals(vN)){
            return N;
        }
        else {
            Vector p0_p_vN = p0_p.subtract(vN);
            return p0_p_vN.length() == _radius ? p0_p_vN.normalize() : N;
        }
    }
    /**
     * find intersections with cylinder
     * @param ray ray intersecting with the cylinder
     * @return list of intersections can be max of 2 points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray){
        GeoPoint p1 = null;
        GeoPoint p2 = null;
        Point3D o1 = _base1._q0;
        var ints = _base1.findGeoIntersections(ray);
        if (ints != null) {
            GeoPoint p = ints.get(0);
            if (alignZero(o1.distance(p.point) - _radius) <= 0){
                p1 = p;
            }
        }

        Point3D o2 = _base2._q0;
        ints = _base2.findGeoIntersections(ray);
        if (ints != null) {
            GeoPoint p = ints.get(0);
            if (alignZero(o2.distance(p.point) - _radius) <= 0){
                p2 = p;
            }
        }

        if (p1 != null && p2 != null){
            return List.of(p1, p2);
        }

        List<GeoPoint> pointIntersectTube = super.findGeoIntersections(ray);
        if (pointIntersectTube == null) {
            if (p1 != null){
                return List.of(p1);
            }
            if (p2 != null){
                return List.of(p2);
            }

            return null;
        }

        Vector v = _axisRay.getDir();

        if (p1 == null && p2 == null) {
            // bases are not intersected
            // therefore all tube intersections - either all inside or all outside
            for (GeoPoint p : pointIntersectTube) {
                double distanceFromLowBase = alignZero(p.point.subtract(o1).dotProduct(v));
                if (distanceFromLowBase <= 0 || alignZero(distanceFromLowBase - _height) >= 0)
                    return null;
            }
            return pointIntersectTube;
        }

        // if we are here - one base is intersected
        List<GeoPoint> resultList = new LinkedList<>(List.of(p1 == null ? p2 : p1));
        for (GeoPoint p : pointIntersectTube) {
            double distanceFromLowBase = alignZero(p.point.subtract(o1).dotProduct(v));
            if (distanceFromLowBase > 0 && alignZero(distanceFromLowBase - _height) < 0){
                resultList.add(p);
            }
        }

        return resultList;
    }

    @Override
    public String toString() {
        return "height = " + _height +
                ", axisRay = " + _axisRay +
                ", radius = " + _radius;
    }
}
