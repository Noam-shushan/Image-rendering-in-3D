package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * this class represent a Plane with point and vector normal that standing on the point
 *
 * @author Noam Shushan
 */
public class Plane extends Geometry {

    /**
     * the point that the normal stand on
     */
    final Point3D _q0;
    /**
     * the normal that stand on the point
     */
    final Vector _normal;

    /**
     * Constructor of Plane from point and vector normal that standing on the point
     * @param p0 the point were the normal is point from
     * @param normal the normal that stand on the point
     */
    public Plane(Point3D p0, Vector normal) {
        _q0 = p0;
        _normal = normal.normalized();
    }

    /**
     * Constructor of Plane from 3 points on its surface <br/>
     * the points are ordered from right to left
     * we calculate the normal on the constructor to avoid repeated request of the normal
     * @param p1 P1
     * @param p2 P2
     * @param p3 P3
     * @throws IllegalArgumentException if UxV = (0,0,0) => all 3 point on the same line
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;

        // the calculation of the normal:
        // V = P2 - P1
        // U = P3 - P1
        // N = normalize( V x U )
        try {
            Vector U = p2.subtract(p1);
            Vector V = p3.subtract(p1);

            // if UxV = (0,0,0) this Plane not create because all 3 point on the same line
            Vector N = U.crossProduct(V);
            N.normalize();

            _normal = N;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("can not create a plane with all 3 point on the same line");
        }
    }

    /**
     * get the vector normal of the plane, it is the same normal for every point on the plane
     * @param point dummy point not use for flat geometries
     *              should be assigned null value
     * @return normal to the plane
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    @Override
    public String toString() {
        return "q0 = " + _q0 + ", normal = " + _normal;
    }

    /**
     * find intersections point with the plane
     * @param ray ray that cross the plane
     * @return list of intersection points that were found => p0 + tv
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        if(_q0.equals(p0)){
            return null;
        }

        Vector n = _normal;

        // t = n∙(q0 - p0) / n∙v
        // if t > 0 point as found

        Vector p0_q0 = _q0.subtract(p0);
        double mone = alignZero(n.dotProduct(p0_q0));
        if (isZero(mone)){ // the starting point of the ray is inside the plane
            return null;
        }

        double nv = alignZero(n.dotProduct(v));
        if(isZero(nv)){ // the ray is vertical on the plane
            return null;
        }

        double t = alignZero(mone / nv);

        if(t > 0){
            return List.of(new GeoPoint(this,  ray.getPoint(t)));
        }
        return null;
    }

}
