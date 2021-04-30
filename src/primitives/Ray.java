package primitives;


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
     * find the closest point to the starting point of the ray in list of points
     * @param pointsList list of points
     * @return the closest point
     */
    public Point3D findClosestPoint(List<Point3D> pointsList){
        if(pointsList == null){
            return null;
        }

        Point3D closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for(var point : pointsList){
            double temp = point.distance(_p0);
            if(minDistance > temp){
                closestPoint = point;
                minDistance = temp;
            }
        }

        return closestPoint;
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
