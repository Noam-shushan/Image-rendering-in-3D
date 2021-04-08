package primitives;

/**
 * this is a basic point for RayTracing project in 3D
 * @author Noam Shushan
 */
public class Point3D {
    /**
     * x axis
     */
    final Coordinate _x;
    /**
     * y axis
     */
    final Coordinate _y;
    /**
     * z axis
     */
    final Coordinate _z;

    /**
     * a zero constants of point (0, 0, 0)
      */
    public final static Point3D ZERO = new Point3D(0d, 0d, 0d);

    /**
     * Constructor for Point3D
     * generate an object of Point3D from 3 axis values
     * @param x x value
     * @param y y value
     * @param z z value
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * add two Point3D
     * @param other the other point to add to this point
     * @return new Point3D of (x1 + x2, y1 + y2, z1 + z2)
     */
    public Point3D add(Vector other) {
        return new Point3D(
                other.getHead()._x._coord + _x._coord,
                other.getHead()._y._coord + _y._coord,
                other.getHead()._z._coord + _z._coord);
    }

    /**
     * subtract tow Point3D
     * @param other the other point to subtract from this point
     * @return new Vector with the value of (x1 - x2, y1 - y2, z1 - z2)
     * @throws IllegalArgumentException if (x1 - x2, y1 - y2, z1 - z2) = (0,0,0)
     */
    public Vector subtract(Point3D other) {
        return new Vector(
                new Point3D(
                        _x._coord - other._x._coord,
                        _y._coord - other._y._coord,
                        _z._coord - other._z._coord));
    }

    /**
     * Calculate the distance between two point
     * @param other the other point to calculate the distance from
     * @return sqrt( (x2 - x1)^2 + (y2 -y1)^2 + (z2 - z1)^2 )
     */
    public double distance(Point3D other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Calculate the squared distance between two point
     * @param other the other point to calculate the squared distance from
     * @return (x2 - x1)^2 + (y2 -y1)^2 + (z2 - z1)^2
     */
    public double distanceSquared(Point3D other){
        return (other._x._coord - _x._coord) * (other._x._coord - _x._coord) +
                (other._y._coord - _y._coord) * (other._y._coord - _y._coord) +
                (other._z._coord - _z._coord) * (other._z._coord - _z._coord);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ", " + _z + ')';
    }

    /**
     * getter for the x value
     * @return x coordinate value
     */
    public double getX() {
        return _x._coord;
    }
    /**
     * getter for the y value
     * @return y coordinate value
     */
    public double getY() {
        return _y._coord;
    }
    /**
     * getter for the z value
     * @return z coordinate value
     */
    public double getZ() {
        return _z._coord;
    }
}
