package geometries;

import primitives.*;

/**
 * this a class represent a Cylinder
 *
 * @author Noam Shushan
 */
public class Cylinder extends Tube{
    final double _height; // the height of the Cylinder

    /**
     * Constructor of the Cylinder
     * @param axisRay
     * @param radius
     * @param height
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius ,axisRay);
        _height = height;
    }

    @Override
    public Vector getNormal(Point3D point) {
        Point3D p0 = _axisRay.getP0();
        Vector N = _axisRay.getDir();

        // find the vector on the lower base
        Vector p0_p = point.subtract(p0);
        if(p0_p.dotProduct(N) == 0){ // the vectors is orthogonal to each other
            if(p0_p.length() <= _radius){ // the point is on the base of the cylinder
                return N;
            }
        }

        // find the vector on the upper base
        Vector vN = N.scale(N.dotProduct(p0_p));
        if(p0_p.equals(vN)){
            return N;
        }
        else {
            Vector p0_p_vN = p0_p.subtract(vN);
            return p0_p_vN.length() == _radius ? p0_p_vN.normalize() : N;
        }
    }

    @Override
    public String toString() {
        return "height = " + _height +
                ", axisRay = " + _axisRay +
                ", radius = " + _radius;
    }
}
