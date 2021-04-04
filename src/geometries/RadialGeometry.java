package geometries;

/**
 * A class that gives a radius of round geometric shapes
 * @author Noam Shushan
 */
public abstract class RadialGeometry {
    protected final double _radius;

    /**
     * Constructor
     * @param radius the radius of the geometry (Tube/Cylinder/Sphere)
     */
    public RadialGeometry(double radius) {
        _radius = radius;
    }

    /**
     * getter for the radius
     * @return the radius of the geometry
     */
    public double getRadius() {
        return _radius;
    }
}
