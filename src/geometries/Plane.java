package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 * this class present a Plane
 *
 * @author Noam Shushan
 */
public class Plane implements Geometry {

    final Point3D _q0;
    final Vector _normal;

    /**
     * Constructor of Plane from starting point and normal vector
     * @param p0
     * @param normal
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
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

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

        double nv = _normal.dotProduct(v);
        if(isZero(nv)){ // the ray is vertical on the plane
            return null;
        }

        double t = _normal.dotProduct(_q0.subtract(p0)) / nv;

        return List.of(p0.add(v.scale(t)));
    }
}
