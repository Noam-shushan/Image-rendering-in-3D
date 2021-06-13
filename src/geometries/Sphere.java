package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.List;

/**
 * this class represent a sphere by center point and radius
 * @author  Noam Shushan
 */
public class Sphere extends Geometry {

    /**
     * the center of the sphere
     */
    final Point3D _center;

    /**
     * the radius of the sphere
     * most be greater then zero
     */
    final double _radius;

    /**
     * Constructor for the Sphere class
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     * @throws IllegalArgumentException if radius < 0
     */
    public Sphere(double radius, Point3D center) {
        if(radius < 0){
            throw new IllegalArgumentException("radius most be greater then zero");
        }

        _radius = radius;
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
     * n = normalize(p - centerPoint)
     * @param p the point on the sphere we want the normal from
     * @return normal vector
     */
    @Override
    public Vector getNormal(Point3D p) {
        Vector p0_p = p.subtract(_center);
        return p0_p.normalize();
    }

    /**
     * find intersections point with the sphere
     * let O be the center of the sphere, let r be the radius <br/>
     * u = O − p0 <br/>
     * tm = v * u <br/>
     * d = sqrt ( |u|^2 - tm^2 ) <br/>
     * th = sqrt ( r^2 - d^2 ) <br/>
     * if (d >= r) there are no intersections <br/>
     * t1,2 = tm +- th, p1,2 = p0 + t1,2*v <br/>
     * t1,2*v => take only if t > 0 <br/>
     * @param ray ray that cross the sphere
     * @return list of intersection points that were found
     * @throws IllegalArgumentException if the starting point of the ray equals to the center of the sphere
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
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

        double th = alignZero(Math.sqrt( (_radius * _radius) - (d * d) ));

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if(t1 > 0 && t2 > 0){
            Point3D p1 = ray.getPoint(t1);
            Point3D p2 = ray.getPoint(t2);

            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }

        if(t1 > 0){
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        }

        if(t2 > 0){
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }

        return null; // 0 points
    }
}