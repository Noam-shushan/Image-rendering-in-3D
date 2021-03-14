package primitives;

import java.util.Objects;

/**
 * this is a class to present a Vector of 3D
 * @author Noam shushan
 */
public class Vector {
    private Point3D _head; // the representation of the vector

    /**
     * Constructor for the Vector
     * generate a new object of Vector from 3 Coordinate's
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z){
        this(new Point3D(x,y,z));
    }

    /**
     * Constructor for the Vector
     * generate a new object of Vector from 3 axis values
     * @param x x value
     * @param y y value
     * @param z z value
     */
    public Vector(double x, double y, double z){
        this(new Point3D(x,y,z));
    }

    /**
     * Constructor for the Vector
     * generate a new object of Vector from Point3D
     * @param head the head of the Vector
     */
    public Vector(Point3D head) {
        if(head.equals(Point3D.ZERO)) { // vector zero is not allowed to create
            throw new IllegalArgumentException("cannot create Vector to point(0,0,0)");
        }

        _head = new Point3D(head._x, head._y, head._z);
    }

    /**
     * add tow vectors
     * @param other
     * @return new Vector from (x1 + x2, y1 + y2, z1 + z2)
     */
    public Vector add(Vector other){
        return new Vector(_head.add(other));
    }

    /**
     * subtract tow vectors
     * @param other
     * @return new Vector from (x2 - x1, y2 - y1, z2 - z1)
     */
    public Vector subtract(Vector other){
        return _head.subtract(other.getHead());
    }

    /**
     * multiply this vector with a scalar
     * @param scalar the scalar
     * @return new Vector with the value of (scalar * x, scalar * y, scalar * z)
     */
    public Vector scale(double scalar){
        if(Double.compare(scalar, 0d) == 0){
            throw new IllegalArgumentException("scaling factor == 0");
        }
        return new Vector(new Point3D(
                _head._x._coord * scalar,
                _head._y._coord * scalar,
                _head._z._coord * scalar));
    }

    /**
     * dot product of tow vectors (x1, y1, z1), (x2, y2, z2)
     * @param other (x2, y2, z2)
     * @return x1*x2 + y1*y2 + z1*z2
     */
    public double dotProduct(Vector other){
        return _head._x._coord * other._head._x._coord +
                _head._y._coord * other._head._y._coord +
                _head._z._coord * other._head._z._coord;
    }

    /**
     * cross product of tow vectors (x1, y1, z1), (x2, y2, z2)
     * @param other (x2, y2, z2)
     * @return new Vector from (x1, y1, z1)X(x2, y2, z2) =
     * = (y1*z2 -z1*y2, z1*x2 - x1*z2, x1*y2 - y1*x2)
     */
    public Vector crossProduct(Vector other){
        return new Vector(
                (_head._y._coord * other._head._z._coord) - (_head._z._coord * other._head._y._coord),
                (_head._z._coord * other._head._x._coord) - (_head._x._coord * other._head._z._coord),
                (_head._x._coord * other._head._y._coord) - (_head._y._coord * other._head._x._coord)
        );
    }

    /**
     * the squared length of the vector (x, y, z)
     * @return x^2 + y^2 + z^2
     */
    public double lengthSquared(){
        return dotProduct(this);
    }

    /**
     * the length of the vector (x, y, z)
     * @return sqrt(x^2 + y^2 + z^2)
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * the normal of this vector of (x, y, z)
     * @return (x, y, z) / |(x, y, z)| = (x, y, z) / sqrt(x^2 + y^2 + z^2)
     */
    public Vector normalize(){
        _head = scale(1 / length())._head;
        return this;
    }

    /**
     * the normal of this vector
     * @return a new vector of normal vector from this vector
     */
    public Vector normalized(){
        var res = new Vector(_head);
        return res.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(_head, vector._head);
    }

    @Override
    public String toString() {
        return _head.toString();
    }

    /**
     * getter for the head
     * @return head
     */
    public Point3D getHead() {
        return new Point3D(_head._x, _head._y, _head._z);
    }
}
