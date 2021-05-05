package geometries;

/**
 * A class that gives a radius for round geometric shapes
 * @author Noam Shushan
 */
public abstract class RadialGeometry {
    /**
     * the radius
     * most be greater then zero
     */
    protected final double _radius;

    /**
     * Constructor for RadialGeometry
     * @param radius the radius for the geometry (Tube/Cylinder/Sphere)
     * @throws IllegalArgumentException if radius < 0
     */
    public RadialGeometry(double radius) {
        if(radius < 0){
            throw new IllegalArgumentException("radius most be greater then zero");
        }
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
