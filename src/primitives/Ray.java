package primitives;


/**
 * this is a class present a ray by starting point and direction
 * @author Noam Shushan
 */
public class Ray {
    final Point3D _p0; // the starting point of the given fund on the ray
    final Vector _dir; // the direction of the ray

    /**
     * Constructor for Ray class
     * generat an object of a Ray from Point3D and Vector
     * @param p0 the starting point of the given fund on the ray
     * @param dir the direction of the ray
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getDir() {
        return _dir;
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
