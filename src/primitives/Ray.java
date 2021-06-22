package primitives;


import static geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * this class represent a ray by starting point and direction
 * @author Noam Shushan
 */
public class Ray {
    /**
     * the starting point of the ray
     */
    final Point3D _p0;
    /**
     * the direction of the ray
     */
    final Vector _dir;

    /**
     * constants for size moving first rays for shading rays
     */
    private static final double DELTA = 0.1d;

    /**
     * Constructor for Ray class
     * generate an object of a Ray from Point3D and Vector
     * @param p0 the starting point of the given fund on the ray
     * @param dir the direction of the ray
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * constructor for creating a ray with small movement of the starting point
     * @param point the starting point that on some geometry
     * @param dir the direction of the ray
     * @param n normal to the point on some geometry
     */
    public Ray(Point3D point, Vector dir, Vector n) {
        double delta = n.dotProduct(dir) >= 0d ? DELTA : - DELTA;
        _p0 = point.add(n.scale(delta));
        _dir = dir.normalized();
    }


    /**
     * get the starting point of the ray
     * @return starting point of the ray (p0)
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * get the direction of the ray
     * @return the direction of the ray
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * p = p0 + tv
     * v is the direction of the ray, p0 is the stating point of the ray
     * @param t scalar
     * @return point on the ray
     */
    public Point3D getPoint(double t){
        return _p0.add(_dir.scale(t));
    }

    /**
     * find the closest point to the starting point of the ray in list of GeoPoints
     * @param geoPoints list of GeoPoints
     * @return the closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){
        if(geoPoints == null){
            return null;
        }

        GeoPoint closesGeoPoint = null;
        double minDistance = Double.MAX_VALUE;

        for(var geoPoint : geoPoints){
            double temp = geoPoint.point.distance(_p0);
            if(minDistance > temp){
                closesGeoPoint = geoPoint;
                minDistance = temp;
            }
        }

        return closesGeoPoint;
    }

    /**
     * construct bean of rays from the starting of the ray in the random direction around the target point
     * @param targetPoint the target point to create rays around her
     * @param numOfRays number of rays to create
     * @param radius the radius of random points around the target point
     * @return bean of rays
     */
     public  List<Ray> createBean(Point3D targetPoint, int numOfRays, double radius) {
         // random points around the point on the ray
         var randomPoints = targetPoint.createRandomPointsAround(numOfRays, radius);

        List<Ray> bean = new LinkedList<>();
        for(var p : randomPoints){
            Vector dir = p.subtract(_p0).normalized();
            bean.add(new Ray(_p0, dir));
        }
        return bean;
    }

    /**
     * construct bean of rays from the starting point and around the direction
     * randomly by the opening angle of the bean
     * @param numOfRays number of rays to create
     * @param angle the opening angle of the bean
     * @param predicate predicate on the directions of the rays in the bean
     * @return bean of rays that start in this ray and goes in random directions by the angle
     */
    public List<Ray> createBeanForGlossy(int numOfRays, double angle, Predicate<Vector> predicate) {
        // We set the distance between the geometry and the glossy object
        // as constants 1 to get simple calculation of the opening angle of the bean
        // that way we create random point around some point the is
        // distance from the stating point of the ray is 1
        var pointOnTheRay = this.getPoint(1);
        // first normal to the ray
        var vX = _dir.createVerticalVector();
        // second normal to the ray
        var vY = _dir.crossProduct(vX)
                .normalize();
        // random points around the point on the ray
        var randomPoints = pointOnTheRay.createRandomPointsAround(numOfRays, angle,
                vX, vY);
        List<Ray> bean = new LinkedList<>();
        for(var p : randomPoints){
            Vector dir = p.subtract(_p0).normalize();
             // some condition on the direction of the rays
            if(predicate.test(dir)){
                bean.add(new Ray(_p0, dir));
            }
        }
        return bean;
    }

    @Override
    public String toString() {
        return "p0 = " + _p0 + ", direction = " + _dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }
}
