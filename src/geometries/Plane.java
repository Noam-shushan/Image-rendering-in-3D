package geometries;

import primitives.Point3D;
import primitives.Vector;

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
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;
//        //TODO check direction of vectors
//        Vector U = p1.subtract(p2);
//        Vector V = p3.subtract(p2);

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        Vector N = U.crossProduct(V);

        N.normalize();

        //right hand rule
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
}
