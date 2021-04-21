package primitives;

/**
 * this class represent a Vector of 3D
 * @author Noam shushan
 */
public class Vector {
    /**
     * the representation of the vector
     */
    private Point3D _head;

    /**
     * Constructor for the Vector
     * generate a new object of Vector from Point3D
     * @param head the head of the Vector
     * @throws IllegalArgumentException if head is vector zero
     */
    public Vector(Point3D head) {
        if(head.equals(Point3D.ZERO)) { // vector zero is not allowed to create
            throw new IllegalArgumentException("cannot create Vector to point(0,0,0)");
        }

        _head = new Point3D(head.getX(), head.getY(), head.getZ());
    }

    /**
     * Constructor for the Vector
     * generate a new object of Vector from 3 axis values
     * @param x x value
     * @param y y value
     * @param z z value
     * @throws IllegalArgumentException if x = y = z = 0
     */
    public Vector(double x, double y, double z){
        this(new Point3D(x, y, z));
    }

    /**
     * add tow vectors
     * @param other the other vector to add
     * @return new Vector from (x1 + x2, y1 + y2, z1 + z2)
     */
    public Vector add(Vector other){
        return new Vector(_head.add(other));
    }

    /**
     * subtract tow vectors
     * @param other the other vector to subtract with
     * @return new Vector from (x1 - x2, y1 - y2, z1 - z2)
     * @throws IllegalArgumentException if (x1 - x2, y1 - y2, z1 - z2) = (0,0,0)
     */
    public Vector subtract(Vector other){
        return _head.subtract(other._head);
    }

    /**
     * multiply this vector with a scalar
     * @param scalar the scalar
     * @return new Vector with the value of (scalar * x, scalar * y, scalar * z)
     * @throws IllegalArgumentException if scalar = 0
     */
    public Vector scale(double scalar){
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
     * @throws IllegalArgumentException if we get vector zero
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
        return _head.equals(vector._head);
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
        return _head;
    }

    public static double getAngle(Vector u, Vector v){
        return Math.acos(u.dotProduct(v) / (u.length() * v.length()) );
    }
}
