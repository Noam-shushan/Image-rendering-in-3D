package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.List;

/**
 * this is a sphere class
 * @author  Noam Shushan
 */
public class Sphere extends RadialGeometry implements Geometry {

    final Point3D _center; // the center of the sphere

    /**
     * Constructor for the Sphere class
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = center;
    }

    /**
     * getter for center
     * @return the center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * the normal of sphere:
     * n = normalize(P - O)
     * @param p
     * @return normal vector
     */
    @Override
    public Vector getNormal(Point3D p) {
        Vector p0_p = p.subtract(_center);
        return p0_p.normalize();
    }

    /**
     * find intersections point with the sphere
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        if(p0.equals(_center)){
            throw new IllegalArgumentException("ray p0 cannot be equals to the center of the sphere");
        }

        Vector u = _center.subtract(p0);
        double tm = u.dotProduct(v);
        double d = alignZero(Math.sqrt(u.lengthSquared() - (tm * tm) ));

        if(d >= _radius){
            return null; // there is no intersections points
        }

        double th = Math.sqrt( (_radius * _radius) - (d * d) );

        double t1 = tm - th;
        double t2 = tm + th;

        if(t1 > 0 && t2 > 0){
            Point3D p1 = ray.getPoint(t1);
            Point3D p2 = ray.getPoint(t2);

            return List.of(p1, p2);
        }

        if(t1 > 0){
            return List.of(ray.getPoint(t1));
        }

        if(t2 > 0){
            return List.of(ray.getPoint(t2));
        }

        return null;
    }
}