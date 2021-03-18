package geometries;

import primitives.*;

/**
 * this is a class present a Cylinder
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
        return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "height = " + _height +
                ", axisRay = " + _axisRay +
                ", radius = " + _radius;
    }
}
