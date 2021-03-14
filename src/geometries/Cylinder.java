package geometries;

import primitives.*;

public class Cylinder extends Tube{
    final double _height; // the height of the Cylinder

    /**
     * Constructor of the Cylinder
     * @param axisRay
     * @param radius
     * @param height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }

    /**
     * @param point should be null for flat geometries
     * @return
     */
    @Override
    public Vector getNormal(Point3D point){
        return null;
    }

    @Override
    public String toString() {
        return "height = " + _height +
                ", axisRay = " + _axisRay +
                ", radius = " + _radius;
    }
}
