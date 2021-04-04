package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * this class represent a Plane
 *
 * @author Noam Shushan
 */
public class Plane implements Geometry {

    final Point3D _q0;
    final Vector _normal;

    /**
     * Constructor of Plane from starting point and normal vector
     * @param p0 the point were the normal is point from
     * @param normal the normal of the plane
     */
    public Plane(Point3D p0, Vector normal) {
        _q0 = p0;
        _normal = normal.normalized();
    }

    /**
     * Constructor of Plane from 3 points on its surface
     * the points are ordered from right to left
     * forming an arc in right direction
     * we calculate the normal on the constructor
     * to avoid repeated request of the normal
     * the calculation of the normal:
     * V = P2 - P1
     * U = P3 - P1
     * N = normalize( V x U )
     * @param p1 P1
     * @param p2 P2
     * @param p3 P3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        // if UxV = (0,0,0) this Plane not create because all 3 point on the same line
        Vector N = U.crossProduct(V);
        N.normalize();

        _normal = N;
    }

    /**
     *
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
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        if(_q0.equals(p0)){
            return List.of(_q0);
        }

        Vector n = _normal;

        Vector p0_q0 = _q0.subtract(p0);
        double mechane = n.dotProduct(p0_q0);
        if (isZero(mechane)){
            return null;
        }

        double nv = n.dotProduct(v);
        if(isZero(nv)){ // the ray is vertical on the plane
            return null;
        }

        double t = alignZero(mechane / nv);

        if(t > 0)
            return List.of(ray.getPoint(t));

        return null;
    }
}
