package geometries;

public abstract class RadialGeometry {
    final double _radius;

    public RadialGeometry(double radius) {
        _radius = radius;
    }

    /**
     * getter for the radius
     *
     * @return the radius
     */
    public double getRadius() {
        return _radius;
    }
}
