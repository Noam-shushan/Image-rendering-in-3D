package primitives;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.alignZero;
import static primitives.Util.random;

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

    // generate a random object
    static Random rand = new Random();

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
                other.getHead().getX() + _x._coord,
                other.getHead().getY() + _y._coord,
                other.getHead().getZ() + _z._coord);
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

    /**
     * create random point around this point in circles
     * @param numOfPoints number of points to create
     * @param radius the radius of the circle that we create the point on him
     * @return list of random point around this point
     */
    public List<Point3D> createRandomPointsAround(int numOfPoints, double radius){
        List<Point3D> randomPointsList = new LinkedList<>();
        randomPointsList.add(this); // add the center point

        for(int k = 1; k < numOfPoints; k++) {
            // random radius
            double r = Math.sqrt(rand.nextDouble()) * radius;
            // random angle
            double theta = rand.nextDouble() * Math.PI * 2;
            // (x, y) = (xCenter + r*cos(theta), yCenter + r*sin(theta)
            double x = alignZero(_x._coord + r * Math.cos(theta));
            double y = alignZero(_y._coord + r * Math.sin(theta));

            Point3D randomPoint = new Point3D(x, y, _z._coord);
            // check if the point is already wes created
            if (!randomPointsList.contains(randomPoint)) {
                randomPointsList.add(randomPoint);
            }
        }

        return randomPointsList;
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
