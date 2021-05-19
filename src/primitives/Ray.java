package primitives;


import static geometries.Intersectable.GeoPoint;

import java.util.List;

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

    private static final double DELTA = 0.1d; // constants for size moving first rays for shading rays

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

        if(dir.length() != 1d){
            dir.normalize();
        }
        _dir = dir;
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
     * @param t scalar for interaction point with some geometry
     * @return p
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
